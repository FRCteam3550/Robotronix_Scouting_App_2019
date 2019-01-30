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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.robotronix3550.robotronix_scouting_app.data.ScoutContract;
import com.robotronix3550.robotronix_scouting_app.data.ScoutContract.ScoutEntry;
import com.robotronix3550.robotronix_scouting_app.data.ScoutInfo;

import static com.robotronix3550.robotronix_scouting_app.CreateMatchActivity.PREFS_SCOUTER;
import static com.robotronix3550.robotronix_scouting_app.R.id.TxtCountBlock;
import static com.robotronix3550.robotronix_scouting_app.R.id.TxtCountPins;
import static com.robotronix3550.robotronix_scouting_app.R.id.lvl1Button;

public class ScoutMatchActivity extends AppCompatActivity {

    public static final String TAG = ScoutMatchActivity.class.getSimpleName();

    Integer mRobot;
    String mScouter;
    Integer mMatch;
    Boolean SandTele;

    Integer mSandCargoLvl1;
    Integer mSandPanelLvl1;
    Integer mSandCargoLvl2;
    Integer mSandPanelLvl2;
    Integer mSandCargoLvl3;
    Integer mSandPanelLvl3;

    Integer mTeleCargoLvl1;
    Integer mTelePanelLvl1;
    Integer mTeleCargoLvl2;
    Integer mTelePanelLvl2;
    Integer mTeleCargoLvl3;
    Integer mTelePanelLvl3;

    Integer mTeleHabLvl1;
    Integer mTeleHabLvl2;
    Integer mTeleHabLvl3;

    Integer mSandHabLvl1;
    Integer mSandHabLvl2;
    Integer mSandHabLvl3;

    Integer mTeleBlocks;
    Integer mTelePin;


    Integer alliance_score;
    Integer enemy_score;

    /** EditText field to enter the scout's match */
    private EditText mMatchEditText;

    /** EditText field to enter the scout's robot */
    private ToggleButton HabLvl1;
    private ToggleButton HabLvl2;
    private ToggleButton HabLvl3;
    private Switch SandTeleSwitch;

    private EditText AllyScoreEditText;
    private EditText EnemyScoreEditText;
    private TextView TxtCntPanelLvl1;
    private TextView TxtCntPanelLvl2;
    private TextView TxtCntPanelLvl3;
    private TextView TxtCntCargoLvl1;
    private TextView TxtCntCargoLvl2;
    private TextView TxtCntCargoLvl3;
    private TextView TxtCountBlocks;
    private TextView TxtCountPins;


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
        SandTeleSwitch = findViewById(R.id.SandTeleSwitch);
        HabLvl1 = findViewById(R.id.lvl1Button);
        HabLvl2 = findViewById(R.id.lvl2Button);
        HabLvl3 = findViewById(R.id.lvl3Button);

        TxtCntPanelLvl1 = findViewById(R.id.TxtCntPanelLvl1);
        TxtCntPanelLvl2 = findViewById(R.id.TxtCntPanelLvl2);
        TxtCntPanelLvl3 = findViewById(R.id.TxtCntPanelLvl3);
        TxtCntCargoLvl1 = findViewById(R.id.TxtCntCargoLvl1);
        TxtCntCargoLvl2 = findViewById(R.id.TxtCntCargoLvl2);
        TxtCntCargoLvl3 = findViewById(R.id.TxtCntCargoLvl3);
        TxtCountPins = findViewById(R.id.TxtCountPins);
        TxtCountBlocks = findViewById(R.id.TxtCountBlock);

        AllyScoreEditText = findViewById(R.id.editNumber1);
        EnemyScoreEditText = findViewById(R.id.editNumber2);

        SandTele = SandTeleSwitch.isChecked();
        SandTeleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                saveHabLvlState();

                if (SandTeleSwitch.isChecked()) {
                    SandTeleSwitch.setText("Tele");
                    SandTele = true;
                } else {
                    SandTeleSwitch.setText("Sandstorm");
                    SandTele = false;
                }
                updateTxtCnt();
            }
        });

        // Comes from rotating the tablet
        //
        if( savedInstanceState != null ) {

            // mMatch = intent.getIntExtra(CreateMatchActivity.EXTRA_MATCH, 0);
            mRobot = intent.getIntExtra(CreateMatchActivity.EXTRA_ROBOT, 0);

            // mPrefs = getPreferences(MODE_PRIVATE);
            mPrefs = getSharedPreferences(PREFS_SCOUTER, MODE_PRIVATE);
            mScouter = mPrefs.getString("PREF_SCOUTER", "Prenom");
            mMatch = mPrefs.getInt("PREF_MATCH", 98);

            /*
            TxtCntPlvl1 = savedInstanceState.getInt("Panel_Lvl1");
            TxtCntPlvl2 = savedInstanceState.getInt("Panel_Lvl2");
            TxtCntPlvl3 = savedInstanceState.getInt("Panel_lvl3");
            TxtCntClvl1 = savedInstanceState.getInt("Cargo_lvl1");
            TxtCntClvl2 = savedInstanceState.getInt("Cargo_lvl2");
            TxtCntClvl3 = savedInstanceState.getInt("Cargo_lvl3");

            TxtViewCount7 = savedInstanceState.getInt("Pins");
            TxtViewCount8 = savedInstanceState.getInt("Blocks");
            //AllyScoreEditText = savedInstanceState.getInt("Ally_score");
            //EnemyScoreEditText = savedInstanceState.getInt("Enemy_score");
            */

            mSandCargoLvl1 = 0;
            mSandCargoLvl2 = 0;
            mSandCargoLvl3 = 0;
            mSandPanelLvl1 = 0;
            mSandPanelLvl2 = 0;
            mSandPanelLvl2 = 0;
            mSandPanelLvl3 = 0;

            mTeleCargoLvl1 = 0;
            mTeleCargoLvl2 = 0;
            mTeleCargoLvl3 = 0;
            mTelePanelLvl1 = 0;
            mTelePanelLvl2 = 0;
            mTelePanelLvl3 = 0;
            mTeleBlocks = 0;
            mTelePin = 0;

            mSandHabLvl1 = 0;
            mSandHabLvl2 = 0;
            mSandHabLvl3 = 0;

            mTeleHabLvl1 = 0;
            mTeleHabLvl2 = 0;
            mTeleHabLvl3 = 0;


            alliance_score = 0;
            enemy_score = 0;

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


            mSandCargoLvl1 = 0;
            mSandCargoLvl2 = 0;
            mSandCargoLvl3 = 0;
            mSandPanelLvl1 = 0;
            mSandPanelLvl2 = 0;
            mSandPanelLvl2 = 0;
            mSandPanelLvl3 = 0;

            mTeleCargoLvl1 = 0;
            mTeleCargoLvl2 = 0;
            mTeleCargoLvl3 = 0;
            mTelePanelLvl1 = 0;
            mTelePanelLvl2 = 0;
            mTelePanelLvl3 = 0;
            mTeleBlocks = 0;
            mTelePin = 0;

            mSandHabLvl1 = 0;
            mSandHabLvl2 = 0;
            mSandHabLvl3 = 0;
            mTeleHabLvl1 = 0;
            mTeleHabLvl2 = 0;
            mTeleHabLvl3 = 0;
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

                int SandCargoLvl1Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_SAND_CARGO_LVL1);
                int SandPanelLvl1Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_SAND_PANEL_LVL1);
                int SandCargoLvl2Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_SAND_CARGO_LVL2);
                int SandPanelLvl2Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_SAND_PANEL_LVL2);
                int SandCargoLvl3Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_SAND_CARGO_LVL3);
                int SandPanelLvl3Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_SAND_PANEL_LVL3);

                int TeleCargoLvl1Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_TELE_CARGO_LVL1);
                int TeleCargoLvl2Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_TELE_CARGO_LVL2);
                int TeleCargoLvl3Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_TELE_CARGO_LVL3);
                int TelePanelLvl1Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_TELE_PANEL_LVL1);
                int TelePanelLvl2Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_TELE_PANEL_LVL2);
                int TelePanelLvl3Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_TELE_PANEL_LVL3);

                int SandHabLvl1Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_SAND_HAB_LVL1);
                int SandHabLvl2Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_SAND_HAB_LVL2);
                int SandHabLvl3Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_SAND_HAB_LVL3);

                int TeleHabLvl1Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_TELE_HAB_LVL1);
                int TeleHabLvl2Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_TELE_HAB_LVL2);
                int TeleHabLvl3Idx = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_TELE_HAB_LVL3);

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

                mSandCargoLvl1 = cursor.getInt(SandCargoLvl1Idx);
                mSandCargoLvl2 = cursor.getInt(SandCargoLvl2Idx);
                mSandCargoLvl3 = cursor.getInt(SandCargoLvl3Idx);
                mTeleCargoLvl1 = cursor.getInt(TeleCargoLvl1Idx);
                mTeleCargoLvl2 = cursor.getInt(TeleCargoLvl2Idx);
                mTeleCargoLvl3 = cursor.getInt(TeleCargoLvl3Idx);

                mSandPanelLvl1 = cursor.getInt(SandPanelLvl1Idx);
                mSandPanelLvl2 = cursor.getInt(SandPanelLvl2Idx);
                mSandPanelLvl3 = cursor.getInt(SandPanelLvl3Idx);
                mTelePanelLvl1 = cursor.getInt(TelePanelLvl1Idx);
                mTelePanelLvl2 = cursor.getInt(TelePanelLvl2Idx);
                mTelePanelLvl3 = cursor.getInt(TelePanelLvl3Idx);

                mSandHabLvl1 = cursor.getInt(SandHabLvl1Idx);
                mSandHabLvl2 = cursor.getInt(SandHabLvl2Idx);
                mSandHabLvl3 = cursor.getInt(SandHabLvl3Idx);

                mTeleHabLvl1 = cursor.getInt(TeleHabLvl1Idx);
                mTeleHabLvl2 = cursor.getInt(TeleHabLvl2Idx);
                mTeleHabLvl3 = cursor.getInt(TeleHabLvl3Idx);

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
        //mMatchEditText.setText(mMatch.toString());
        //mRobotEditText.setText(mRobot.toString());

        updateTxtCnt();
    }

    public void saveHabLvlState() {

        if( SandTele ) {

            if(HabLvl1.isChecked())
                mTeleHabLvl1 = 1;
            else
                mTeleHabLvl1 = 0;

            if(HabLvl2.isChecked())
                mTeleHabLvl2 = 1;
            else
                mTeleHabLvl2 = 0;

            if(HabLvl3.isChecked())
                mTeleHabLvl3= 1;
            else
                mTeleHabLvl3= 0;

        } else {

            if(HabLvl1.isChecked())
                mSandHabLvl1 = 1;
            else
                mSandHabLvl1 = 0;

            if(HabLvl2.isChecked())
                mSandHabLvl2 = 1;
            else
                mSandHabLvl2 = 0;

            if(HabLvl3.isChecked())
                mSandHabLvl3= 1;
            else
                mSandHabLvl3= 0;
        }

    }

    public void updateTxtCnt() {

        boolean bSandHabLvl1 = false;
        boolean bSandHabLvl2 = false;
        boolean bSandHabLvl3 = false;
        boolean bTeleHabLvl1 = false;
        boolean bTeleHabLvl2 = false;
        boolean bTeleHabLvl3 = false;

        if( mSandHabLvl1 != 0) bSandHabLvl1 = true;
        if( mSandHabLvl2 != 0) bSandHabLvl2 = true;
        if( mSandHabLvl3 != 0) bSandHabLvl3 = true;
        if( mTeleHabLvl1 != 0) bTeleHabLvl1 = true;
        if( mTeleHabLvl2 != 0) bTeleHabLvl2 = true;
        if( mTeleHabLvl3 != 0) bTeleHabLvl3 = true;

        if (SandTele == false){
            TxtCntPanelLvl1.setText(mSandPanelLvl1.toString());
            TxtCntPanelLvl2.setText(mSandPanelLvl2.toString());
            TxtCntPanelLvl3.setText(mSandPanelLvl3.toString());
            TxtCntCargoLvl1.setText(mSandCargoLvl1.toString());
            TxtCntCargoLvl2.setText(mSandCargoLvl2.toString());
            TxtCntCargoLvl3.setText(mSandCargoLvl3.toString());
            HabLvl1.setChecked(bSandHabLvl1);
            HabLvl2.setChecked(bSandHabLvl2);
            HabLvl3.setChecked(bSandHabLvl3);
        }
        else {
            HabLvl1.setChecked(bTeleHabLvl1);
            HabLvl2.setChecked(bTeleHabLvl2);
            HabLvl3.setChecked(bTeleHabLvl3);
            TxtCntPanelLvl1.setText(mTelePanelLvl1.toString());
            TxtCntPanelLvl2.setText(mTelePanelLvl2.toString());
            TxtCntPanelLvl3.setText(mTelePanelLvl3.toString());
            TxtCntCargoLvl1.setText(mTeleCargoLvl1.toString());
            TxtCntCargoLvl2.setText(mTeleCargoLvl2.toString());
            TxtCntCargoLvl3.setText(mTeleCargoLvl3.toString());

        }

    }


    public void IncPanelLvl1(View view) {
        TextView TxtCntPanelLvl1 = (TextView) findViewById(R.id.TxtCntPanelLvl1);
        // SandTele == true -> TeleOp
        // SandTele == false -> Sandstorm
        if( SandTele ) {
            mTelePanelLvl1++;
            TxtCntPanelLvl1.setText(mTelePanelLvl1.toString());
        } else {
            mSandPanelLvl1++;
            TxtCntPanelLvl1.setText(mSandPanelLvl1.toString());
        }
    }

    public void DecPanelLvl1(View view) {
        TextView TxtCntPanelLvl1 = (TextView) findViewById(R.id.TxtCntPanelLvl1);
        if( SandTele ) {
            if(mTelePanelLvl1 > 0) {
                mTelePanelLvl1--;
            }
            TxtCntPanelLvl1.setText(mTelePanelLvl1.toString());
        } else {
            if(mSandPanelLvl1 > 0) {
                mSandPanelLvl1--;
            }
            TxtCntPanelLvl1.setText(mSandPanelLvl1.toString());
        }
    }

    public void IncCargoLvl1(View view) {
        TextView TxtCntClvl1 = (TextView) findViewById(R.id.TxtCntCargoLvl1);
        if( SandTele ) {
            mTeleCargoLvl1++;
            TxtCntClvl1.setText(mTeleCargoLvl1.toString());
        } else {
            mSandCargoLvl1++;
            TxtCntClvl1.setText(mSandCargoLvl1.toString());
        }
    }

    public void DecCargoLvl1(View view) {
        TextView TxtCntCargoLvl1 = (TextView) findViewById(R.id.TxtCntCargoLvl1);
        if( SandTele ) {
            if(mTeleCargoLvl1 > 0) {
                mTeleCargoLvl1--;
            }
            TxtCntCargoLvl1.setText(mTeleCargoLvl1.toString());
        } else {
            if(mSandCargoLvl1 > 0) {
                mSandCargoLvl1--;
            }
            TxtCntCargoLvl1.setText(mSandCargoLvl1.toString());
        }

    }

    public void IncPanelLvl2(View view) {
        TextView TxtCntPanelLvl2 = (TextView) findViewById(R.id.TxtCntPanelLvl2);
        if( SandTele ) {
            mTelePanelLvl2++;
            TxtCntPanelLvl2.setText(mTelePanelLvl2.toString());
        } else {
            mSandPanelLvl2++;
            TxtCntPanelLvl2.setText(mSandPanelLvl2.toString());
        }
    }

    public void DecPanelLvl2(View view) {
        TextView TxtCntPanelLvl2= (TextView) findViewById(R.id.TxtCntPanelLvl2);
        if( SandTele ) {
            if(mTelePanelLvl2 > 0) {
                mTelePanelLvl2--;
            }
            TxtCntPanelLvl2.setText(mTelePanelLvl2.toString());
        } else {
            if(mSandPanelLvl2 > 0) {
                mSandPanelLvl2--;
            }
            TxtCntPanelLvl2.setText(mSandPanelLvl2.toString());
        }
    }


    public void IncCargoLvl2(View view) {
        TextView TxtCntCargoLvl2 = (TextView) findViewById(R.id.TxtCntCargoLvl2);
        if( SandTele ) {
            mTeleCargoLvl2++;
            TxtCntCargoLvl2.setText(mTeleCargoLvl2.toString());
        } else {
            mSandCargoLvl2++;
            TxtCntCargoLvl2.setText(mSandCargoLvl2.toString());
        }
    }

    public void DecCargoLvl2(View view) {
        TextView TxtCntCargoLvl2 = (TextView) findViewById(R.id.TxtCntCargoLvl2);
        if( SandTele ) {
            if(mTeleCargoLvl2 > 0) {
                mTeleCargoLvl2--;
            }
            TxtCntCargoLvl2.setText(mTeleCargoLvl2.toString());
        } else {
            if(mSandCargoLvl2 > 0) {
                mSandCargoLvl2--;
            }
            TxtCntCargoLvl2.setText(mSandCargoLvl2.toString());
        }
    }

    public void IncPanelLvl3(View view) {
        TextView TxtCntPanelLvl3 = (TextView) findViewById(R.id.TxtCntPanelLvl3);
        if( SandTele ) {
            mTelePanelLvl3++;
            TxtCntPanelLvl3.setText(mTelePanelLvl3.toString());
        } else {
            mSandPanelLvl3++;
            TxtCntPanelLvl3.setText(mSandPanelLvl3.toString());
        }
    }
    public void DecPanelLvl3(View view) {
        TextView TxtCntPanelLvl2 = (TextView) findViewById(R.id.TxtCntPanelLvl3);
        if( SandTele ) {
            if(mTelePanelLvl3 > 0) {
                mTelePanelLvl3--;
            }
            TxtCntPanelLvl3.setText(mTelePanelLvl3.toString());
        } else {
            if(mSandPanelLvl3 > 0) {
                mSandPanelLvl3--;
            }
            TxtCntPanelLvl3.setText(mSandPanelLvl3.toString());
        }
    }

    public void IncCargoLvl3(View view) {
        TextView TxtCntCargoLvl3 = (TextView) findViewById(R.id.TxtCntCargoLvl3);
        if( SandTele ) {
            mTeleCargoLvl3++;
            TxtCntCargoLvl3.setText(mTeleCargoLvl3.toString());
        } else {
            mSandCargoLvl3++;
            TxtCntCargoLvl3.setText(mSandCargoLvl3.toString());
        }
    }
    public void DecCargoLvl3(View view) {
        TextView TxtCntCargoLvl3 = (TextView) findViewById(R.id.TxtCntCargoLvl3);
        if( SandTele ) {
            if(mTeleCargoLvl3 > 0) {
                mTeleCargoLvl3--;
            }
            TxtCntCargoLvl3.setText(mTeleCargoLvl3.toString());
        } else {
            if(mSandCargoLvl3 > 0) {
                mSandCargoLvl3--;
            }
            TxtCntCargoLvl3.setText(mSandCargoLvl3.toString());
        }
    }
    public void IncPins(View view) {
        TextView TxtCountPins = (TextView) findViewById(R.id.TxtCountPins);
        mTelePin++;
    }

    public void DecPins(View view) {
        TextView TxtCountPins = (TextView) findViewById(R.id.TxtCountPins);
        if(mTelePin > 0) {
            mTelePin--;
        }
    }
    public void IncBlocks(View view) {
        TextView TxtCountView8 = (TextView) findViewById(R.id.TxtCountBlock);
        mTeleBlocks++;
    }

    public void DecBlocks(View view) {
        TextView TxtCountBlocks = (TextView) findViewById(R.id.TxtCountBlock);
        if(mTeleBlocks > 0) {
            mTeleBlocks--;
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {

        super.onSaveInstanceState(outState);
        outState.putInt("Panel_Lvl1", Integer.parseInt(TxtCntPanelLvl1.getText().toString().trim()));
        outState.putInt("Panel_Lvl2", Integer.parseInt(TxtCntPanelLvl2.getText().toString().trim()));
        outState.putInt("Panel_lvl3", Integer.parseInt(TxtCntPanelLvl3.getText().toString().trim()));
        outState.putInt("Cargo_lvl1", Integer.parseInt(TxtCntCargoLvl1.getText().toString().trim()));
        outState.putInt("Cargo_lvl2", Integer.parseInt(TxtCntCargoLvl2.getText().toString().trim()));
        outState.putInt("Cargo_lvl3", Integer.parseInt(TxtCntCargoLvl3.getText().toString().trim()));
        outState.putInt("Pins", Integer.parseInt(TxtCountPins.getText().toString().trim()));
        outState.putInt("Blocks", Integer.parseInt(TxtCountBlocks.getText().toString().trim()));
        outState.putInt("Ally_score", Integer.parseInt(AllyScoreEditText.getText().toString().trim()));
        outState.putInt("Enemy_score", Integer.parseInt(EnemyScoreEditText.getText().toString().trim()));



    }

    public void saveMatch(View view) {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space


        //String matchString = mMatchEditText.getText().toString().trim();
        //String robotString = mRobotEditText.getText().toString().trim();

        //mMatch = Integer.parseInt(matchString);
        //mRobot = Integer.parseInt(robotString);

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

        saveHabLvlState();


        /*Create a ContentValues object where column names are the keys,
          and scout attributes from the editor are the values.*/
        ContentValues values = new ContentValues();
        values.put(ScoutEntry.COLUMN_SCOUT_SCOUTER, mScouter);
        values.put(ScoutEntry.COLUMN_SCOUT_MATCH, mMatch);
        values.put(ScoutEntry.COLUMN_SCOUT_ROBOT, mRobot);

        values.put(ScoutEntry.COLUMN_SCOUT_SAND_CARGO_LVL1,mSandCargoLvl1 );
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_CARGO_LVL1,mTeleCargoLvl1);
        values.put(ScoutEntry.COLUMN_SCOUT_SAND_CARGO_LVL2,mSandCargoLvl2 );
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_CARGO_LVL2,mTeleCargoLvl2);
        values.put(ScoutEntry.COLUMN_SCOUT_SAND_CARGO_LVL3,mSandCargoLvl3);
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_CARGO_LVL3,mTeleCargoLvl3);

        values.put(ScoutEntry.COLUMN_SCOUT_SAND_PANEL_LVL1,mSandPanelLvl1);
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_PANEL_LVL1,mTelePanelLvl1);
        values.put(ScoutEntry.COLUMN_SCOUT_SAND_PANEL_LVL2,mSandPanelLvl2);
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_PANEL_LVL2,mTelePanelLvl2);
        values.put(ScoutEntry.COLUMN_SCOUT_SAND_PANEL_LVL3,mSandPanelLvl3);
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_PANEL_LVL3,mTelePanelLvl3);
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_PINS,mTelePin);
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_BLOCKS,mTeleBlocks);


        values.put(ScoutEntry.COLUMN_SCOUT_TELE_HAB_LVL1,mTeleHabLvl1);
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_HAB_LVL2,mTeleHabLvl2);
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_HAB_LVL3,mTeleHabLvl3);
        values.put(ScoutEntry.COLUMN_SCOUT_SAND_HAB_LVL1,mSandHabLvl1);
        values.put(ScoutEntry.COLUMN_SCOUT_SAND_HAB_LVL2,mSandHabLvl2);
        values.put(ScoutEntry.COLUMN_SCOUT_SAND_HAB_LVL3,mSandHabLvl3);

        /*
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_HELP_CLIMB, end_help );
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_BROKEN, end_broken );
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_CLIMB, end_climb );
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_PARK, end_park );
        */

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
