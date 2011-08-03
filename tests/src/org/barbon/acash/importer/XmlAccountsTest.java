package org.barbon.acash.importer;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

import android.test.AndroidTestCase;

import java.io.InputStream;

import java.util.List;

import org.barbon.acash.tests.R;

public class XmlAccountsTest extends AndroidTestCase {
    public InputStream getXml(int id) throws NameNotFoundException {
        Context testContext =
            getContext().createPackageContext("org.barbon.acash.tests", 0);
        InputStream xml = testContext.getResources()
            .openRawResource(id);

        return xml;
    }

    public static void assertEquals(String fullName, String description,
                                    boolean placeholder,
                                    GnuCashAccount account) {
        assertEquals(fullName, account.fullName);
        assertEquals(description, account.description);
        assertEquals(placeholder, account.isPlaceholder);
    }

    public void testParseBase() throws NameNotFoundException {
        InputStream xml = getXml(R.raw.accounts_base);
        XmlAccounts parser = new XmlAccounts(xml);
        List<GnuCashAccount> accounts = parser.getAccounts();
        Exception error = parser.getError();

        assertEquals(null, error);
        assertEquals(4, accounts.size());

        assertEquals("Attività",
                     "Attività", false,
                     accounts.get(0));
        assertEquals("Attività:Attività correnti",
                     "Attività correnti", false,
                     accounts.get(1));
        assertEquals("Attività:Attività correnti:Conto corrente",
                     "Conto corrente", false,
                     accounts.get(2));
        assertEquals("Attività:Attività correnti:Liquidi",
                     "Liquidi", false,
                     accounts.get(3));
    }

    public void testParsePlaceholder() throws NameNotFoundException {
        InputStream xml = getXml(R.raw.accounts_placeholder);
        XmlAccounts parser = new XmlAccounts(xml);
        List<GnuCashAccount> accounts = parser.getAccounts();
        Exception error = parser.getError();

        assertEquals(null, error);
        assertEquals(2, accounts.size());

        assertEquals("Attività",
                     "Attività", true,
                     accounts.get(0));
        assertEquals("Attività:Liquidi",
                     "Liquidi", false,
                     accounts.get(1));
    }
}
