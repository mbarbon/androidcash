/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.app.AlertDialog;

import android.content.DialogInterface;

import android.os.Bundle;

import org.barbon.acash.compat.CompatActivity;

public abstract class ItemEdit extends CompatActivity {
    private static final String CONTENT_MODIFIED = "ITEM_EDIT_CONTENT_MODIFIED";

    protected boolean contentModified;

    // discard changes
    private DialogInterface.OnClickListener discardChanges =
        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int button) {
                finish();
            }
        };

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putBoolean(CONTENT_MODIFIED, contentModified);
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);

        contentModified = bundle.getBoolean(CONTENT_MODIFIED, false);
    }

    protected void retrieveContentModified(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            contentModified =
                savedInstanceState.getBoolean(CONTENT_MODIFIED, false);
        else
            contentModified = false;
    }

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
