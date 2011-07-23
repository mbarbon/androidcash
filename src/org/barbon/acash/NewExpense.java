/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.app.Activity;
import android.app.Dialog;

import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.DatePicker;

import java.io.File;

import java.util.Date;

public class NewExpense extends Activity {
    private ExpenseView expenseView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newexpense);

        expenseView = (ExpenseView) findViewById(R.id.expense_view);
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
            addNewAccount();

            return true;
        case R.id.expense_list:
            showExpenseList();

            return true;
        case R.id.account_list:
            showAccountList();

            return true;
        case R.id.export_expenses:
            exportExpenses();

            return true;
        case R.id.delete_expenses:
            deleteExpenses();

            return true;
        case R.id.about_acash:
        {
            Dialog about = new AboutDialog(this);

            about.show();

            return true;
        }
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void addNewAccount() {
        Intent intent = new Intent("org.barbon.acash.NEW_ACCOUNT");

        startActivity(intent);
    }

    private void showExpenseList() {
        Intent intent = new Intent("org.barbon.acash.EXPENSE_LIST");

        startActivity(intent);
    }

    private void showAccountList() {
        Intent intent = new Intent("org.barbon.acash.ACCOUNT_LIST");

        startActivity(intent);
    }

    private void exportExpenses() {
        File publicDir = Environment.getExternalStorageDirectory();
        File appDir = new File(publicDir, "AndroidCash");
        File qifFile = new File(appDir, "acash.qif"); // TODO config

        // TODO check file overwrite, directory creation, external storage
        appDir.mkdirs();

        ExpenseDatabase db = ExpenseDatabase.getInstance(this);

        if (!db.exportQif(qifFile))
            // TODO do something
            ;
    }

    private void deleteExpenses() {
        ExpenseDatabase db = ExpenseDatabase.getInstance(this);

        if (!db.deleteExpenses())
            // TODO do something
            ;
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
