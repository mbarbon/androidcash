package org.barbon.acash;

import android.content.Context;
import android.content.ContentValues;

import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpenseDatabase {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "expenses";

    private static final String ACCOUNTS_TABLE = "accounts";
    private static final String EXPENSES_TABLE = "expenses";

    public static final String GNUCASH_ACCOUNT_COLUMN = "gc_account";
    public static final String ACCOUNT_DESCRIPTION_COLUMN =
        "account_description";
    public static final String FROM_ACCOUNT_COLUMN = "account_from";
    public static final String TO_ACCOUNT_COLUMN = "account_to";
    public static final String DATE_COLUMN = "transaction_date";
    public static final String AMOUNT_COLUMN = "transaction_amount";
    public static final String TRANSACTION_DESCRIPTION_COLUMN =
        "transaction_description";

    private static ExpenseDatabase theInstance;

    private ExpenseOpenHelper openHelper;
    private SQLiteDatabase database;

    private ExpenseDatabase(Context context) {
        openHelper = new ExpenseOpenHelper(context);
    }

    public static ExpenseDatabase getInstance(Context context) {
        if (theInstance == null)
            theInstance = new ExpenseDatabase(context);

        return theInstance;
    }

    private SQLiteDatabase getDatabase() {
        if (database == null)
            database = openHelper.getWritableDatabase();

        return database;
    }

    public Cursor getFromAccountList() {
        SQLiteDatabase db = getDatabase();

        return db.rawQuery(
            "SELECT id AS _id, " + ACCOUNT_DESCRIPTION_COLUMN +
            "     FROM " + ACCOUNTS_TABLE +
            "     ORDER BY " + ACCOUNT_DESCRIPTION_COLUMN, null);
    }

    public Cursor getToAccountList() {
        SQLiteDatabase db = getDatabase();

        return db.rawQuery(
            "SELECT id AS _id, " + ACCOUNT_DESCRIPTION_COLUMN +
            "    FROM " + ACCOUNTS_TABLE +
            "    ORDER BY " + ACCOUNT_DESCRIPTION_COLUMN, null);
    }

    public boolean insertAccount(String description, String gnuCash) {
        SQLiteDatabase db = getDatabase();
        ContentValues vals = new ContentValues();

        vals.put(ACCOUNT_DESCRIPTION_COLUMN, description);
        vals.put(GNUCASH_ACCOUNT_COLUMN, gnuCash);

        return db.insert(ACCOUNTS_TABLE, null, vals) != -1;
    }

    private static class ExpenseOpenHelper extends SQLiteOpenHelper {
        private static final String ACCOUNTS_TABLE_CREATE =
            "CREATE TABLE " + ACCOUNTS_TABLE + " ( " +
            "id INTEGER PRIMARY KEY," +
            GNUCASH_ACCOUNT_COLUMN + " TEXT UNIQUE, " +
            ACCOUNT_DESCRIPTION_COLUMN + " TEXT UNIQUE" +
            ")";

        private static final String EXPENSES_TABLE_CREATE =
            "CREATE TABLE " + EXPENSES_TABLE + " ( " +
            "id INTEGER PRIMARY KEY," +
            FROM_ACCOUNT_COLUMN + " INTEGER, " +
            TO_ACCOUNT_COLUMN + " INTEGER, " +
            TRANSACTION_DESCRIPTION_COLUMN + " TEXT, " +
            DATE_COLUMN + " DATETIME, " +
            AMOUNT_COLUMN + " FLOAT," +
            "FOREIGN KEY(" + FROM_ACCOUNT_COLUMN + ") REFERENCES " +
                ACCOUNTS_TABLE + "(id)," +
            "FOREIGN KEY(" + FROM_ACCOUNT_COLUMN + ") REFERENCES " +
                ACCOUNTS_TABLE + "(id)" +
            ")";

        public ExpenseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(ACCOUNTS_TABLE_CREATE);
            db.execSQL(EXPENSES_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int from, int to) {
            throw new UnsupportedOperationException(
                "Unable to upgrade database");
        }
    }
}
