package org.barbon.acash;

import android.app.ListActivity;

import android.content.Intent;

import android.database.Cursor;

import android.os.Bundle;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AccountList extends ListActivity {
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

    private void setAccountData(Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
            this, R.layout.accountitem, data,
            new String[] { ExpenseDatabase.ACCOUNT_DESCRIPTION_COLUMN },
            new int[] { R.id.account_item_description });

        setListAdapter(adapter);
    }

    private void displayAccount(long id) {
        Intent intent = new Intent("org.barbon.acash.ACCOUNT_DETAIL");

        intent.putExtra(AccountDetail.ACCOUNT_ID, id);

        startActivity(intent);
    }
}
