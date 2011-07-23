/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.content.Context;

import android.util.AttributeSet;

import android.widget.EditText;
import android.widget.LinearLayout;

public class AccountView extends LinearLayout {
    private EditText description, gnuCash;

    public AccountView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOrientation(VERTICAL);
        inflate(context, R.layout.accountview, this);

        description = (EditText) findViewById(R.id.account_description);
        gnuCash = (EditText) findViewById(R.id.gnucash_account);
    }

    // accessors

    public String getAccountDescription() {
        return description.getText().toString();
    }

    public void setAccountDescription(String descr) {
        description.setText(descr);
    }

    public String getGnuCashAccount() {
        return gnuCash.getText().toString();
    }

    public void setGnuCashAccount(String name) {
        gnuCash.setText(name);
    }
}
