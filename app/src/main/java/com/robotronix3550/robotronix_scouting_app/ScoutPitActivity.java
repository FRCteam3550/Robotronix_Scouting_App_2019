package com.robotronix3550.robotronix_scouting_app;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private EditText mScoutNameEditText;
    private Spinner mRobotDrivetrainSpinner;
    private EditText mRobotWeightEditText;

    private ToggleButton GroundCargoPickUpToggleButton;
    private ToggleButton GroundPanelPickUpToggleButton;
    private ToggleButton LoadingStationCargoPickUpToggleButton;
    private ToggleButton LoadingStationPanelPickUpToggleButton;

    private SharedPreferences mPrefs;
    private String mFileName;

    ImageView mRobotPictureImageView;

    Uri mCurrentScoutUri;

    Integer mRobot;
    Integer mDrivetrain;
    Integer mWeight;
    Integer mMatch;
    String  mScoutName;

    Boolean mGroundCargoPickUp;
    Boolean mGroundPanelPickUp;
    Boolean mLoadingStationCargoPickUp;
    Boolean mLoadingStationPanelPickUp;

    Integer mIntGroundCargoPickUp;
    Integer mIntGroundPanelPickUp;
    Integer mIntLoadingStationCargoPickUp;
    Integer mIntLoadingStationPanelPickUp;


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
        mScoutName = mPrefs.getString("PREF_SCOUTER", "Prenom");

        mRobotEditText = (EditText) findViewById(R.id.TeamNumberEditText);
        mScoutNameEditText = (EditText) findViewById(R.id.ScoutNameEditText);

        mRobotDrivetrainSpinner = findViewById(R.id.drivetainTypeSpinner);
        mRobotWeightEditText = (EditText) findViewById(R.id.RobotWeightEditText);

        mRobotPictureImageView = findViewById(R.id.RobotPhoto);


        GroundCargoPickUpToggleButton = (ToggleButton) findViewById(R.id.GroundCargoPickUpToggleButton);
        GroundPanelPickUpToggleButton = (ToggleButton) findViewById(R.id.GroundPanelPickUpToggleButton);
        LoadingStationCargoPickUpToggleButton = (ToggleButton) findViewById(R.id.LoadingStationCargoPickUpToggleButton);
        LoadingStationPanelPickUpToggleButton = (ToggleButton) findViewById(R.id.LoadingStationPanelPickUpToggleButton);

        if(Build.VERSION.SDK_INT >=23) {

            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        Intent intent = getIntent();
        mCurrentScoutUri = intent.getData();

        if( mCurrentScoutUri == null) {

            mGroundCargoPickUp = false;
            mGroundPanelPickUp = false;
            mLoadingStationCargoPickUp = false;
            mLoadingStationPanelPickUp = false;
            mDrivetrain = 0;

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
                int groundpanelColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_PIT_GROUND_PANEL_PICKUP);
                int groundcargoColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_PIT_GROUND_CARGO_PICKUP);
                int lscargoColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_PIT_LS_CARGO_PICKUP);
                int lspanelColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_PIT_LS_PANEL_PICKUP);
                int imageColIdx = cursor.getColumnIndex(ScoutContract.ScoutEntry.COLUMN_SCOUT_IMAGE_ID);

                cursor.moveToFirst();
                boolean isFirst = cursor.isFirst();

                // db_id = cursor.getInt(0);
                mMatch = cursor.getInt(matchColIdx);
                mRobot = cursor.getInt(robotColIdx);
                mDrivetrain = cursor.getInt(drivetrainColIdx);
                mWeight = cursor.getInt(weightColIdx);
                mScoutName = cursor.getString(scouterColIdx);
                mIntGroundCargoPickUp = cursor.getInt(groundcargoColIdx);
                mIntGroundPanelPickUp = cursor.getInt(groundpanelColIdx);
                mIntLoadingStationCargoPickUp = cursor.getInt(lscargoColIdx);
                mIntLoadingStationPanelPickUp = cursor.getInt(lspanelColIdx);
                if(cursor.isNull(imageColIdx)) {
                    mCurrentPhotoPath = null;
                } else {
                    mCurrentPhotoPath = cursor.getString(imageColIdx);
                }

                mGroundCargoPickUp = true;
                if(mIntGroundCargoPickUp == 0) mGroundCargoPickUp = false;

                mGroundPanelPickUp = true;
                if(mIntGroundPanelPickUp== 0) mGroundPanelPickUp = false;

                mLoadingStationCargoPickUp = true;
                if(mIntLoadingStationCargoPickUp == 0) mLoadingStationCargoPickUp = false;

                mLoadingStationPanelPickUp = true;
                if(mIntLoadingStationPanelPickUp == 0) mLoadingStationPanelPickUp = false;

                mRobotEditText.setText(mRobot.toString());
                mRobotDrivetrainSpinner.setSelection(mDrivetrain);
                mRobotWeightEditText.setText(mWeight.toString());

                if( mCurrentPhotoPath != null ) {
                    File imageFile = new File(mCurrentPhotoPath);
                    if (imageFile.exists()) {
                        Log.d(TAG, "image found, setting image view ");

                        mRobotPictureImageView.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
                    } else {

                        Log.d(TAG, "image File doesn't exist ");

                    }
                } else {
                    Log.d(TAG, "no image File");

                }

                //Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                //mRobotPictureImageView.setImageBitmap(imageBitmap);

            }
            catch(Exception throwable){
                Log.e(TAG, "Could not get data from cursor", throwable);
            }
            finally {
                if(cursor!=null)
                    cursor.close();
            }
        }

         GroundCargoPickUpToggleButton.setChecked(mGroundCargoPickUp);
        GroundPanelPickUpToggleButton.setChecked(mGroundPanelPickUp);
        LoadingStationCargoPickUpToggleButton.setChecked(mLoadingStationCargoPickUp);
        LoadingStationPanelPickUpToggleButton.setChecked(mLoadingStationPanelPickUp);
        //



        mScoutNameEditText.setText(mScoutName);

        setupSpinner();

    }

    private void setupSpinner() {

        String[] DrivetrainTypes = new String[] { "", "Tank drive", "Mecanum", "Swirv", "H drive", "Autres"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, DrivetrainTypes);
        mRobotDrivetrainSpinner.setAdapter(spinnerAdapter);


        mRobotDrivetrainSpinner.setSelection(mDrivetrain);

    }

    public void savePit(View view) {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space



        if(GroundCargoPickUpToggleButton.isChecked())
            mIntGroundCargoPickUp = 1;
        else
            mIntGroundCargoPickUp = 0;

        if(GroundPanelPickUpToggleButton.isChecked())
            mIntGroundPanelPickUp = 1;
        else
            mIntGroundPanelPickUp = 0;

        if(LoadingStationCargoPickUpToggleButton.isChecked())
            mIntLoadingStationCargoPickUp = 1;
        else
            mIntLoadingStationCargoPickUp = 0;

        if(LoadingStationPanelPickUpToggleButton.isChecked())
            mIntLoadingStationPanelPickUp = 1;
        else
            mIntLoadingStationPanelPickUp = 0;

        mScoutName = mScoutNameEditText.getText().toString().trim();

        if(mCurrentPhotoPath == null) mCurrentPhotoPath = "";

        String robotString = mRobotEditText.getText().toString().trim();
        Integer robot;

        String weightString = mRobotWeightEditText.getText().toString().trim();
        Integer weight;
        if(weightString.equals("")) {
            weight = 0;
        } else {
            weight = Integer.parseInt(weightString);
        }

        Integer drivetrain = mRobotDrivetrainSpinner.getSelectedItemPosition();

        if (robotString.equals("")) {

            Toast.makeText(this, getString(R.string.missing_robot_failed),
                    Toast.LENGTH_LONG).show();

        } else if (mScoutName.equals("")) {

            Toast.makeText(this, getString(R.string.missing_scouter_failed),
                    Toast.LENGTH_LONG).show();

        } else {


            robot = Integer.parseInt(robotString);

            // Create a ContentValues object where column names are the keys,
            // and scout attributes from the editor are the values.
            ContentValues values = new ContentValues();
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_SCOUTER, mScoutName);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_MATCH, 0);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_ROBOT, robot);

            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_PIT_GROUND_CARGO_PICKUP, mIntGroundCargoPickUp);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_PIT_GROUND_PANEL_PICKUP, mIntGroundPanelPickUp);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_PIT_LS_CARGO_PICKUP, mIntLoadingStationCargoPickUp);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_PIT_LS_PANEL_PICKUP, mIntLoadingStationPanelPickUp);

            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_ROBOT_DRIVETRAIN, drivetrain);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_ROBOT_WEIGHT, weight);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_IMAGE_ID, mFileName);



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
            ed.putString("PREF_SCOUTER", mScoutName);
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

    String mCurrentPhotoPath = "";

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
        mFileName = image.getAbsolutePath();
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
        mScoutName = mScoutNameEditText.getText().toString().trim();
        ed.putString("PREF_SCOUTER", mScoutName);
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
