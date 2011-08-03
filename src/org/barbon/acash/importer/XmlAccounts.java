/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash.importer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlAccounts {
    private static final String GNC_NS = "http://www.gnucash.org/XML/gnc";
    private static final String ACT_NS = "http://www.gnucash.org/XML/act";
    private static final String SLOT_NS = "http://www.gnucash.org/XML/slot";
    private static final String EMPTY_NS = "";

    private class GnuCashHandler extends DefaultHandler {
        private Map<String, GnuCashAccount> accounts =
            new HashMap<String, GnuCashAccount>();

        // account data
        private String description, id, parentId, type;
        private boolean isPlaceholder;

        // slot data
        private Map<String, String> slotValues;
        private String slotKey, slotValue;

        // XML character data
        private StringBuilder characterData = null;

        public void startElement(String uri, String localName, String qName,
                                 Attributes atts) {
            // clear character data
            if (characterData == null || characterData.length() > 0)
                characterData = new StringBuilder();

            if (localName.equals("slots") && uri.equals(ACT_NS))
                slotValues = new HashMap<String, String>();
        }

        public void endElement(String uri, String localName, String qName) {
            if (localName.equals("account") && uri.equals(GNC_NS)) {
                GnuCashAccount account = new GnuCashAccount();
                GnuCashAccount parent = null;
                String fullName;

                if (parentId != null) {
                    parent = accounts.get(parentId);

                    if (parent.fullName != null)
                        fullName = parent.fullName + ":" + description;
                    else
                        fullName = description;
                }
                else
                    // ROOT node
                    fullName = null;

                account.fullName = fullName;
                account.description = description;
                account.isPlaceholder = isPlaceholder;

                accounts.put(id, account);

                // ignore the ROOT account
                if (!type.equals("ROOT"))
                    gnuCashAccounts.add(account);

                fullName = description = id = parentId = type = null;
                isPlaceholder = false;
            }
            else if (localName.equals("name") && uri.equals(ACT_NS))
                description = characterData.toString();
            else if (localName.equals("id") && uri.equals(ACT_NS))
                id = characterData.toString();
            else if (localName.equals("parent") && uri.equals(ACT_NS))
                parentId = characterData.toString();
            else if (localName.equals("description") && uri.equals(ACT_NS))
                description = characterData.toString();
            else if (localName.equals("type") && uri.equals(ACT_NS))
                type = characterData.toString();
            else if (localName.equals("slot") && uri.equals(EMPTY_NS)) {
                // fill slot key/value
                slotValues.put(slotKey, slotValue);

                slotKey = slotValue = null;
            }
            else if (localName.equals("slots") && uri.equals(ACT_NS)) {
                // handle slot values
                String placeholder = slotValues.get("placeholder");
                isPlaceholder = placeholder != null &&
                    placeholder.equals("true");

                slotValues = null;
            }
            else if (localName.equals("key") && uri.equals(SLOT_NS))
                slotKey = characterData.toString();
            else if (localName.equals("value") && uri.equals(SLOT_NS))
                slotValue = characterData.toString();

            characterData = null;
        }

        public void characters (char[] ch, int start, int length) {
            // ignore text between elements
            if (characterData != null)
                characterData.append(ch, start, length);
        }
    }

    private InputStream gnuCashFile;
    private List<GnuCashAccount> gnuCashAccounts;
    private Exception error;

    public XmlAccounts(File file) {
        try {
            gnuCashFile = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public XmlAccounts(InputStream file) {
        gnuCashFile = file;
    }

    public List<GnuCashAccount> getAccounts() {
        if (gnuCashAccounts != null)
            return gnuCashAccounts;

        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            GnuCashHandler handler = new GnuCashHandler();

            gnuCashAccounts = new ArrayList<GnuCashAccount>();
            parser.parse(gnuCashFile, handler);
        }
        catch (ParserConfigurationException e) {
            error = e;

            return gnuCashAccounts = null;
        }
        catch (SAXException e) {
            error = e;

            return gnuCashAccounts = null;
        }
        catch (IOException e) {
            error = e;

            return gnuCashAccounts = null;
        }

        return gnuCashAccounts;
    }

    public Exception getError() {
        return error;
    }
}
