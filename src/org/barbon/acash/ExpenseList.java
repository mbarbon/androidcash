package org.barbon.acash;

import android.app.ListActivity;

import android.content.Intent;

import android.database.Cursor;

import android.os.Bundle;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ExpenseList extends ListActivity {
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

        startManagingCursor(data); // TODO deprecated
        setListAdapter(adapter);
    }

    private void displayExpense(long id) {
        Intent intent = new Intent("org.barbon.acash.EXPENSE_DETAIL");

        intent.putExtra(ExpenseDetail.EXPENSE_ID, id);

        startActivity(intent);
    }
}
