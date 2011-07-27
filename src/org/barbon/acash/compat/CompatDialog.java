package org.barbon.acash.compat;

import android.app.Dialog;

import android.content.Context;

import android.view.KeyEvent;

public class CompatDialog extends Dialog {
    public CompatDialog(Context context) {
        super(context);
    }

    // emulate onBackPressed for Android 1.6
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK)
            return super.onKeyDown(keyCode, event);

        onBackPressed();

        return true;
    }

    public void onBackPressed() {
        cancel();
    }
}
