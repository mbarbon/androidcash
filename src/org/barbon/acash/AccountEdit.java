/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.view.View;

public abstract class AccountEdit extends ItemEdit {
    protected AccountView accountView;
    protected View actionButton;

    protected long accountId = -1;

    // enable/disable the action button
    protected AccountView.OnContentChangedListener onAccountChanged =
        new AccountView.OnContentChangedListener() {
            public void onContentChanged(AccountView view) {
                actionButton.setEnabled(view.isValidAccount(accountId));
                contentModified = true;
            }
        };
}
