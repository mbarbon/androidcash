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

    /* returns true if the dialog has not been shown yet */
    public static boolean showFirstTime(Context context) {
        SharedPreferences prefs =
            context.getSharedPreferences("global", Context.MODE_PRIVATE);

        return !prefs.getBoolean("displayed_about", false);
    }
}
