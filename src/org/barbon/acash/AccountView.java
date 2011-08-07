/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.content.ContentValues;
import android.content.Context;

import android.text.Editable;
import android.text.TextWatcher;

import android.util.AttributeSet;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AccountView extends LinearLayout {
    private EditText description, gnuCash;
    private CheckBox hidden;

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

    private class CheckBoxChanged
        implements CompoundButton.OnCheckedChangeListener {
        public void onCheckedChanged(CompoundButton button, boolean isChecked) {
            contentChanged();
        }
    }

    public AccountView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOrientation(VERTICAL);
        inflate(context, R.layout.accountview, this);

        description = (EditText) findViewById(R.id.account_description);
        gnuCash = (EditText) findViewById(R.id.gnucash_account);
        hidden = (CheckBox) findViewById(R.id.account_hidden);

        TextWatcher textChanged = new TextContentChanged();

        description.addTextChangedListener(textChanged);
        gnuCash.addTextChangedListener(textChanged);
        hidden.setOnCheckedChangeListener(new CheckBoxChanged());
    }

    public void setAccountId(long id) {
        ExpenseDatabase db = ExpenseDatabase.getInstance(getContext());
        ContentValues vals = db.getAccount(id);

        setAccountDescription(
            (String) vals.get(ExpenseDatabase.ACCOUNT_DESCRIPTION_COLUMN));
        setGnuCashAccount(
            (String) vals.get(ExpenseDatabase.GNUCASH_ACCOUNT_COLUMN));
        setAccountHidden(
            (boolean) vals.getAsBoolean(ExpenseDatabase.ACCOUNT_HIDDEN_COLUMN));
    }

    public void showAccountHidden(boolean show) {
        hidden.setVisibility(show ? VISIBLE : GONE);
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

    public boolean isAccountHidden() {
        return hidden.isChecked();
    }

    public void setAccountHidden(boolean hide) {
        hidden.setChecked(hide);
    }

    public boolean isValidAccount() {
        return isValidAccount(-1);
    }

    public boolean isValidAccount(long skipId) {
        String acct = getGnuCashAccount(), descr = getAccountDescription();

        if (acct.length() == 0 || descr.length() == 0)
            return false;

        ExpenseDatabase db = ExpenseDatabase.getInstance(getContext());

        return !db.isAccountDuplicate(descr, acct, skipId);
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
