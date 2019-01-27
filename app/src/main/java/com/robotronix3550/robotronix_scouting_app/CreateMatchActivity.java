package com.robotronix3550.robotronix_scouting_app;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
//import android.widget.Spinner;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.robotronix3550.robotronix_scouting_app.data.ScoutContract;
import com.robotronix3550.robotronix_scouting_app.data.ScoutInfo;


public class CreateMatchActivity extends AppCompatActivity {

    public static final String TAG = CreateMatchActivity.class.getSimpleName();

    /** EditText field to enter the scout's scouter name */
    private EditText mNameEditText;

    /** EditText field to enter the scout's match */
    private EditText mMatchEditText;

    /** EditText field to enter the scout's robot */
    private EditText mRobotEditText;

    private CheckBox mScheduledMatchCheckedBox;

    private ImageView mRobotImage;

    String mScouter;
    Integer mMatch;
    Integer mRobot;

    Integer db_id;
    Integer db_match;
    Integer db_robot;

    Boolean mScheduledMatch;

    ScoutInfo RobotInfo;

    private SharedPreferences mPrefs;

    public static final String PREFS_SCOUTER = "MyPrefScouterFile";

    // public static final String EXTRA_SCOUTER = "com.robotronix3550.robotronix_scouting_app.SCOUTER";

    // public static final String EXTRA_MATCH = "com.robotronix3550.robotronix_scouting_app.MATCH";

    public static final String EXTRA_ROBOT = "com.robotronix3550.robotronix_scouting_app.ROBOT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        // mPrefs = getPreferences(MODE_PRIVATE);
        mPrefs = getSharedPreferences(PREFS_SCOUTER, MODE_PRIVATE);
        mScouter = mPrefs.getString("PREF_SCOUTER", "Prenom");
        mMatch = mPrefs.getInt("PREF_MATCH", 0) + 1; // increment match number
        mScheduledMatch = mPrefs.getBoolean("PREF_SCHEDULED", false);

        String message = "Scouter un Match";

        // Capture the layout's TextView and set the string as its text
        TextView titleTextView = findViewById(R.id.titleMatchTextView);
        titleTextView.setText(message);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.scoutNameEditText);
        mMatchEditText = (EditText) findViewById(R.id.matchNoEditText);
        mRobotEditText = (EditText) findViewById(R.id.robotNoEditText);
        mScheduledMatchCheckedBox = findViewById(R.id.ScheduleMatchCheckedBox);
        mRobotImage = (ImageView) findViewById(R.id.RobotImageToScout);

        String[] column = null;  // return all columns
        String selection = null; // return all rows
        String[] selectionArgs = null; // not used
        String sortOrder = null;  // unordered

        if( mScheduledMatch ){

            Cursor cursor = getContentResolver().query(ScoutContract.ScoutEntry.CONTENT_URI, column, selection, selectionArgs, sortOrder);
            // Show a toast message depending on whether or not the insertion was successful
            if (cursor == null) {

                Toast.makeText(this, getString(R.string.queryDB_scout_failed),
                        Toast.LENGTH_LONG).show();
            } else {

                try{
                    if (cursor.moveToFirst()) {
                        while ( !cursor.isAfterLast() ) {
                            db_id = cursor.getInt(0);
                            db_match = cursor.getInt(1);
                            db_robot = cursor.getInt(2);

                            if( db_match == mMatch && db_robot == mRobot ) {
                                RobotInfo = new ScoutInfo(db_id, db_match, db_robot);
                                break;
                            } else {
                                cursor.moveToNext();
                            }
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

            if (RobotInfo != null ) {

                mMatchEditText.setText(RobotInfo.getMatch().toString());
                mRobotEditText.setText(RobotInfo.getRobot().toString());
                mRobotImage.setImageResource(RobotInfo.getRobotImageId());

            } else {

                mMatchEditText.setText(mMatch);
                mRobotImage.setImageResource(R.drawable.robotronixlogo);

            }

        } else {
            mMatchEditText.setText(mMatch.toString());
            mRobotImage.setImageResource(R.drawable.robotronixlogo);
        }



        // Display other values on screen
        mNameEditText.setText(mScouter);

        mRobotEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here

                // yourEditText...
                //changeRobotImage();
                String robotString = mRobotEditText.getText().toString().trim();
                String matchString = mMatchEditText.getText().toString().trim();

                if (!robotString.equals("")) {
                    mRobot = Integer.parseInt(robotString);
                    mMatch = Integer.parseInt(matchString);

                    ScoutInfo RobotInfo = new ScoutInfo(0, mMatch, mRobot);
                    mRobotImage.setImageResource(RobotInfo.getRobotImageId());
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });


    }


    /**
     * Get user input from editor and save new scout into database.
     */
/*
    public void changeRobotImage() {

        String robotString = mRobotEditText.getText().toString().trim();
        String matchString = mMatchEditText.getText().toString().trim();

        mRobot = Integer.parseInt(robotString);
        mMatch = Integer.parseInt(matchString);

        ScoutInfo RobotInfo = new ScoutInfo(0, mMatch, mRobot);
        mRobotImage.setImageResource(RobotInfo.getRobotImageId());

    }
*/
    /**
     * Get user input from editor and save new scout into database.
     */
    public void createMatch(View view) {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        mScouter = mNameEditText.getText().toString().trim();
        String matchString = mMatchEditText.getText().toString().trim();
        String robotString = mRobotEditText.getText().toString().trim();

        if (matchString.equals("")) {

            Toast.makeText(this, getString(R.string.missing_match_failed),
                    Toast.LENGTH_LONG).show();

        } else if (matchString.equals("0")) {

            Toast.makeText(this, getString(R.string.create_match_failed),
                    Toast.LENGTH_LONG).show();

        } else if (robotString.equals("")) {

            Toast.makeText(this, getString(R.string.missing_robot_failed),
                    Toast.LENGTH_LONG).show();

        } else if (mScouter.equals("")) {
            Toast.makeText(this, getString(R.string.missing_scouter_failed),
                    Toast.LENGTH_LONG).show();

        }else {

            mMatch = Integer.parseInt(matchString);
            mRobot = Integer.parseInt(robotString);

            SharedPreferences.Editor ed = mPrefs.edit();
            ed.putString("PREF_SCOUTER", mScouter);
            ed.putInt("PREF_MATCH", mMatch);
            ed.commit();

            Intent intent = new Intent(this, ScoutMatchActivity.class);
            // intent.putExtra(EXTRA_SCOUTER, mScouter);
            // intent.putExtra(EXTRA_MATCH, match);
            intent.putExtra(EXTRA_ROBOT, mRobot);

            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed(){

        // Intent intent = new Intent(this, MainActivity.class);
        // mScouter = mNameEditText.getText().toString().trim();
        // intent.putExtra(EXTRA_SCOUTER, mScouter);

        SharedPreferences.Editor ed = mPrefs.edit();
        mScouter = mNameEditText.getText().toString().trim();

        String matchString = mMatchEditText.getText().toString().trim();
        mMatch = Integer.parseInt(matchString);

        ed.putString("PREF_SCOUTER", mScouter);
        ed.putInt("PREF_MATCH", mMatch);
        ed.commit();

        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

}
