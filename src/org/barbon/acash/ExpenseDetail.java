/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.app.Activity;

import android.database.Cursor;

import android.os.Bundle;

import android.view.View;

public class ExpenseDetail extends Activity {
    private ExpenseView expenseView;
    private long expenseId;

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
}
