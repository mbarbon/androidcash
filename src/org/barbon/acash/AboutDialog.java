/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.app.Dialog;

import android.content.Context;
import android.content.SharedPreferences;

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

    public void onBackPressed() {
        // saves "displayed_about" more oft than needed, but it should
        // not be a problem
        SharedPreferences prefs =
            getContext().getSharedPreferences("global", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean("displayed_about", true);
        editor.commit();

        super.onBackPressed();
    }

    public static Dialog showDialog(Context context, boolean firstTime) {
        if (firstTime) {
            SharedPreferences prefs =
                context.getSharedPreferences("global", Context.MODE_PRIVATE);

            if (prefs.getBoolean("displayed_about", false))
                return null;
        }

        return new AboutDialog(context);
    }
}
