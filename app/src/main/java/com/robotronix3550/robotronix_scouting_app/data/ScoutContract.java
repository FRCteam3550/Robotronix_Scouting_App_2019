package com.robotronix3550.robotronix_scouting_app.data;


import android.net.Uri;
import android.content.ContentResolver;
import android.provider.BaseColumns;

/**
 * API Contract for the Scouting app.
 */

public final class ScoutContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ScoutContract() {}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.robotronix3550.robotronix_scouting_app.scouts";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.scouts/scouts/ is a valid path for
     * looking at scout data. content://com.example.android.scouts/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_SCOUTS = "scouts";



    /**
     * Inner class that defines constant values for the scouting database table.
     * Each entry in the table represents a single scoutingInfo.
     */
    public static final class ScoutEntry implements BaseColumns {

        /** The content URI to access the pet data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SCOUTS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCOUTS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCOUTS;

        /** Name of database table for scouts */
        public final static String TABLE_NAME = "scouts";

        /**
         * Unique ID number for the scout (only for use in the database table).
         * Type: INTEGER
         */
        public final static String SCOUT_ID = BaseColumns._ID;

        /**
         * Match number
         * Type: INTEGER
         */
        public final static String COLUMN_SCOUT_MATCH ="match";

        /**
         * Robot number
         * Type: INTEGER
         */
        public final static String COLUMN_SCOUT_ROBOT = "robot";

        /**
         * Scouter name
         * Type: TEXT
         */
        public final static String COLUMN_SCOUT_SCOUTER ="scouter";

        /**
         * Robot cross the line in autonomous mode
         * Type: BOOL (INTEGER)
         */
        public final static String COLUMN_SCOUT_AUTO_LINE = "auto_line";

        /**
         * Robot deposit a cube into the switch in autonomous mode
         * Type: BOOL (INTEGER)
         */
        public final static String COLUMN_SCOUT_AUTO_SWITCH = "auto_switch";

        /**
         * Robot deposit a cube into the scale in autonomous mode
         * Type: BOOL (INTEGER)
         */
        public final static String COLUMN_SCOUT_AUTO_SCALE = "auto_scale";

        /**
         * Robot pick a cube in autonomous mode
         * Type: BOOL (INTEGER)
         */
        public final static String COLUMN_SCOUT_AUTO_CUBE = "auto_cube";

        /**
         * Robot push a cube in exchange in teleop mode
         * Type: INTEGER
         */
        public final static String COLUMN_SCOUT_TELE_EXCHANGE = "tele_exchange";

        /**
         * Robot put a cube in ally switch in teleop mode
         * Type: INTEGER
         */
        public final static String COLUMN_SCOUT_TELE_ALLY_SWITCH = "tele_ally_switch";

        /**
         * Robot put a cube in scale in teleop mode
         * Type: INTEGER
         */
        public final static String COLUMN_SCOUT_TELE_SCALE = "tele_scale";


        /**
         * Robot put a cube in enemy switch in teleop mode
         * Type: INTEGER
         */
        public final static String COLUMN_SCOUT_TELE_ENEMY_SWITCH = "tele_enemy_switch";

        /**
         * Robot pick a cube on the ground in teleop mode
         * Type: INTEGER
         */
        public final static String COLUMN_SCOUT_TELE_CUBE = "tele_cube";

        /**
         * Robot receive a cube from portal in teleop mode
         * Type: INTEGER
         */
        public final static String COLUMN_SCOUT_TELE_PORTAL = "tele_portal";

        /**
         * Robot climb at end of game
         * Type: BOOL (INTEGER)
         */
        public final static String COLUMN_SCOUT_TELE_CLIMB = "climb";

        /**
         * Robot help other robots to climb at end of game
         * Type: BOOL (INTEGER)
         */
        public final static String COLUMN_SCOUT_TELE_HELP_CLIMB = "help_climb";

        /**
         * Robot park at end of game
         * Type: BOOL (INTEGER)
         */
        public final static String COLUMN_SCOUT_TELE_PARK = "park";

        /**
         * Robot get incapacitated during the game
         * Type: BOOL (INTEGER)
         */
        public final static String COLUMN_SCOUT_TELE_BROKEN = "broken";

        /**
         * Game score for the alliance
         * Type: INTEGER
         */
        public final static String COLUMN_SCOUT_GAME_ALLY_SCORE = "ally_score";

        /**
         * Game score for the enemy
         * Type: INTEGER
         */
        public final static String COLUMN_SCOUT_GAME_ENEMY_SCORE = "enemy_score";

        /**
         * Scouter name
         * Type: TEXT
         */
        public final static String COLUMN_SCOUT_ROBOT_DRIVETRAIN ="robot_drivetrain";

        /**
         * Game score for the enemy
         * Type: INTEGER
         */
        public final static String COLUMN_SCOUT_ROBOT_WEIGHT = "robot_weight";


        /**
         * Returns robot is valid.
         */
        public static boolean isValidRobot(int robot) {
            if (robot > 0) {
                return true;
            }
            return false;
        }

    }

}

