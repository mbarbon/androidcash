/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import android.content.Intent;

public class Globals {
    // intents

    public static final Intent NEW_EXPENSE_INTENT =
        new Intent("org.barbon.acash.NEW_EXPENSE");

    public static final Intent NEW_ACCOUNT_INTENT =
        new Intent("org.barbon.acash.NEW_ACCOUNT");

    public static final Intent EXPENSE_LIST_INTENT =
        new Intent("org.barbon.acash.EXPENSE_LIST");

    public static final Intent ACCOUNT_LIST_INTENT =
        new Intent("org.barbon.acash.ACCOUNT_LIST");

    public static final Intent ACCOUNT_DETAILS_INTENT =
        new Intent("org.barbon.acash.ACCOUNT_DETAILS");

    public static final Intent EXPENSE_DETAILS_INTENT =
        new Intent("org.barbon.acash.EXPENSE_DETAILS");

    public static final Intent BROWSE_GNUCASH_FILES =
        new Intent("org.barbon.acash.BROWSE_GNUCASH_FILES");

    // formats

    public static final String NUMBER_FORMAT = "#.######";
}
