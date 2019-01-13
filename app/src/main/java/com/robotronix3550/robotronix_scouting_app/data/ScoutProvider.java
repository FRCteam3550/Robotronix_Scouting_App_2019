package com.robotronix3550.robotronix_scouting_app.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.robotronix3550.robotronix_scouting_app.data.ScoutContract.ScoutEntry;

import java.io.IOException;

/**
 * {@link ContentProvider} for Scouts app.
 */
public class ScoutProvider extends ContentProvider {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = ScoutProvider.class.getSimpleName();

    /**
     * URI matcher code for the content URI for the scouts table
     */
    private static final int SCOUTS = 100;

    /**
     * URI matcher code for the content URI for a single scout in the scouts table
     */
    private static final int SCOUT_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.example.android.scouts/scouts" will map to the
        // integer code {@link #SCOUTS}. This URI is used to provide access to MULTIPLE rows
        // of the scouts table.
        sUriMatcher.addURI(ScoutContract.CONTENT_AUTHORITY, ScoutContract.PATH_SCOUTS, SCOUTS);

        // The content URI of the form "content://com.example.android.scouts/scouts/#" will map to the
        // integer code {@link #SCOUT_ID}. This URI is used to provide access to ONE single row
        // of the scouts table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.scouts/scouts/3" matches, but
        // "content://com.example.android.scouts/scouts" (without a number at the end) doesn't match.
        sUriMatcher.addURI(ScoutContract.CONTENT_AUTHORITY, ScoutContract.PATH_SCOUTS + "/#", SCOUT_ID);
    }

    /**
     * Database helper object
     */
    private ScoutDBHelper mDbHelper;

    SqliteExporter dbExport;


    @Override
    public boolean onCreate() {
        mDbHelper = new ScoutDBHelper(getContext());
        dbExport = new SqliteExporter();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case SCOUTS:
                // For the SCOUTS code, query the scouts table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the scouts table.
                cursor = database.query(ScoutEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case SCOUT_ID:
                // For the SCOUT_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.scouts/scouts/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                // Doesn't work
                selection = ScoutEntry._ID + "=?";
                // selection = ScoutEntry._ID + "=" + String.valueOf(ContentUris.parseId(uri));

                // Doesn't work
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                //selectionArgs = null;

                // This will perform a query on the scouts table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(ScoutEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SCOUTS:
                return insertScout(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a scout into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertScout(Uri uri, ContentValues values) {

        // check that it's greater than or equal to 0
        Integer match = values.getAsInteger(ScoutEntry.COLUMN_SCOUT_MATCH);
        if (match != null && match < 0) {
            throw new IllegalArgumentException("Scout requires valid match");
        }

        // Check that the robot is valid
        Integer robot = values.getAsInteger(ScoutEntry.COLUMN_SCOUT_ROBOT);
        if (robot == null || !ScoutEntry.isValidRobot(robot)) {
            throw new IllegalArgumentException("Scout requires valid robot");
        }

        // Check that the name is not null
        String scouter = values.getAsString(ScoutEntry.COLUMN_SCOUT_SCOUTER);
        if (scouter == null) {
            throw new IllegalArgumentException("Scout requires a scouter name");
        }

        // No need to check the breed, any value is valid (including null).

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new scout with the given values
        long id = database.insert(ScoutEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SCOUTS:
                return updateScout(uri, contentValues, selection, selectionArgs);
            case SCOUT_ID:
                // For the SCOUT_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = ScoutEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateScout(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update scouts in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more scouts).
     * Return the number of rows that were successfully updated.
     */
    private int updateScout(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link ScoutEntry#COLUMN_SCOUT_NAME} key is present,
        // check that the name value is not null.
        if (values.containsKey(ScoutEntry.COLUMN_SCOUT_SCOUTER)) {
            String name = values.getAsString(ScoutEntry.COLUMN_SCOUT_SCOUTER);
            if (name == null) {
                throw new IllegalArgumentException("Scout requires a name");
            }
        }

        // If the {@link ScoutEntry#COLUMN_SCOUT_ROBOT} key is present,
        // check that the robot value is valid.
        if (values.containsKey(ScoutEntry.COLUMN_SCOUT_ROBOT)) {
            Integer robot = values.getAsInteger(ScoutEntry.COLUMN_SCOUT_ROBOT);
            if (robot == null || !ScoutEntry.isValidRobot(robot)) {
                throw new IllegalArgumentException("Scout requires valid robot");
            }
        }

        // If the {@link ScoutEntry#COLUMN_SCOUT_MATCH} key is present,
        // check that the weight value is valid.
        if (values.containsKey(ScoutEntry.COLUMN_SCOUT_MATCH)) {
            // Check that the weight is greater than or equal to 0 kg
            Integer match = values.getAsInteger(ScoutEntry.COLUMN_SCOUT_MATCH);
            if (match != null && match < 0) {
                throw new IllegalArgumentException("Scout requires valid match");
            }
        }

        // No need to check the breed, any value is valid (including null).

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Returns the number of database rows affected by the update statement
        return database.update(ScoutEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SCOUTS:
                // Delete all rows that match the selection and selection args
                return database.delete(ScoutEntry.TABLE_NAME, selection, selectionArgs);
            case SCOUT_ID:
                // Delete a single row given by the ID in the URI
                selection = ScoutEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return database.delete(ScoutEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SCOUTS:
                return ScoutEntry.CONTENT_LIST_TYPE;
            case SCOUT_ID:
                return ScoutEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Override
    public Bundle call(String method, String arg, Bundle extras) {

        Bundle bu = new Bundle();

        if(method.equals("exportDB")) {
            // Do whatever it is you need to do
            exportDB( arg );
            return bu;
        } else {
            return null;
        }
    }
    public void exportDB(String FileName ) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        try {
            dbExport.export(db, FileName);
            Log.e(LOG_TAG, "Called export on sqliteExporter");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}


