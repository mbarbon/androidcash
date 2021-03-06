/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.app.ListActivity;

import android.content.Intent;

import android.database.Cursor;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.barbon.acash.importer.GnuCashAccount;
import org.barbon.acash.importer.XmlAccounts;

public class AccountList extends ListActivity {
    private static final int BROWSE_FILE = 0;

    private AdapterView.OnItemClickListener clickListener =
        new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long rowId) {
                displayAccount(rowId);
            }
        };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExpenseDatabase db = ExpenseDatabase.getInstance(this);

        setAccountData(db.getAccountList());

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(clickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);

        inflater.inflate(R.menu.accountlist, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.new_account:
            startActivity(Globals.NEW_ACCOUNT_INTENT);

            return true;
        case R.id.import_from_gnucash:
            startActivityForResult(Globals.BROWSE_GNUCASH_FILES, BROWSE_FILE);

            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode != BROWSE_FILE || resultCode == RESULT_CANCELED)
            return;

        String importFile = data.getStringExtra(BrowseGnuCash.FILE_NAME);

        XmlAccounts accounts = new XmlAccounts(importFile);
        ExpenseDatabase db = ExpenseDatabase.getInstance(this);

        for (GnuCashAccount account : accounts.getAccounts())
            db.insertAccountIfNeeded(account.description, account.fullName);
    }

    // implementation

    private void setAccountData(Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
            this, R.layout.accountitem, data,
            new String[] { ExpenseDatabase.ACCOUNT_DESCRIPTION_COLUMN },
            new int[] { R.id.account_item_description });

        startManagingCursor(data); // TODO deprecated
        setListAdapter(adapter);
    }

    private void displayAccount(long id) {
        Intent intent = new Intent(Globals.ACCOUNT_DETAILS_INTENT);

        intent.putExtra(AccountDetails.ACCOUNT_ID, id);

        startActivity(intent);
    }
}
