package com.robotronix3550.robotronix_scouting_app;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.robotronix3550.robotronix_scouting_app.data.ScoutContract;
import com.robotronix3550.robotronix_scouting_app.data.ScoutDBHelper;
import com.robotronix3550.robotronix_scouting_app.data.ScoutInfo;
import com.robotronix3550.robotronix_scouting_app.data.ScoutProvider;

import java.util.ArrayList;
import java.util.List;

public class ReviewMatchActivity extends AppCompatActivity {

    public static final String EXTRA_DB_ID = "com.robotronix3550.robotronix_scouting_app.DB_ID";

    public static final String TAG = ReviewMatchActivity.class.getSimpleName();
    private ScoutDBHelper mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_match);

        // Create an ArrayList of AndroidFlavor objects
        final ArrayList<ScoutInfo> ReviewInfos = new ArrayList<ScoutInfo>();

        String[] column = null;  // return all columns
        String selection = null; // return all rows
        String[] selectionArgs = null; // not used
        String sortOrder = null;  // unordered
        Integer db_id;
        Integer db_match;
        Integer db_robot;
        Cursor cursor = getContentResolver().query(ScoutContract.ScoutEntry.CONTENT_URI, column, selection, selectionArgs, sortOrder);

        // Show a toast message depending on whether or not the insertion was successful
        if (cursor == null) {

            ReviewInfos.add(new ScoutInfo(0, 10, 3550));
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.queryDB_scout_failed),
                    Toast.LENGTH_LONG).show();
        } else {

            try{
                if (cursor.moveToFirst()) {
                    while ( !cursor.isAfterLast() ) {
                        db_id = cursor.getInt(0);
                        db_match = cursor.getInt( 1);
                        db_robot = cursor.getInt(2);
                        ReviewInfos.add(new ScoutInfo(db_id, db_match, db_robot));
                        cursor.moveToNext();
                    }
                }
            }
            catch(Exception throwable){
                Log.e(TAG, "Could not get the table names from db", throwable);
            }
            finally{
                if(cursor!=null)
                    cursor.close();
            }

        }

        // Create an {@link AndroidFlavorAdapter}, whose data source is a list of
        // {@link AndroidFlavor}s. The adapter knows how to create list item views for each item
        // in the list.
        ScoutAdapter InfoAdapter = new ScoutAdapter(this, ReviewInfos);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) findViewById(R.id.listview_scoutInfos);
        listView.setAdapter(InfoAdapter);

        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                // Get the {@link Word} object at the given position the user clicked on
                ScoutInfo info = ReviewInfos.get(position);

                if( info.getMatch() == 0 ) {

                    Intent intent = new Intent(getBaseContext(), ScoutPitActivity.class);
                    // intent.putExtra(EXTRA_DB_ID, info.getDb_id());
                    Log.d("REVIEW", "position : " + position);
                    Log.d("REVIEW", "id : " + id);
                    Log.d("REVIEW", "info match : " + info.getMatch());
                    Log.d("REVIEW", "info robot : " + info.getRobot());

                    Uri currentScoutUri = ContentUris.withAppendedId(ScoutContract.ScoutEntry.CONTENT_URI, (position+1));
                    intent.setData(currentScoutUri);
                    Log.d("REVIEW", "uri : " + currentScoutUri.toString());

                    startActivity(intent);
                } else {

                    Intent intent = new Intent(getBaseContext(), ScoutMatchActivity.class);
                    Log.d("REVIEW", "position : " + position);
                    Log.d("REVIEW", "id : " + id);
                    Log.d("REVIEW", "info match : " + info.getMatch());
                    Log.d("REVIEW", "info robot : " + info.getRobot());

                    Uri currentScoutUri = ContentUris.withAppendedId(ScoutContract.ScoutEntry.CONTENT_URI, (position+1));
                    intent.setData(currentScoutUri);
                    Log.d("REVIEW", "uri : " + currentScoutUri.toString());

                    //intent.putExtra(EXTRA_DB_ID, info.getDb_id());
                    startActivity(intent);

                }
                //Toast.makeText(this, "hello match : ", //  + info.getMatch().toString(),      // getString(R.string.queryDB_scout_failed),
                //        Toast.LENGTH_LONG).show();

            }
        });

    }


}