/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.app.AlertDialog;

import android.content.DialogInterface;

import android.os.Bundle;

import android.view.View;

public class AccountDetails extends AccountEdit {
    public static final String ACCOUNT_ID = "accountId";

    private DialogInterface.OnClickListener deleteAccount =
        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int button) {
                ExpenseDatabase db =
                    ExpenseDatabase.getInstance(AccountDetails.this);

                // TODO check return value
                db.deleteAccountAndExpenses(accountId);

                // exit
                finish();
            }
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountdetail);

        accountView = (AccountView) findViewById(R.id.account_view);
        actionButton = findViewById(R.id.update_account);

        accountView.setOnContentChangedListener(onAccountChanged);

        accountId = getIntent().getLongExtra(ACCOUNT_ID, -1);

        accountView.setAccountId(accountId);

        contentModified = false;
    }

    // event handlers

    public void onUpdateAccount(View view) {
        ExpenseDatabase db = ExpenseDatabase.getInstance(this);

        if (!db.updateAccount(accountId,
                              accountView.getAccountDescription(),
                              accountView.getGnuCashAccount()))
            // TODO do something
            ;

        contentModified = false;
    }

    public void onDeleteAccount(View v) {
        ExpenseDatabase db = ExpenseDatabase.getInstance(this);

        if (db.accountHasExpenses(accountId)) {
            AlertDialog.Builder deny = new AlertDialog.Builder(this);

            deny.setTitle(accountView.getAccountDescription());
            deny.setMessage(R.string.alert_account_has_expenses);
            deny.setNegativeButton(R.string.cancel, null);

            deny.show();
        }
        else {
            AlertDialog.Builder confirm = new AlertDialog.Builder(this);

            confirm.setTitle(accountView.getAccountDescription());
            confirm.setMessage(R.string.alert_delete_account);
            confirm.setPositiveButton(R.string.delete, deleteAccount);
            confirm.setNegativeButton(R.string.cancel, null);

            confirm.show();
        }
    }
}
