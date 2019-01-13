package com.robotronix3550.robotronix_scouting_app.data;


import android.os.Environment;
import android.util.Log;

// import com.robotronix3550.robotronix_scouting_app.R;

import java.io.File;

public class FileUtils {

    /** Tag for the log messages */
    public static final String LOG_TAG = FileUtils.class.getSimpleName();

    /*
    public static String getAppDir(){
        return App.getContext().getExternalFilesDir(null) + "/" + App.getContext().getString(R.string.app_name);
    }

    public static File createDirIfNotExist(String path){
        File dir = new File(path);
        if( !dir.exists() ){
            dir.mkdir();
        }
        return dir;
    }
    */

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    /* creates a directory for scouting data in the public document directory */
    public static File getDocumentDir(String ScoutingDir) {
        // Get the directory for the user's public document directory.
        //File file = new File(Environment.getExternalStoragePublicDirectory(
        //        Environment.DIRECTORY_DOCUMENTS), ScoutingDir);
        File file = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath(),ScoutingDir);

        //if (!file.mkdirs()) {
        //    Log.e(LOG_TAG, "Scouting directory not created");
        //}
        return file;
    }

    public static String createScoutingFileName(){
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmm");
        // return "db_backup_" + sdf.format(new Date()) + ".csv";
        return "scout_mtl_tab1" + ".csv";
    }


}