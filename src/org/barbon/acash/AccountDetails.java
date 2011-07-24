/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.ContentValues;
import android.content.DialogInterface;

import android.os.Bundle;

import android.view.View;

public class AccountDetails extends Activity {
    public static final String ACCOUNT_ID = "accountId";

    private AccountView accountView;
    private long accountId;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountdetail);

        accountView = (AccountView) findViewById(R.id.account_view);

        accountId = getIntent().getLongExtra(ACCOUNT_ID, -1);

        ExpenseDatabase db = ExpenseDatabase.getInstance(this);
        ContentValues vals = db.getAccount(accountId);

        accountView.setAccountDescription(
            (String) vals.get(ExpenseDatabase.ACCOUNT_DESCRIPTION_COLUMN));
        accountView.setGnuCashAccount(
            (String) vals.get(ExpenseDatabase.GNUCASH_ACCOUNT_COLUMN));
    }

    // event handlers

    public void onUpdateAccount(View view) {
        ExpenseDatabase db = ExpenseDatabase.getInstance(this);

        if (!db.updateAccount(accountId,
                              accountView.getAccountDescription(),
                              accountView.getGnuCashAccount()))
            // TODO do something
            ;
    }

    public void onDeleteAccount(View v) {
        AlertDialog.Builder confirm = new AlertDialog.Builder(this);

        confirm.setTitle(accountView.getAccountDescription());
        confirm.setMessage(R.string.alert_delete_account);
        confirm.setPositiveButton(R.string.delete, deleteAccount);
        confirm.setNegativeButton(R.string.cancel, null);

        confirm.show();
    }
}
