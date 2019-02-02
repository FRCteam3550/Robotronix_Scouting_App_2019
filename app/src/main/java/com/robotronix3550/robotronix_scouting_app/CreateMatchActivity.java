package com.robotronix3550.robotronix_scouting_app;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
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
    Integer mScheduledMatch;

    ScoutInfo RobotInfo;

    private SharedPreferences mPrefs;

    Uri mCurrentScoutUri;

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
        mCurrentScoutUri = intent.getData();

        // mPrefs = getPreferences(MODE_PRIVATE);
        mPrefs = getSharedPreferences(PREFS_SCOUTER, MODE_PRIVATE);
        mScouter = mPrefs.getString("PREF_SCOUTER", "Prenom");
        mMatch = mPrefs.getInt("PREF_MATCH", 0) + 1; // increment match number

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
        Cursor cursor = null;
        boolean bSchedule = false;

        if( mCurrentScoutUri != null) {

            cursor = getContentResolver().query(mCurrentScoutUri, column, selection, selectionArgs, sortOrder);
            String debug = DatabaseUtils.dumpCursorToString(cursor);
            Log.d(TAG, debug);

            int matchColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_MATCH);
            int robotColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_ROBOT);
            //int scouterColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_SCOUTER);
            int scheduleColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_SCHEDULE_MATCH);

            cursor.moveToFirst();
            boolean isFirst = cursor.isFirst();

            mMatch = cursor.getInt(matchColIdx);
            mRobot = cursor.getInt(robotColIdx);
            mScheduledMatch = cursor.getInt(scheduleColIdx);

            if ( mScheduledMatch == 1) bSchedule = true;

            Log.d(TAG, "match : " + mMatch);
            Log.d(TAG, "robot : " + mRobot);
            Log.d(TAG, "schedule : " + mScheduledMatch);
            RobotInfo = new ScoutInfo(0, mMatch, mRobot, mScouter, mScheduledMatch);

            mRobotEditText.setText(RobotInfo.getRobot().toString());
            mRobotImage.setImageResource(RobotInfo.getRobotImageId());
            mScheduledMatchCheckedBox.setText("OUI");

        } else {
            mRobotImage.setImageResource(R.drawable.robotronixlogo);
            mScheduledMatchCheckedBox.setText("NON");
        }

        mMatchEditText.setText(mMatch.toString());
        mScheduledMatchCheckedBox.setChecked(bSchedule);



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

                    ScoutInfo RobotInfo = new ScoutInfo(0, mMatch, mRobot, "", 0);
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

            if( mCurrentScoutUri != null) {
                intent.setData(mCurrentScoutUri);
            } else {
                // intent.putExtra(EXTRA_SCOUTER, mScouter);
                // intent.putExtra(EXTRA_MATCH, match);
                intent.putExtra(EXTRA_ROBOT, mRobot);
            }
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
