package com.robotronix3550.robotronix_scouting_app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.robotronix3550.robotronix_scouting_app.data.ScoutContract.ScoutEntry;


/**
 * Database helper for Scouts app. Manages database creation and version management.
 */
public class ScoutDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ScoutDBHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "scoutingDatabse.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link ScoutDBHelper}.
     *
     * @param context of the app
     */
    public ScoutDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the Scouts table
        String SQL_CREATE_SCOUTS_TABLE =  "CREATE TABLE " + ScoutEntry.TABLE_NAME + " ("
                + ScoutEntry.SCOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ScoutEntry.COLUMN_SCOUT_MATCH + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_ROBOT + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_SCOUTER + " TEXT, "
                + ScoutEntry.COLUMN_SCOUT_AUTO_LINE + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_AUTO_SWITCH + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_AUTO_SCALE + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_AUTO_CUBE + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_TELE_EXCHANGE + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_TELE_ALLY_SWITCH + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_TELE_SCALE + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_TELE_ENEMY_SWITCH + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_TELE_CUBE + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_TELE_PORTAL + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_TELE_CLIMB + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_TELE_HELP_CLIMB + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_TELE_PARK + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_TELE_BROKEN + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_GAME_ALLY_SCORE + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_GAME_ENEMY_SCORE + " INTEGER NOT NULL DEFAULT 0, "
                + ScoutEntry.COLUMN_SCOUT_ROBOT_DRIVETRAIN + " TEXT, "
                + ScoutEntry.COLUMN_SCOUT_ROBOT_WEIGHT + " INTEGER NOT NULL DEFAULT 0 );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_SCOUTS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}