package com.robotronix3550.robotronix_scouting_app.data;

import com.robotronix3550.robotronix_scouting_app.R;

/**
 * Created by tsinanyp on 2/16/2018.
 */

public class ScoutInfo {

    Integer db_id;
    Integer Match;
    Integer Robot;
    String  Scouter;
    Boolean ScheduleMatch;

    Integer Auto_line;
    Integer Auto_switch;
    Integer Auto_scale;
    Integer Auto_pick;

    Integer tele_exchange;
    Integer tele_ally_switch;
    Integer tele_scale;
    Integer tele_enemy_switch;
    Integer tele_pick;
    Integer tele_portal;
    Integer tele_climb;
    Integer tele_help_climb;
    Integer tele_park;
    Integer tele_broken;

    Integer game_ally_score;
    Integer game_enemy_score;

    String  robot_drivetrain;
    Integer robot_weight;

    Integer robotImageId;

    public ScoutInfo(Integer id, Integer match, Integer robot, String scouter, Integer schedule) {
        db_id = id;
        Match = match;
        Robot = robot;
        Scouter = scouter;

        if (schedule == 1)
            ScheduleMatch = true;
        else
            ScheduleMatch = false;

        switch( robot ) {
            case 229: robotImageId = R.drawable.robot_229;
                break;
            case 296: robotImageId = R.drawable.robot_296;
                break;
            case 1511: robotImageId = R.drawable.robot_1511;
                break;
            case 2095: robotImageId = R.drawable.robot_2095;
                break;
            case 2234: robotImageId = R.drawable.robot_2234;
                break;
            case 2626: robotImageId = R.drawable.robot_2626;
                break;
            case 3117: robotImageId = R.drawable.robot_3117;
                break;
            case 3360: robotImageId = R.drawable.robot_3360;
                break;
            case 3379: robotImageId = R.drawable.robot_3379;
                break;
            case 3386: robotImageId = R.drawable.robot_3386;
                break;
            case 3510: robotImageId = R.drawable.robot_3510;
                break;
            case 3532: robotImageId = R.drawable.robot_3532;
                break;
            case 3533: robotImageId = R.drawable.robot_3544;
                break;
            case 3550: robotImageId = R.drawable.robot_3550;
                break;
            case 3975: robotImageId = R.drawable.robot_3975;
                break;
            case 3981: robotImageId = R.drawable.robot_3981;
                break;
            case 3985: robotImageId = R.drawable.robot_3985;
                break;
            case 3986: robotImageId = R.drawable.robot_3986;
                break;
            case 3988: robotImageId = R.drawable.robot_3988;
                break;
            case 3990: robotImageId = R.drawable.robot_3990;
                break;
            case 3996: robotImageId = R.drawable.robot_3996;
                break;
            case 4594: robotImageId = R.drawable.robot_4594;
                break;
            case 4930: robotImageId = R.drawable.robot_4930;
                break;
            case 4942: robotImageId = R.drawable.robot_4942;
                break;
            case 4947: robotImageId = R.drawable.robot_4947;
                break;
            case 4950: robotImageId = R.drawable.robot_4950;
                break;
            case 4952: robotImageId = R.drawable.robot_4952;
                break;
            case 4955: robotImageId = R.drawable.robot_4955;
                break;
            case 4957: robotImageId = R.drawable.robot_4957;
                break;
            case 5095: robotImageId = R.drawable.robot_5095;
                break;
            case 5179: robotImageId = R.drawable.robot_5179;
                break;
            case 5439: robotImageId = R.drawable.robot_5439;
                break;
            case 5440: robotImageId = R.drawable.robot_5440;
                break;
            case 5443: robotImageId = R.drawable.robot_5443;
                break;
            case 5528: robotImageId = R.drawable.robot_5528;
                break;
            case 5553: robotImageId = R.drawable.robot_5553;
                break;
            case 5570: robotImageId = R.drawable.robot_5570;
                break;
            case 5618: robotImageId = R.drawable.robot_5618;
                break;
            case 5659: robotImageId = R.drawable.robot_5659;
                break;
            case 5800: robotImageId = R.drawable.robot_5800;
                break;
            case 5865: robotImageId = R.drawable.robot_5865;
                break;
            case 5910: robotImageId = R.drawable.robot_5910;
                break;
            case 5952: robotImageId = R.drawable.robot_5952;
                break;
            case 6431: robotImageId = R.drawable.robot_6431;
                break;
            case 6540: robotImageId = R.drawable.robot_6540;
                break;
            case 6622: robotImageId = R.drawable.robot_6622;
                break;
            case 6851: robotImageId = R.drawable.robot_6851;
                break;
            case 6869: robotImageId = R.drawable.robot_6869;
                break;
            case 6872: robotImageId = R.drawable.robot_6872;
                break;
            case 6929: robotImageId = R.drawable.robot_6929;
                break;
            case 6953: robotImageId = R.drawable.robot_6953;
                break;
            case 7053: robotImageId = R.drawable.robot_7053;
                break;
            case 7116: robotImageId = R.drawable.robot_7116;
                break;
            case 7134: robotImageId = R.drawable.robot_7134;
                break;
            case 7162: robotImageId = R.drawable.robot_7162;
                break;
            case 7471: robotImageId = R.drawable.robot_7471;
                break;
            case 7555: robotImageId = R.drawable.robot_7555;
                break;
            case 7574: robotImageId = R.drawable.robot_7574;
                break;
            case 7590: robotImageId = R.drawable.robot_7590;
                break;
            case 7605: robotImageId = R.drawable.robot_7605;
                break;
            case 7615: robotImageId = R.drawable.robot_7615;
                break;
            case 7700: robotImageId = R.drawable.robot_7700;
                break;

                default: robotImageId = R.drawable.robotronixlogo;
            break;
        }
    }

    public Integer getDb_id() {
        return db_id;
    }

    public void setDb_id(Integer db_id) {
        this.db_id = db_id;
    }

    public Integer getMatch() {
        return Match;
    }

    public void setMatch(Integer match) {
        Match = match;
    }

    public Integer getRobot() {
        return Robot;
    }

    public void setRobot(Integer robot) {
        Robot = robot;
    }

    public Boolean getScheduleMatch() { return ScheduleMatch; }

    public void setScheduleMatch(Boolean match) { ScheduleMatch = match; }

    public String getScouter() {
        return Scouter;
    }

    public void setScouter(String scouter) {
        Scouter = scouter;
    }

    public Integer getAuto_line() {
        return Auto_line;
    }

    public void setAuto_line(Integer auto_line) {
        Auto_line = auto_line;
    }

    public Integer getAuto_switch() {
        return Auto_switch;
    }

    public void setAuto_switch(Integer auto_switch) {
        Auto_switch = auto_switch;
    }

    public Integer getAuto_scale() {
        return Auto_scale;
    }

    public void setAuto_scale(Integer auto_scale) {
        Auto_scale = auto_scale;
    }

    public Integer getAuto_pick() {
        return Auto_pick;
    }

    public void setAuto_pick(Integer auto_pick) {
        Auto_pick = auto_pick;
    }

    public Integer getTele_exchange() {
        return tele_exchange;
    }

    public void setTele_exchange(Integer tele_exchange) {
        this.tele_exchange = tele_exchange;
    }

    public Integer getTele_ally_switch() {
        return tele_ally_switch;
    }

    public void setTele_ally_switch(Integer tele_ally_switch) {
        this.tele_ally_switch = tele_ally_switch;
    }

    public Integer getTele_scale() {
        return tele_scale;
    }

    public void setTele_scale(Integer tele_scale) {
        this.tele_scale = tele_scale;
    }

    public Integer getTele_enemy_switch() {
        return tele_enemy_switch;
    }

    public void setTele_enemy_switch(Integer tele_enemy_switch) {
        this.tele_enemy_switch = tele_enemy_switch;
    }

    public Integer getTele_pick() {
        return tele_pick;
    }

    public void setTele_pick(Integer tele_pick) {
        this.tele_pick = tele_pick;
    }

    public Integer getTele_portal() {
        return tele_portal;
    }

    public void setTele_portal(Integer tele_portal) {
        this.tele_portal = tele_portal;
    }

    public Integer getTele_climb() {
        return tele_climb;
    }

    public void setTele_climb(Integer tele_climb) {
        this.tele_climb = tele_climb;
    }

    public Integer getTele_help_climb() {
        return tele_help_climb;
    }

    public void setTele_help_climb(Integer tele_help_climb) {
        this.tele_help_climb = tele_help_climb;
    }

    public Integer getTele_park() {
        return tele_park;
    }

    public void setTele_park(Integer tele_park) {
        this.tele_park = tele_park;
    }

    public Integer getTele_broken() {
        return tele_broken;
    }

    public void setTele_broken(Integer tele_broken) {
        this.tele_broken = tele_broken;
    }

    public Integer getGame_ally_score() {
        return game_ally_score;
    }

    public void setGame_ally_score(Integer game_ally_score) {
        this.game_ally_score = game_ally_score;
    }

    public Integer getGame_enemy_score() {
        return game_enemy_score;
    }

    public void setGame_enemy_score(Integer game_enemy_score) {
        this.game_enemy_score = game_enemy_score;
    }

    public String getRobot_drivetrain() {
        return robot_drivetrain;
    }

    public void setRobot_drivetrain(String robot_drivetrain) {
        this.robot_drivetrain = robot_drivetrain;
    }

    public Integer getRobot_weight() {
        return robot_weight;
    }

    public void setRobot_weight(Integer robot_weight) {
        this.robot_weight = robot_weight;
    }

    public Integer getRobotImageId() {
        return robotImageId;
    }

    public void setRobotImageId(Integer robotImageId) {
        this.robotImageId = robotImageId;
    }
}
