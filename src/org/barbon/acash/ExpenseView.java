package org.barbon.acash;

import android.app.Activity;

import android.content.Context;
import android.content.ContentValues;

import android.database.Cursor;

import android.text.format.DateFormat;

import android.util.AttributeSet;

import android.view.View;

import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

public class ExpenseView extends LinearLayout {
    private EditText expenseAmount, expenseDescription;

    private TextView expenseDateView;
    private Date expenseDate;
    private Activity context;

    private View.OnClickListener dateClickListener;
    private View.OnClickListener dateClicked =
        new View.OnClickListener() {
            public void onClick(View v) {
                if (dateClickListener != null)
                    dateClickListener.onClick(ExpenseView.this);
            }
        };

    public ExpenseView(Context cxt, AttributeSet attrs) {
        super(cxt, attrs);

        context = (Activity) cxt; // TODO avoid cast or check if it is safe

        setOrientation(VERTICAL);
        inflate(context, R.layout.expenseview, this);

        ExpenseDatabase db = ExpenseDatabase.getInstance(context);

        setAccountData(R.id.from_account, db.getFromAccountList());
        setAccountData(R.id.to_account, db.getToAccountList());

        expenseDateView = (TextView) findViewById(R.id.expense_date);
        expenseAmount = (EditText) findViewById(R.id.expense_amount);
        expenseDescription = (EditText) findViewById(R.id.expense_description);

        expenseDateView.setOnClickListener(dateClicked);

        setExpenseDate(new Date());
        expenseAmount.setText("");
    }

    public void setExpenseId(long id) {
        ExpenseDatabase db = ExpenseDatabase.getInstance(context);
        ContentValues vals = db.getExpense(id);

        setExpenseDate(vals.getAsInteger(ExpenseDatabase.DATE_YEAR_COLUMN),
                       vals.getAsInteger(ExpenseDatabase.DATE_MONTH_COLUMN),
                       vals.getAsInteger(ExpenseDatabase.DATE_DAY_COLUMN));
        expenseAmount.setText(Double.toString(vals.getAsDouble(ExpenseDatabase.AMOUNT_COLUMN)));
        expenseDescription.setText(vals.getAsString(ExpenseDatabase.EXPENSE_DESCRIPTION_COLUMN));
        setAccountId(R.id.from_account, vals.getAsInteger(ExpenseDatabase.FROM_ACCOUNT_COLUMN));
        setAccountId(R.id.to_account, vals.getAsInteger(ExpenseDatabase.TO_ACCOUNT_COLUMN));
    }

    public void setDateClickListener(View.OnClickListener listener) {
        dateClickListener = listener;
    }

    // date accessors

    public int getExpenseYear() {
        return expenseDate.getYear() + 1900;
    }

    public int getExpenseMonth() {
        return expenseDate.getMonth();
    }

    public int getExpenseDay() {
        return expenseDate.getDate();
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date date) {
        expenseDate = date;

        java.text.DateFormat dateFormat =
            DateFormat.getDateFormat(context.getApplicationContext());

        expenseDateView.setText(dateFormat.format(expenseDate));
    }

    public void setExpenseDate(int year, int month, int day) {
        setExpenseDate(new Date(year - 1900, month, day));
    }

    // other accessors

    public double getExpenseAmount() {
        return Double.parseDouble(expenseAmount.getText().toString());
    }

    public String getExpenseDescription() {
        return expenseDescription.getText().toString();
    }

    public int getExpenseAccountFrom() {
        return getAccountId(R.id.from_account);
    }

    public int getExpenseAccountTo() {
        return getAccountId(R.id.to_account);
    }

    // implementation

    private void setAccountData(int id, Cursor data) {
        Spinner spinner = (Spinner) findViewById(id);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
            context, android.R.layout.simple_spinner_item, data,
            new String[] { ExpenseDatabase.ACCOUNT_DESCRIPTION_COLUMN },
            new int[] { android.R.id.text1 });
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item);

        context.startManagingCursor(data); // TODO deprecated

        spinner.setAdapter(adapter);
    }

    private int getAccountId(int id) {
        Spinner spinner = (Spinner) findViewById(id);

        return (int) spinner.getSelectedItemId();
    }

    private void setAccountId(int id, long rowId) {
        Spinner spinner = (Spinner) findViewById(id);
        CursorAdapter adapter = (CursorAdapter) spinner.getAdapter();

        for (int i = 0; i < adapter.getCount(); ++i)
            if (adapter.getItemId(i) == rowId)
            {
                spinner.setSelection(i);
                break;
            }
    }
}
