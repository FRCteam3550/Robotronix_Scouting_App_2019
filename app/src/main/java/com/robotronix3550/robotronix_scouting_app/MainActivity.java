package com.robotronix3550.robotronix_scouting_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {



    //String mScouter;

    // String mTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        // mScouter = intent.getStringExtra(CreateMatchActivity.EXTRA_SCOUTER);
        // mTablet = intent.getStringExtra(CreateSettingActivity.EXTRA_TABLET);

    }


    public void fonction2(View view) {
        //Do something in response to button
        Intent intent = new Intent(this, memes.class);
        /*EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);*/
        startActivity(intent);

    }


    public void createMatch(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, CreateMatchActivity.class);
        // intent.putExtra(CreateMatchActivity.EXTRA_SCOUTER, mScouter);
        startActivity(intent);

    }


    public void scoutPit(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ScoutPitActivity.class);
        //intent.putExtra(CreateSettingActivity.EXTRA_TABLET, mTablet);
        startActivity(intent);

    }

    public void reviewMatch(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ReviewMatchActivity.class);
        //intent.putExtra(CreateSettingActivity.EXTRA_TABLET, mTablet);
        startActivity(intent);

    }



    public void createSetting(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, CreateSettingActivity.class);
        //intent.putExtra(CreateSettingActivity.EXTRA_TABLET, mTablet);
        startActivity(intent);

    }

}
