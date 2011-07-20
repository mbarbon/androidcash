package org.barbon.acash;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.ContentValues;
import android.content.DialogInterface;

import android.database.Cursor;

import android.os.Bundle;

import android.view.View;

import android.widget.DatePicker;
import android.widget.EditText;

public class ExpenseDetail extends Activity {
    private ExpenseView expenseView;
    private long expenseId;

    private static final int DATE_DIALOG_ID = 0;

    public static final String EXPENSE_ID = "expenseId";

    // TODO duplicate code

    // set the expense date
    private DatePickerDialog.OnDateSetListener dateSet =
        new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int month, int day) {
                expenseView.setExpenseDate(year, month, day);
            }
        };

    // reset the dialog date when the user closes it
    private DialogInterface.OnDismissListener dateDismissed =
        new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                removeDialog(DATE_DIALOG_ID);
            }
        };

    // popup date dialog
    private View.OnClickListener dateClicked =
        new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expensedetail);

        expenseId = getIntent().getLongExtra(EXPENSE_ID, -1);;

        expenseView = (ExpenseView) findViewById(R.id.expense_view);
        expenseView.setDateClickListener(dateClicked);
        expenseView.setExpenseId(expenseId);
    }

    // TODO duplicate code
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            DatePickerDialog dateDialog =
                new DatePickerDialog(this, dateSet,
                                     expenseView.getExpenseYear(),
                                     expenseView.getExpenseMonth(),
                                     expenseView.getExpenseDay());

            dateDialog.setOnDismissListener(dateDismissed);

            return dateDialog;
        }

        return null;
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
