package org.barbon.acash;

import android.app.ListActivity;

import android.database.Cursor;

import android.os.Bundle;

import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ExpenseList extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExpenseDatabase db = ExpenseDatabase.getInstance(this);

        setExpenseData(db.getExpenseList());

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
    }

    private void setExpenseData(Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
            this, R.layout.expenseitem, data,
            new String[] { ExpenseDatabase.AMOUNT_COLUMN,
                           ExpenseDatabase.TRANSACTION_DESCRIPTION_COLUMN },
            new int[] { R.id.expense_item_amount,
                        R.id.expense_item_description });

        setListAdapter(adapter);
    }
}
