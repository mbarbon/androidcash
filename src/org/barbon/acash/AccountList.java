package org.barbon.acash;

import android.app.ListActivity;

import android.database.Cursor;

import android.os.Bundle;

import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AccountList extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExpenseDatabase db = ExpenseDatabase.getInstance(this);

        setAccountData(db.getAccountList());

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
    }

    private void setAccountData(Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
            this, R.layout.accountitem, data,
            new String[] { ExpenseDatabase.ACCOUNT_DESCRIPTION_COLUMN },
            new int[] { R.id.account_item_description });

        setListAdapter(adapter);
    }
}
