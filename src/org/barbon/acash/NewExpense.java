/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.app.Activity;
import android.app.Dialog;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.DatePicker;

import java.util.Date;

public class NewExpense extends Activity {
    private ExpenseView expenseView;

    private static final int ABOUT_DIALOG = 1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newexpense);

        expenseView = (ExpenseView) findViewById(R.id.expense_view);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // first-time only about dialog
        if (AboutDialog.showFirstTime(this))
            showDialog(ABOUT_DIALOG);
    }

    @Override
    protected Dialog onCreateDialog(int id, Bundle bundle) {
        if (id == ABOUT_DIALOG)
            return new AboutDialog(this);

        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);

        inflater.inflate(R.menu.newexpense, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.new_account:
            startActivity(Globals.NEW_ACCOUNT_INTENT);

            return true;
        case R.id.expense_list:
            startActivity(Globals.EXPENSE_LIST_INTENT);

            return true;
        case R.id.account_list:
            startActivity(Globals.ACCOUNT_LIST_INTENT);

            return true;
        case R.id.about_acash:
            showDialog(ABOUT_DIALOG);

            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    // event handlers

    public void onAddExpense(View v) {
        // add a new expense
        ExpenseDatabase db = ExpenseDatabase.getInstance(this);

        if (!db.insertExpense(expenseView.getExpenseAccountFrom(),
                              expenseView.getExpenseAccountTo(),
                              expenseView.getExpenseAmount(),
                              expenseView.getExpenseDate(),
                              expenseView.getExpenseDescription()))
            // TODO so something
            ;

        expenseView.clearExpenseAmount();
        expenseView.clearExpenseDescription();
    }
}
