/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.view.View;

public abstract class ExpenseEdit extends ItemEdit {
    protected ExpenseView expenseView;
    protected View actionButton;

    protected long expenseId = -1;

    // enable/disable the action button
    protected ExpenseView.OnContentChangedListener onExpenseChanged =
        new ExpenseView.OnContentChangedListener() {
            public void onContentChanged(ExpenseView view) {
                actionButton.setEnabled(view.isValidExpense());
                contentModified = true;
            }
        };

    @Override
    public void onPause() {
        expenseView.disableNotifications();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        expenseView.enableNotifications();
    }
}
