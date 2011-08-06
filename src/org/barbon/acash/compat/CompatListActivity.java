package org.barbon.acash.compat;

import android.app.ListActivity;
import android.app.Dialog;

import android.os.Bundle;

import android.view.KeyEvent;

public class CompatListActivity extends ListActivity {
    // emulate onBackPressed for Android 1.6
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK)
            return super.onKeyDown(keyCode, event);

        onBackPressed();

        return true;
    }

    public void onBackPressed() {
        finish();
    }

    // Android 1.6 compatibility
    protected Dialog onCreateDialog(int id) {
        return onCreateDialog(id, null);
    }

    protected Dialog onCreateDialog(int id, Bundle bundle) {
        return null;
    }
}
