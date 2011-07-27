/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.app.AlertDialog;

import android.content.DialogInterface;

import org.barbon.acash.compat.CompatActivity;

public abstract class ItemEdit extends CompatActivity {
    protected boolean contentModified;

    // discard changes
    private DialogInterface.OnClickListener discardChanges =
        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int button) {
                finish();
            }
        };

    @Override
    public void onBackPressed() {
        if (contentModified)
        {
            AlertDialog.Builder confirm = new AlertDialog.Builder(this);

            confirm.setTitle(R.string.item_modified_title);
            confirm.setMessage(R.string.item_modified_message);
            confirm.setPositiveButton(R.string.discard_changes,
                                      discardChanges);
            confirm.setNegativeButton(R.string.cancel, null);

            confirm.show();
        } else
            super.onBackPressed();
    }
}
