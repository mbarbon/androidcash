/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.app.Activity;

import android.os.Bundle;

import android.view.View;

public class NewAccount extends Activity {
    private AccountView accountView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newaccount);

        accountView = (AccountView) findViewById(R.id.account_view);
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
    }
}