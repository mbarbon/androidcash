/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.os.Bundle;

import android.view.View;

public class NewAccount extends AccountEdit {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newaccount);

        accountView = (AccountView) findViewById(R.id.account_view);
        actionButton = findViewById(R.id.add_account);

        accountView.setOnContentChangedListener(onAccountChanged);

        accountView.setAccountDescription("");
        accountView.setGnuCashAccount("");
        displayHelpMessage();

        retrieveContentModified(savedInstanceState);
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

        contentModified = false;
    }
}