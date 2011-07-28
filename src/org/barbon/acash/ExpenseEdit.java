/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.app.Activity;

import android.view.View;

public abstract class ExpenseEdit extends Activity {
    protected ExpenseView expenseView;
    protected View actionButton;

    protected long expenseId = -1;
    protected boolean contentModified;

    // enable/disable the action button
    protected ExpenseView.OnContentChangedListener onExpenseChanged =
        new ExpenseView.OnContentChangedListener() {
            public void onContentChanged(ExpenseView view) {
                actionButton.setEnabled(view.isValidExpense());
                contentModified = true;
            }
        };
}
