package org.barbon.acash;

import android.app.Activity;

import android.os.Bundle;

import android.view.View;

import android.widget.EditText;

public class NewAccount extends Activity {
    private EditText description, gnuCash;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newaccount);

        description = (EditText) findViewById(R.id.account_description);
        gnuCash = (EditText) findViewById(R.id.gnucash_account);
    }

    // event handlers

    public void onAddAccount(View v) {
        ExpenseDatabase db = ExpenseDatabase.getInstance(this);

        if (!db.insertAccount(description.getText().toString(),
                              gnuCash.getText().toString()))
            // TODO do something
            ;

        description.setText("");
        gnuCash.setText("");
    }
}