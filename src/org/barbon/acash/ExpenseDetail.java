package org.barbon.acash;

import android.app.Activity;

import android.content.ContentValues;

import android.database.Cursor;

import android.os.Bundle;

import android.view.View;

import android.widget.EditText;

public class ExpenseDetail extends Activity {
    public static final String EXPENSE_ID = "expenseId";

    private EditText description, gnuCash;
    private long accountId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expensedetail);
    }
}
