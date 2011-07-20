package org.barbon.acash;

import android.app.ListActivity;

import android.content.Intent;

import android.database.Cursor;

import android.os.Bundle;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ExpenseList extends ListActivity {
    // better format for the amount
    private static class SimpleBinder
            implements SimpleCursorAdapter.ViewBinder {
        DecimalFormat format = new DecimalFormat("#.######");

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
        Intent intent = new Intent("org.barbon.acash.EXPENSE_DETAIL");

        intent.putExtra(ExpenseDetail.EXPENSE_ID, id);

        startActivity(intent);
    }
}
