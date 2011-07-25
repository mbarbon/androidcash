/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.app.Activity;

import android.database.Cursor;

import android.os.Bundle;

import android.view.View;

public class NewAccount extends Activity {
    private AccountView accountView;
    private View addButton;

    // enable/disable the 'add account' button
    private AccountView.OnContentChangedListener onAccountChanged =
        new AccountView.OnContentChangedListener() {
            public void onContentChanged(AccountView view) {
                addButton.setEnabled(view.isValidAccount());
            }
        };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newaccount);

        accountView = (AccountView) findViewById(R.id.account_view);
        addButton = findViewById(R.id.add_account);

        accountView.setOnContentChangedListener(onAccountChanged);

        accountView.setAccountDescription("");
        accountView.setGnuCashAccount("");
        displayHelpMessage();
    }

    // implementation

    private void displayHelpMessage() {
        View help = findViewById(R.id.new_account_at_least_2);

        ExpenseDatabase db = ExpenseDatabase.getInstance(this);

        help.setVisibility(db.getAccountCount() >= 2 ? View.GONE :
                                                       View.VISIBLE);
    }

    // event handlers

    public void onAddAccount(View v) {
        ExpenseDatabase db = ExpenseDatabase.getInstance(this);

        if (!db.insertAccount(accountView.getAccountDescription(),
                              accountView.getGnuCashAccount()))
            // TODO do something
            ;

        accountView.setAccountDescription("");
        accountView.setGnuCashAccount("");
        displayHelpMessage();
    }
}