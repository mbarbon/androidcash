package org.barbon.acash;

import android.app.Activity;

import android.content.ContentValues;

import android.database.Cursor;

import android.os.Bundle;

import android.view.View;

import android.widget.EditText;

public class AccountDetail extends Activity {
    public static final String ACCOUNT_ID = "accountId";

    private EditText description, gnuCash;
    private long accountId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountdetail);

        description = (EditText) findViewById(R.id.account_description);
        gnuCash = (EditText) findViewById(R.id.gnucash_account);

        accountId = getIntent().getLongExtra(ACCOUNT_ID, -1);

        ExpenseDatabase db = ExpenseDatabase.getInstance(this);
        ContentValues vals = db.getAccount(accountId);

        description.setText(
            (String) vals.get(ExpenseDatabase.ACCOUNT_DESCRIPTION_COLUMN));
        gnuCash.setText(
            (String) vals.get(ExpenseDatabase.GNUCASH_ACCOUNT_COLUMN));
    }
}
