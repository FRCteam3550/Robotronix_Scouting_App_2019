package com.robotronix3550.robotronix_scouting_app;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.robotronix3550.robotronix_scouting_app.data.FileUtils;
import com.robotronix3550.robotronix_scouting_app.data.ScoutContract;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.robotronix3550.robotronix_scouting_app.CreateMatchActivity.PREFS_SCOUTER;

public class ScoutPitActivity extends AppCompatActivity {

    public static final String TAG = ScoutPitActivity.class.getSimpleName();

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private EditText mRobotEditText;
    private EditText mNameEditText;
    private Spinner mRobotDrivetrainSpinner;
    private EditText mRobotWeightEditText;

    private ToggleButton mAutoLineTogglebutton;
    private ToggleButton mAutoSwitchTogglebutton;
    private ToggleButton mAutoScaleTogglebutton;
    private ToggleButton mAutoPickTogglebutton;
    private ToggleButton mTeleExchangeTogglebutton;
    private ToggleButton mTeleSwitchTogglebutton;
    private ToggleButton mTeleScaleTogglebutton;
    private ToggleButton mTelePickTogglebutton;
    private ToggleButton mTelePortalTogglebutton;
    private ToggleButton mTeleClimbTogglebutton;
    private ToggleButton mTeleHelpClimbTogglebutton;

    private SharedPreferences mPrefs;
    private String mFileName;

    ImageView mRobotPictureImageView;

    Uri mCurrentScoutUri;

    Integer mRobot;
    Integer mDrivetrain;
    Integer mWeight;
    Integer mMatch;
    String  mScouter;

    Integer auto_line;
    Integer auto_pick;
    Integer auto_scale;
    Integer auto_switch;

    Integer tele_exchange;
    Integer tele_switch;
    Integer tele_portal;
    Integer tele_scale;

    Integer tele_pick;
    Integer tele_climb;
    Integer tele_help;

    // Integer alliance_score;
    // Integer enemy_score;

    private boolean mDrivetrainChanged = false;
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mDrivetrainChanged=true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout_pit);

        // mPrefs = getPreferences(MODE_PRIVATE );
        mPrefs = getSharedPreferences(PREFS_SCOUTER, MODE_PRIVATE);
        mScouter = mPrefs.getString("PREF_SCOUTER", "Prenom");

        mRobotEditText = (EditText) findViewById(R.id.TeamNumberEditText);
        mNameEditText = (EditText) findViewById(R.id.ScoutNameEditText);

        mRobotDrivetrainSpinner = findViewById(R.id.drivetainTypeSpinner);
        mRobotWeightEditText = (EditText) findViewById(R.id.RobotWeightEditText);

        mRobotPictureImageView = findViewById(R.id.RobotPhoto);

        /*
        mAutoLineTogglebutton = (ToggleButton) findViewById(R.id.AutoLineToggleButton);
        mAutoSwitchTogglebutton = (ToggleButton) findViewById(R.id.AutoSwitchToggleButton);
        mAutoScaleTogglebutton = (ToggleButton) findViewById(R.id.AutoScaleToggleButton);
        mAutoPickTogglebutton = (ToggleButton) findViewById(R.id.AutoPickToggleButton);
        mTeleExchangeTogglebutton = (ToggleButton) findViewById(R.id.TeleExchangeToggleButton);
        mTeleSwitchTogglebutton = (ToggleButton) findViewById(R.id.TeleSwitchToggleButton);
        mTeleScaleTogglebutton = (ToggleButton) findViewById(R.id.TeleScaleToggleButton);
        mTelePickTogglebutton = (ToggleButton) findViewById(R.id.TelePickToggleButton);
        mTelePortalTogglebutton = (ToggleButton) findViewById(R.id.TelePortalToggleButton);
        mTeleClimbTogglebutton = (ToggleButton) findViewById(R.id.TeleClimbToggleButton);
        mTeleHelpClimbTogglebutton = (ToggleButton) findViewById(R.id.TeleHelpToggleButton);
        */

        if(Build.VERSION.SDK_INT >=23) {

            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        Intent intent = getIntent();
        mCurrentScoutUri = intent.getData();

        if( mCurrentScoutUri == null) {

            auto_line = 0;
            auto_pick = 0;
            auto_scale = 0;
            auto_switch = 0;

            tele_exchange = 0;
            tele_switch = 0;
            tele_scale = 0;
            tele_pick = 0;
            tele_portal = 0;
            tele_climb = 0;
            tele_help = 0;

        } else {

            Cursor cursor = null;

            try {

                cursor = getContentResolver().query(mCurrentScoutUri, null, null, null, null);

                String debug = DatabaseUtils.dumpCursorToString(cursor);
                Log.d(TAG, debug);

                // Find the columns of pet attributes that we're interested in
                int matchColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_MATCH);
                int robotColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_ROBOT);
                int weightColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_ROBOT_WEIGHT);
                int drivetrainColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_ROBOT_DRIVETRAIN);
                int scouterColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_SCOUTER);

                /*
                int autoLineColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_AUTO_LINE);
                int autoPickColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_AUTO_CUBE);
                int autoScaleColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_AUTO_SCALE);
                int autoSwitchColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_AUTO_SWITCH);

                int endHelpColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_HELP_CLIMB);
                int endClimbColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_CLIMB);
                int teleExchColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_EXCHANGE);
                int teleAllySwitchColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_ALLY_SWITCH);
                int teleScaleColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_SCALE);
                int telePortalColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_PORTAL);
                int telePickColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_CUBE);
                */

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
                mDrivetrain = cursor.getInt(drivetrainColIdx);
                mWeight = cursor.getInt(weightColIdx);
                mScouter = cursor.getString(scouterColIdx);

                /*
                auto_line = cursor.getInt(autoLineColIdx);
                auto_pick = cursor.getInt(autoPickColIdx);
                auto_scale = cursor.getInt(autoScaleColIdx);
                auto_switch = cursor.getInt(autoSwitchColIdx);

                tele_help = cursor.getInt(endHelpColIdx);
                tele_climb = cursor.getInt(endClimbColIdx);
                tele_exchange = cursor.getInt(teleExchColIdx);
                tele_switch = cursor.getInt(teleAllySwitchColIdx);
                tele_scale = cursor.getInt(teleScaleColIdx);
                tele_portal = cursor.getInt(telePortalColIdx);
                tele_pick = cursor.getInt(telePickColIdx);
                */

                mRobotEditText.setText(mRobot.toString());
                mRobotDrivetrainSpinner.setSelection(mDrivetrain);
                mRobotWeightEditText.setText(mWeight.toString());


            }
            catch(Exception throwable){
                Log.e(TAG, "Could not get data from cursor", throwable);
            }
            finally {
                if(cursor!=null)
                    cursor.close();
            }
        }

        boolean bauto_line = true;
        if(auto_line == 0) bauto_line = false;

        boolean bauto_pick = true;
        if(auto_pick == 0) bauto_pick = false;

        boolean bauto_scale = true;
        if(auto_scale == 0) bauto_scale = false;

        boolean bauto_switch = true;
        if(auto_switch == 0) bauto_switch = false;

        boolean btele_exchange = true;
        if(tele_exchange == 0) btele_exchange = false;

        boolean btele_switch = true;
        if(tele_switch == 0) btele_switch = false;

        boolean btele_scale = true;
        if(tele_scale == 0) btele_scale = false;

        boolean btele_pick = true;
        if(tele_pick == 0) btele_pick = false;

        boolean btele_portal = true;
        if(tele_portal == 0) btele_portal = false;

        boolean btele_climb = true;
        if(tele_climb == 0) btele_climb = false;

        boolean btele_help = true;
        if(tele_help == 0) btele_help = false;

        /*
        mAutoLineTogglebutton.setChecked(bauto_line);
        mAutoSwitchTogglebutton.setChecked(bauto_switch);
        mAutoScaleTogglebutton.setChecked(bauto_scale);
        mAutoPickTogglebutton.setChecked(bauto_pick);
        mTeleExchangeTogglebutton.setChecked(btele_exchange);
        mTeleSwitchTogglebutton.setChecked(btele_switch);
        mTeleScaleTogglebutton.setChecked(btele_scale);
        mTelePickTogglebutton.setChecked(btele_pick);
        mTelePortalTogglebutton.setChecked(btele_portal);
        mTeleClimbTogglebutton.setChecked(btele_climb);
        mTeleHelpClimbTogglebutton.setChecked(btele_climb);
        */

        mNameEditText.setText(mScouter);



        setupSpinner();

    }

    private void setupSpinner() {

        String[] DrivetrainTypes = new String[] { "", "Tank drive", "Mecanum", "Swirl", "Mixte", "Autres"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, DrivetrainTypes);
        mRobotDrivetrainSpinner.setAdapter(spinnerAdapter);

    }

    public void savePit(View view) {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space


        if(mAutoLineTogglebutton.isChecked())
            auto_line = 1;
        else
            auto_line = 0;

        if(mAutoPickTogglebutton.isChecked())
            auto_pick = 1;
        else
            auto_pick = 0;

        if(mAutoScaleTogglebutton.isChecked())
            auto_scale= 1;
        else
            auto_scale = 0;

        if(mAutoSwitchTogglebutton.isChecked())
            auto_switch = 1;
        else
            auto_switch = 0;


        if(mTeleExchangeTogglebutton.isChecked())
            tele_exchange = 1;
        else
            tele_exchange = 0;

        if(mTeleSwitchTogglebutton.isChecked())
            tele_switch = 1;
        else
            tele_switch = 0;

        if(mTeleScaleTogglebutton.isChecked())
            tele_scale = 1;
        else
            tele_scale = 0;

        if(mTelePickTogglebutton.isChecked())
            tele_pick = 1;
        else
            tele_pick = 0;

        if(mTelePortalTogglebutton.isChecked())
            tele_portal = 1;
        else
            tele_portal = 0;

        if(mTeleClimbTogglebutton.isChecked())
            tele_climb = 1;
        else
            tele_climb = 0;

        if(mTeleHelpClimbTogglebutton.isChecked())
            tele_help = 1;
        else
            tele_help = 0;

        mScouter = mNameEditText.getText().toString().trim();

        String robotString = mRobotEditText.getText().toString().trim();
        Integer robot;

        String weightString = mRobotWeightEditText.getText().toString().trim();
        Integer weight;

        Integer drivetrain = mRobotDrivetrainSpinner.getSelectedItemPosition();

        if (robotString.equals("")) {

            Toast.makeText(this, getString(R.string.missing_robot_failed),
                    Toast.LENGTH_LONG).show();

        } else if (mScouter.equals("")) {

            Toast.makeText(this, getString(R.string.missing_scouter_failed),
                    Toast.LENGTH_LONG).show();

        } else {


            robot = Integer.parseInt(robotString);
            weight = Integer.parseInt(weightString);

            // Create a ContentValues object where column names are the keys,
            // and scout attributes from the editor are the values.
            ContentValues values = new ContentValues();
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_SCOUTER, mScouter);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_MATCH, 0);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_ROBOT, robot);

            /*
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_AUTO_LINE, auto_line);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_AUTO_CUBE, auto_pick);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_AUTO_SCALE, auto_scale);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_AUTO_SWITCH, auto_switch);

            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_EXCHANGE, tele_exchange);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_ALLY_SWITCH, tele_switch);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_ENEMY_SWITCH, tele_switch);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_SCALE, tele_scale);

            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_CUBE, tele_pick);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_PORTAL, tele_portal);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_CLIMB, tele_climb);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_HELP_CLIMB, tele_help);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_PARK, 0);
            */

            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_BROKEN, 0);

            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_GAME_ALLY_SCORE, 0);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_GAME_ENEMY_SCORE, 0);

            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_ROBOT_DRIVETRAIN, drivetrain);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_ROBOT_WEIGHT, weight);



            if( mCurrentScoutUri == null ) {
                // Insert a new scout into the provider, returning the content URI for the new scout.
                Uri newUri = getContentResolver().insert(ScoutContract.ScoutEntry.CONTENT_URI, values);

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
            ed.putString("PREF_SCOUTER", mScouter);
            ed.commit();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void takePhoto(View view) {

        String robotString = mRobotEditText.getText().toString().trim();

        if (robotString.equals("")) {
            Toast.makeText(this, getString(R.string.missing_robot_failed),
                    Toast.LENGTH_LONG).show();
        } else {

            dispatchTakePictureIntent();

        }
    }


    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                Log.d(TAG, "photoFile:" + photoFile.getAbsolutePath() );

            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d(TAG, "Can't create Image file" );

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = Uri.fromFile(photoFile);

                //Uri photoURI = FileProvider.getUriForFile(this,
                //        "com.robotronix3550.robotronix_scouting_app.scouts",
                //        photoFile);

                Log.d(TAG, "photoURI:" + photoURI );

                //
                // TO FIX : Something is wrong with the photoURI, preventing the camera
                // to open the file and save the picture
                //
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                Log.d(TAG, "Start Activity take Picture Intent" );

            }
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String robotString = mRobotEditText.getText().toString().trim();
        String timeStamp = new SimpleDateFormat("MM-dd-hh-mm").format(new Date());
        // String imageFileName = "JPEG_" + timeStamp + "_";
        mFileName = robotString + "_" + timeStamp;

        File DocumentDir = FileUtils.getDocumentDir("Scouting");
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                // imageFileName,  /* prefix */
                mFileName,
                ".jpg",         /* suffix */
                //storageDir      /* directory */
                DocumentDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "on Activity Result request code :" + requestCode );
        Log.d(TAG, "on Activity Result result code :" + resultCode );
        // Log.d(TAG, "on Activity Result data :" + requestCode );

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Bundle extras = data.getExtras();
            // Bitmap imageBitmap = (Bitmap) extras.get("data");
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                mRobotPictureImageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
        @Override
    public void onBackPressed(){

        // Intent intent = new Intent(this, MainActivity.class);
        // mScouter = mNameEditText.getText().toString().trim();
        // intent.putExtra(EXTRA_SCOUTER, mScouter);

        SharedPreferences.Editor ed = mPrefs.edit();
        mScouter = mNameEditText.getText().toString().trim();
        ed.putString("PREF_SCOUTER", mScouter);
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
