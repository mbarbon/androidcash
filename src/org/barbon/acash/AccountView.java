/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.content.Context;

import android.text.Editable;
import android.text.TextWatcher;

import android.util.AttributeSet;

import android.widget.EditText;
import android.widget.LinearLayout;

public class AccountView extends LinearLayout {
    private EditText description, gnuCash;

    public interface OnContentChangedListener {
        public void onContentChanged(AccountView view);
    }

    private OnContentChangedListener contentChangedListener;

    // TODO duplicate in ExpenseView
    // watch account/description changes
    private class TextContentChanged implements TextWatcher {
        public void afterTextChanged(Editable s) {
            contentChanged();
        }

        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
            // nothing to do
        }

        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {
            // nothing to do
        }
    }

    public AccountView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOrientation(VERTICAL);
        inflate(context, R.layout.accountview, this);

        description = (EditText) findViewById(R.id.account_description);
        gnuCash = (EditText) findViewById(R.id.gnucash_account);

        TextWatcher textChanged = new TextContentChanged();

        description.addTextChangedListener(textChanged);
        gnuCash.addTextChangedListener(textChanged);
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

    public void setOnContentChangedListener(OnContentChangedListener listener) {
        contentChangedListener = listener;
    }

    // implementation

    private void contentChanged() {
        if (contentChangedListener != null)
            contentChangedListener.onContentChanged(this);
    }
}
