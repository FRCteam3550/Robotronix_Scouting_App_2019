package com.robotronix3550.robotronix_scouting_app;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.robotronix3550.robotronix_scouting_app.data.ScoutContract;
import com.robotronix3550.robotronix_scouting_app.data.ScoutContract.ScoutEntry;
import com.robotronix3550.robotronix_scouting_app.data.ScoutInfo;

import static com.robotronix3550.robotronix_scouting_app.CreateMatchActivity.PREFS_SCOUTER;

public class ScoutMatchActivity extends AppCompatActivity {

    public static final String TAG = ScoutMatchActivity.class.getSimpleName();

    Integer mCubeExchangeCnt;
    Integer mCubeAllySwitchCnt;
    Integer mCubeEnemySwitchCnt;
    Integer mCubeScaleCnt;
    Integer mRobot;
    Integer mMatch;
    String mScouter;
    Integer auto_line;
    Integer auto_pick;
    Integer auto_scale;
    Integer auto_switch;

    Integer end_help;
    Integer end_broken;
    Integer end_climb;
    Integer end_park;

    Integer alliance_score;
    Integer enemy_score;

    /** EditText field to enter the scout's match */
    private EditText mMatchEditText;

    /** EditText field to enter the scout's robot */
    private EditText mRobotEditText;

    private TextView mCubeExchangeText;

    private TextView mCubeAllySwitchText;

    private TextView mCubeEnemySwitchText;

    private TextView mCubeScaleText;


    private ToggleButton mLineTogglebutton;
    private ToggleButton mPickTogglebutton;
    private ToggleButton mSwitchTogglebutton;
    private ToggleButton mScaleTogglebutton;
    private ToggleButton mHelpTogglebutton;
    private ToggleButton mBrokenTogglebutton;
    private ToggleButton mClimbTogglebutton;
    private ToggleButton mParkTogglebutton;

    private EditText AllyScoreEditText;
    private EditText EnemyScoreEditText;

    private SharedPreferences mPrefs;

    Uri mCurrentScoutUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout_match);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        mCurrentScoutUri = intent.getData();

        // Capture the layout's TextView and set the string as its text
        mMatchEditText = findViewById(R.id.MatchEditText);
        mRobotEditText = findViewById(R.id.RobotEditText);
        mCubeExchangeText = (TextView) findViewById(R.id.ExchangeCntTextView);
        mCubeAllySwitchText = (TextView) findViewById(R.id.AllySwitchTextView);
        mCubeEnemySwitchText = (TextView) findViewById(R.id.EnemySwitchCntTextView);
        mCubeScaleText = (TextView) findViewById(R.id.ScaleCntTextView);

        mLineTogglebutton = (ToggleButton) findViewById(R.id.LineToggleButton);
        mPickTogglebutton = (ToggleButton) findViewById(R.id.PickToggleButton);
        mScaleTogglebutton = (ToggleButton) findViewById(R.id.ScaleToggleButton);
        mSwitchTogglebutton = (ToggleButton) findViewById(R.id.SwitchToggleButton);
        mHelpTogglebutton = (ToggleButton) findViewById(R.id.HelpToggleButton);
        mBrokenTogglebutton = (ToggleButton) findViewById(R.id.BrokenToggleButton);
        mClimbTogglebutton = (ToggleButton) findViewById(R.id.ClimbToggleButton);
        mParkTogglebutton = (ToggleButton) findViewById(R.id.ParkToggleButton);

        AllyScoreEditText = findViewById(R.id.AllyScoreEditText);
        EnemyScoreEditText = findViewById(R.id.EnemyScoreEditText);

        // Comes from rotating the tablet
        //
        if( savedInstanceState != null ) {

            // mMatch = intent.getIntExtra(CreateMatchActivity.EXTRA_MATCH, 0);
            mRobot = intent.getIntExtra(CreateMatchActivity.EXTRA_ROBOT, 0);

            // mPrefs = getPreferences(MODE_PRIVATE);
            mPrefs = getSharedPreferences(PREFS_SCOUTER, MODE_PRIVATE);
            mScouter = mPrefs.getString("PREF_SCOUTER", "Prenom");
            mMatch = mPrefs.getInt("PREF_MATCH", 98);

            mCubeScaleCnt = savedInstanceState.getInt("Scale_count");
            mCubeExchangeCnt = savedInstanceState.getInt("Exchange_count");
            mCubeAllySwitchCnt = savedInstanceState.getInt("Ally_switch_count");
            mCubeEnemySwitchCnt = savedInstanceState.getInt("Enemy_switch_count");

            auto_line = 0;
            auto_pick = 0;
            auto_scale = 0;
            auto_switch = 0;

            end_help = 0;
            end_broken = 0;
            end_climb = 0;
            end_park = 0;

            alliance_score = 0;
            enemy_score = 0;

        } else if( mCurrentScoutUri == null) {

            // Comes from the CreateMatchActivity
            //

            //mMatch = intent.getIntExtra(CreateMatchActivity.EXTRA_MATCH, 0);
            mRobot = intent.getIntExtra(CreateMatchActivity.EXTRA_ROBOT, 0);

            //mPrefs = getPreferences(MODE_PRIVATE);
            mPrefs = getSharedPreferences(PREFS_SCOUTER, MODE_PRIVATE);
            mScouter = mPrefs.getString("PREF_SCOUTER", "Prenom");
            mMatch = mPrefs.getInt("PREF_MATCH", 0); // increment match number


            mCubeExchangeCnt = 0;
            mCubeAllySwitchCnt = 0;
            mCubeEnemySwitchCnt = 0;
            mCubeScaleCnt = 0;

            auto_line = 0;
            auto_pick = 0;
            auto_scale = 0;
            auto_switch = 0;

            end_help = 0;
            end_broken = 0;
            end_climb = 0;
            end_park = 0;

            alliance_score = 0;
            enemy_score = 0;

        } else {
            // Get Data Database (from ReviewMatchActivity)
            //

            String[] column = null;  // return all columns
            String selection = null; // db_id.toString(); // return all rows
            String[] selectionArgs = null; // not used
            String sortOrder = null;  // unordered
            Cursor cursor = null;

            try {

                cursor = getContentResolver().query(mCurrentScoutUri, column, selection, selectionArgs, sortOrder);

                String debug = DatabaseUtils.dumpCursorToString(cursor);
                Log.d(TAG, debug);

                // Find the columns of pet attributes that we're interested in
                int matchColIdx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_MATCH);
                int robotColIdx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_ROBOT);
                int scouterColIdx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_SCOUTER);

                int autoLineColIdx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_AUTO_LINE);
                int autoPickColIdx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_AUTO_CUBE);
                int autoScaleColIdx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_AUTO_SCALE);
                int autoSwitchColIdx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_AUTO_SWITCH);
                int endBrokenColIdx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_TELE_BROKEN);
                int endHelpColIdx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_TELE_HELP_CLIMB);
                int endClimbColIdx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_TELE_CLIMB);
                int endParkColIdx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_TELE_PARK);
                int teleExchColIdx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_TELE_EXCHANGE);
                int teleAllySwitchColIdx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_TELE_ALLY_SWITCH);
                int teleEnemySwitchColIdx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_TELE_ENEMY_SWITCH);
                int teleScaleColIdx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_TELE_SCALE);

                int gameAllyScoreColIdx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_GAME_ALLY_SCORE);
                int gameEnemyScoreColIdx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_GAME_ENEMY_SCORE);

                /*
                int count = cursor.getCount();
                int pos = cursor.getPosition();
                boolean isAfterLast = cursor.isAfterLast();
                boolean isBeforeFirst = cursor.isBeforeFirst();
                boolean isFirst = cursor.isFirst();
                boolean isLast = cursor.isLast();
                */
                cursor.moveToFirst();
                boolean isFirst = cursor.isFirst();

                // db_id = cursor.getInt(0);
                mMatch = cursor.getInt(matchColIdx);
                mRobot = cursor.getInt(robotColIdx);
                mScouter = cursor.getString(scouterColIdx);

                mCubeExchangeCnt = cursor.getInt(teleExchColIdx);
                mCubeAllySwitchCnt = cursor.getInt(teleAllySwitchColIdx);
                mCubeEnemySwitchCnt = cursor.getInt(teleEnemySwitchColIdx);
                mCubeScaleCnt = cursor.getInt(teleScaleColIdx);
                auto_line = cursor.getInt(autoLineColIdx);
                auto_pick = cursor.getInt(autoPickColIdx);
                auto_scale = cursor.getInt(autoScaleColIdx);
                auto_switch = cursor.getInt(autoSwitchColIdx);

                end_help = cursor.getInt(endHelpColIdx);
                end_broken = cursor.getInt(endBrokenColIdx);
                end_climb = cursor.getInt(endClimbColIdx);
                end_park = cursor.getInt(endParkColIdx);

                alliance_score = cursor.getInt(gameAllyScoreColIdx);
                enemy_score = cursor.getInt(gameEnemyScoreColIdx);

                AllyScoreEditText.setText(alliance_score.toString());
                EnemyScoreEditText.setText(enemy_score.toString());

            }
            catch(Exception throwable){
                    Log.e(TAG, "Could not get data from cursor", throwable);
            }
            finally {
                if(cursor!=null)
                    cursor.close();
            }
        }

        // Capture the layout's TextView and set the string as its text
        mMatchEditText.setText(mMatch.toString());
        mRobotEditText.setText(mRobot.toString());
        mCubeExchangeText.setText(mCubeExchangeCnt.toString());
        mCubeAllySwitchText.setText(mCubeAllySwitchCnt.toString());
        mCubeEnemySwitchText.setText(mCubeEnemySwitchCnt.toString());
        mCubeScaleText.setText(mCubeScaleCnt.toString());

        boolean bauto_line = true;
        if(auto_line == 0)  bauto_line = false;

        boolean bauto_pick = true;
        if(auto_pick == 0)  bauto_pick = false;

        boolean bauto_switch = true;
        if(auto_switch == 0)  bauto_switch = false;

        boolean bauto_scale = true;
        if(auto_scale == 0)  bauto_scale = false;

        boolean bend_help = true;
        if(end_help == 0)  bend_help = false;

        boolean bend_climb = true;
        if(end_climb == 0)  bend_climb = false;

        boolean bend_broken = true;
        if(end_broken == 0)  bend_broken = false;

        boolean bend_park = true;
        if(end_park == 0)  bend_park = false;

        mLineTogglebutton.setChecked(bauto_line);
        mPickTogglebutton.setChecked(bauto_pick);
        mScaleTogglebutton.setChecked(bauto_scale);
        mSwitchTogglebutton.setChecked(bauto_switch);

        mHelpTogglebutton.setChecked(bend_help);
        mClimbTogglebutton.setChecked(bend_climb);
        mBrokenTogglebutton.setChecked(bend_broken);
        mParkTogglebutton.setChecked(bend_park);

    }

    public void incCubeExchangeCnt(View view) {
        mCubeExchangeCnt++;
        TextView cubeExchangeText = (TextView) findViewById(R.id.ExchangeCntTextView);
        cubeExchangeText.setText(mCubeExchangeCnt.toString());
    }

    public void decCubeExchangeCnt(View view) {

        if(mCubeExchangeCnt > 0) {
            mCubeExchangeCnt--;
        }
        TextView cubeExchangeText = (TextView) findViewById(R.id.ExchangeCntTextView);
        cubeExchangeText.setText(mCubeExchangeCnt.toString());
    }

    public void incCubeAllySwitchCnt(View view) {
        mCubeAllySwitchCnt++;
        TextView cubeAllySwitchText = (TextView) findViewById(R.id.AllySwitchTextView);
        cubeAllySwitchText.setText(mCubeAllySwitchCnt.toString());
    }

    public void decCubeAllySwitchCnt(View view) {
        if(mCubeAllySwitchCnt > 0) {
            mCubeAllySwitchCnt--;
        }
        TextView cubeAllySwitchText = (TextView) findViewById(R.id.AllySwitchTextView);
        cubeAllySwitchText.setText(mCubeAllySwitchCnt.toString());
    }

    public void incCubeEnemySwitchCnt(View view) {
        mCubeEnemySwitchCnt++;
        TextView cubeEnemySwitchText = (TextView) findViewById(R.id.EnemySwitchCntTextView);
        cubeEnemySwitchText.setText(mCubeEnemySwitchCnt.toString());
    }

    public void decCubeEnemySwitchCnt(View view) {
        if(mCubeEnemySwitchCnt > 0) {
            mCubeEnemySwitchCnt--;
        }
        TextView cubeEnemySwitchText = (TextView) findViewById(R.id.EnemySwitchCntTextView);
        cubeEnemySwitchText.setText(mCubeEnemySwitchCnt.toString());
    }

    public void incCubeScaleCnt(View view) {
        mCubeScaleCnt++;
        TextView cubeScaleText = (TextView) findViewById(R.id.ScaleCntTextView);
        cubeScaleText.setText(mCubeScaleCnt.toString());
    }

    public void decCubeScaleCnt(View view) {
        if(mCubeScaleCnt > 0) {
            mCubeScaleCnt--;
        }
        TextView cubeScaleText = (TextView) findViewById(R.id.ScaleCntTextView);
        cubeScaleText.setText(mCubeScaleCnt.toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {

        super.onSaveInstanceState(outState);
        outState.putInt("Scale_count", Integer.parseInt(mCubeScaleText.getText().toString().trim()));
        outState.putInt("Ally_switch_count", Integer.parseInt(mCubeAllySwitchText.getText().toString().trim()));
        outState.putInt("Enemy_switch_count", Integer.parseInt(mCubeEnemySwitchText.getText().toString().trim()));
        outState.putInt("Exchange_count", Integer.parseInt(mCubeExchangeText.getText().toString().trim()));
        // outState.putInt("Ally_score", Integer.parseInt(AllyScoreEditText.getText().toString().trim()));
        // outState.putInt("Enemy_score", Integer.parseInt(EnemyScoreEditText.getText().toString().trim()));



    }

    public void saveMatch(View view) {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space


        String matchString = mMatchEditText.getText().toString().trim();
        String robotString = mRobotEditText.getText().toString().trim();

        mMatch = Integer.parseInt(matchString);
        mRobot = Integer.parseInt(robotString);

        String AllianceScoreString = AllyScoreEditText.getText().toString().trim();
        String EnemyScoreString = EnemyScoreEditText.getText().toString().trim();

        if (AllianceScoreString.equals("")) {
            alliance_score = 0;
        } else {
            alliance_score = Integer.parseInt(AllianceScoreString);
        }
        if (EnemyScoreString.equals("")) {
            enemy_score = 0;
        } else {
            enemy_score = Integer.parseInt(EnemyScoreString);
        }

        if(mLineTogglebutton.isChecked())
            auto_line = 1;
        else
            auto_line = 0;

        if(mPickTogglebutton.isChecked())
            auto_pick = 1;
        else
            auto_pick = 0;

        if(mScaleTogglebutton.isChecked())
            auto_scale= 1;
        else
            auto_scale = 0;

        if(mSwitchTogglebutton.isChecked())
            auto_switch = 1;
        else
            auto_switch = 0;

        if(mHelpTogglebutton.isChecked())
            end_help = 1;
        else
            end_help = 0;

        if(mBrokenTogglebutton.isChecked())
            end_broken = 1;
        else
            end_broken = 0;

        if(mClimbTogglebutton.isChecked())
            end_climb = 1;
        else
            end_climb = 0;

        if(mParkTogglebutton.isChecked())
            end_park = 1;
        else
            end_park = 0;


        // Create a ContentValues object where column names are the keys,
        // and scout attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(ScoutEntry.COLUMN_SCOUT_SCOUTER, mScouter);
        values.put(ScoutEntry.COLUMN_SCOUT_MATCH, mMatch);
        values.put(ScoutEntry.COLUMN_SCOUT_ROBOT, mRobot);

        values.put(ScoutEntry.COLUMN_SCOUT_TELE_EXCHANGE, mCubeExchangeCnt );
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_ALLY_SWITCH, mCubeAllySwitchCnt );
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_SCALE, mCubeScaleCnt );
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_ENEMY_SWITCH, mCubeEnemySwitchCnt );


        values.put(ScoutEntry.COLUMN_SCOUT_AUTO_LINE, auto_line );
        values.put(ScoutEntry.COLUMN_SCOUT_AUTO_CUBE, auto_pick );
        values.put(ScoutEntry.COLUMN_SCOUT_AUTO_SCALE, auto_scale );
        values.put(ScoutEntry.COLUMN_SCOUT_AUTO_SWITCH, auto_switch );

        values.put(ScoutEntry.COLUMN_SCOUT_TELE_HELP_CLIMB, end_help );
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_BROKEN, end_broken );
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_CLIMB, end_climb );
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_PARK, end_park );

        values.put(ScoutEntry.COLUMN_SCOUT_GAME_ALLY_SCORE, alliance_score);
        values.put(ScoutEntry.COLUMN_SCOUT_GAME_ENEMY_SCORE, enemy_score);

        Uri newUri = null;

        if( mCurrentScoutUri == null) {

            // Insert a new scout into the provider, returning the content URI for the new scout.
            newUri = getContentResolver().insert(ScoutEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_scout_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_scout_successful),
                        Toast.LENGTH_SHORT).show();
            }

        } else {

            int success;
            success = getContentResolver().update(mCurrentScoutUri, values, null, null);

            // Show a toast message depending on whether or not the insertion was successful
            if (success == 0) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_scout_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_scout_successful),
                        Toast.LENGTH_SHORT).show();
            }

        }

        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("PREF_MATCH", mMatch);
        ed.commit();

        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra(CreateMatchActivity.EXTRA_SCOUTER, mScouter);

        startActivity(intent);

    }

}
