/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;

import android.os.Bundle;

import android.view.View;

public class ExpenseDetails extends Activity {
    private ExpenseView expenseView;
    private long expenseId;

    private DialogInterface.OnClickListener deleteExpense =
        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int button) {
                ExpenseDatabase db =
                    ExpenseDatabase.getInstance(ExpenseDetails.this);

                // TODO check return value
                db.deleteExpense(expenseId);

                // exit
                finish();
            }
        };

    public static final String EXPENSE_ID = "expenseId";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expensedetail);

        expenseId = getIntent().getLongExtra(EXPENSE_ID, -1);;

        expenseView = (ExpenseView) findViewById(R.id.expense_view);
        expenseView.setExpenseId(expenseId);
    }

    // event handlers

    public void onUpdateExpense(View v) {
        // update the current expense
        ExpenseDatabase db = ExpenseDatabase.getInstance(this);

        db.updateExpense(expenseId,
                         expenseView.getExpenseAccountFrom(),
                         expenseView.getExpenseAccountTo(),
                         expenseView.getExpenseAmount(),
                         expenseView.getExpenseDate(),
                         expenseView.getExpenseDescription());
    }

    public void onDeleteExpense(View v) {
        AlertDialog.Builder confirm = new AlertDialog.Builder(this);

        confirm.setTitle(expenseView.getExpenseDescription());
        confirm.setMessage(R.string.alert_delete_expense);
        confirm.setPositiveButton(R.string.delete, deleteExpense);
        confirm.setNegativeButton(R.string.cancel, null);

        confirm.show();
    }
}
