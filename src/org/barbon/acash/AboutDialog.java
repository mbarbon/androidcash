/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.app.Dialog;

import android.content.Context;

import android.text.method.LinkMovementMethod;

import android.widget.TextView;

public class AboutDialog extends Dialog {
    public AboutDialog(Context context) {
        super(context);
        setTitle(R.string.about_title);
        setContentView(R.layout.about_dialog);

        TextView click1 = (TextView) findViewById(R.id.about_clickable_1);
        TextView click2 = (TextView) findViewById(R.id.about_clickable_2);

        click1.setMovementMethod(LinkMovementMethod.getInstance());
        click2.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
