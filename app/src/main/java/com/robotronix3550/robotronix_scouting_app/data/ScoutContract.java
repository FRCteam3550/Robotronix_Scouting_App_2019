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
         * Match number
         * Type: BOOL (INTEGER)
         */
        public final static String COLUMN_SCOUT_SCHEDULE_MATCH ="schedule_match";

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

        public final static String COLUMN_SCOUT_SAND_CARGO_LVL1 = "SandCargoLvl1";
        public final static String COLUMN_SCOUT_SAND_PANEL_LVL1 = "SandPanelLvl1";
        public final static String COLUMN_SCOUT_SAND_CARGO_LVL2 = "SandCargoLvl2";
        public final static String COLUMN_SCOUT_SAND_PANEL_LVL2 = "SandPanelLvl2";
        public final static String COLUMN_SCOUT_SAND_CARGO_LVL3 = "SandCargoLvl3";
        public final static String COLUMN_SCOUT_SAND_PANEL_LVL3 = "SandPanelLvl3";

        public final static String COLUMN_SCOUT_TELE_CARGO_LVL1 = "TeleCargoLvl1";
        public final static String COLUMN_SCOUT_TELE_PANEL_LVL1 = "TelePanelLvl1";
        public final static String COLUMN_SCOUT_TELE_CARGO_LVL2 = "TeleCargoLvl2";
        public final static String COLUMN_SCOUT_TELE_PANEL_LVL2 = "TelePanelLvl2";
        public final static String COLUMN_SCOUT_TELE_PANEL_LVL3 = "TeleCargoLvl3";
        public final static String COLUMN_SCOUT_TELE_CARGO_LVL3 = "TelePanelLvl3";

        public final static String COLUMN_SCOUT_TELE_BLOCKS     = "TeleBlocks";
        public final static String COLUMN_SCOUT_TELE_PINS       = "TelePins";

        public final static String COLUMN_SCOUT_TELE_HAB_LVL1   = "TeleHabLvl1";
        public final static String COLUMN_SCOUT_TELE_HAB_LVL2   = "TeleHabLvl2";
        public final static String COLUMN_SCOUT_TELE_HAB_LVL3   = "TeleHabLvl3";

        public final static String COLUMN_SCOUT_SAND_HAB_LVL1   = "SandHabLvl1";
        public final static String COLUMN_SCOUT_SAND_HAB_LVL2   = "SandHabLvl2";
        public final static String COLUMN_SCOUT_SAND_HAB_LVL3   = "SandHabLvl3";

        public final static String COLUMN_SCOUT_CLIMBTIME  = "ClimbTime";

        //public final static String COLUMN_SCOUT_PIT_ROBOT_WEIGHT    = "RobotWeight";
        //public final static String COLUMN_SCOUT_PIT_ROBOT_DRIVETRAIN    = "RobotDrivetrain";
        public final static String COLUMN_SCOUT_PIT_GROUND_CARGO_PICKUP     = "RobotGroundCargoPickUp";
        public final static String COLUMN_SCOUT_PIT_GROUND_PANEL_PICKUP     = "RobotGroundPanelPickup";
        public final static String COLUMN_SCOUT_PIT_LS_CARGO_PICKUP     ="RobotLSCargoPickUp";
        public final static String COLUMN_SCOUT_PIT_LS_PANEL_PICKUP     = "RobotLSPanelPickUp";

        public final static String COLUMN_SCOUT_MATCH_VICTORY   = "MatchVictory ";
        public final static String COLUMN_SCOUT_MATCH_DEFEAT    = "MatchDefeat";
        public final static String COLUMN_SCOUT_MATCH_TIE   = "MatchTie";
        public final static String COLUMN_SCOUT_IMAGE_ID   = "ImageID";

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

