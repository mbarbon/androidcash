/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.app.ListActivity;

import android.content.Intent;

import android.database.Cursor;

import android.os.Bundle;
import android.os.Environment;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.File;

import java.text.DecimalFormat;

public class ExpenseList extends ListActivity {
    // better format for the amount
    private static class SimpleBinder
            implements SimpleCursorAdapter.ViewBinder {
        DecimalFormat format = new DecimalFormat(Globals.NUMBER_FORMAT);

        public boolean setViewValue(View view, Cursor cursor, int column) {
            if (view.getId() != R.id.expense_item_amount)
                return false;

            TextView textView = (TextView) view;

            textView.setText(format.format(cursor.getDouble(column)));

            return true;
        }
    }

    private static final SimpleBinder VIEW_BINDER = new SimpleBinder();

    private AdapterView.OnItemClickListener clickListener =
        new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long rowId) {
                displayExpense(rowId);
            }
        };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExpenseDatabase db = ExpenseDatabase.getInstance(this);

        setExpenseData(db.getExpenseList());

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(clickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);

        inflater.inflate(R.menu.expenselist, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.new_expense:
            // go back to the initial activity
            finish();

            return true;
        case R.id.export_expenses:
            exportExpenses();

            return true;
        case R.id.delete_expenses:
            deleteExpenses();

            // close the activity since there are no more expenses
            finish();

            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    // implementation

    private void setExpenseData(Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
            this, R.layout.expenseitem, data,
            new String[] { ExpenseDatabase.AMOUNT_COLUMN,
                           ExpenseDatabase.EXPENSE_DESCRIPTION_COLUMN },
            new int[] { R.id.expense_item_amount,
                        R.id.expense_item_description });

        adapter.setViewBinder(VIEW_BINDER);
        startManagingCursor(data); // TODO deprecated
        setListAdapter(adapter);
    }

    private void displayExpense(long id) {
        Intent intent = new Intent(Globals.EXPENSE_DETAILS_INTENT);

        intent.putExtra(ExpenseDetails.EXPENSE_ID, id);

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
}
