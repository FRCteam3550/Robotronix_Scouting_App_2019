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
            case 3533: robotImageId = R.drawable.robot_3533;
                break;
            case 3544: robotImageId = R.drawable.robot_3544;
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
            case 4613: robotImageId = R.drawable.robot_4613;
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
            case 6541: robotImageId = R.drawable.robot_6541;
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
            case 7251: robotImageId = R.drawable.robot_7251;
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
            case 7701: robotImageId = R.drawable.robot_7701;
                break;
            case 120: robotImageId = R.drawable.robot_120;
                break;
            case 133: robotImageId = R.drawable.robot_133;
                break;
            case 172: robotImageId = R.drawable.robot_172;
                break;
            case 176: robotImageId = R.drawable.robot_176;
                break;
            case 223: robotImageId = R.drawable.robot_223;
                break;
            case 319: robotImageId = R.drawable.robot_319;
                break;
            case 340: robotImageId = R.drawable.robot_340;
                break;
            case 453: robotImageId = R.drawable.dont_know;
                break;
            case 558: robotImageId = R.drawable.robot_558;
                break;
            case 694: robotImageId = R.drawable.robot_694;
                break;
            case 862: robotImageId = R.drawable.robot_862;
                break;
            case 1091: robotImageId = R.drawable.robot_1091;
                break;
            case 1418: robotImageId = R.drawable.robot_1418;
                break;
            case 1506: robotImageId = R.drawable.robot_1506;
                break;
            case 1574: robotImageId = R.drawable.robot_1574;
                break;
            case 1718: robotImageId = R.drawable.robot_1718;
                break;
            case 1729: robotImageId = R.drawable.robot_1729;
                break;
            case 1787: robotImageId = R.drawable.robot_1787;
                break;
            case 1885: robotImageId = R.drawable.robot_1885;
                break;
            case 1922: robotImageId = R.drawable.robot_1922;
                break;
            case 2062: robotImageId = R.drawable.robot_2062;
                break;
            case 2075: robotImageId = R.drawable.robot_2075;
                break;
            case 2200: robotImageId = R.drawable.robot_2200;
                break;
            case 2202: robotImageId = R.drawable.robot_2202;
                break;
            case 2611: robotImageId = R.drawable.robot_2611;
                break;
            case 2614: robotImageId = R.drawable.robot_2614;
                break;
            case 2619: robotImageId = R.drawable.robot_2619;
                break;
            case 2638: robotImageId = R.drawable.robot_2638;
                break;
            case 2673: robotImageId = R.drawable.robot_2673;
                break;
            case 2702: robotImageId = R.drawable.robot_2702;
                break;
            case 2869: robotImageId = R.drawable.robot_2869;
                break;
            case 2883: robotImageId = R.drawable.robot_2883;
                break;
            case 2957: robotImageId = R.drawable.robot_2957;
                break;
            case 2994: robotImageId = R.drawable.robot_2994;
                break;
            case 3075: robotImageId = R.drawable.robot_3075;
                break;
            case 3193: robotImageId = R.drawable.robot_3193;
                break;
            case 3414: robotImageId = R.drawable.robot_3414;
                break;
            case 3630: robotImageId = R.drawable.dont_know;
                break;
            case 3641: robotImageId = R.drawable.robot_3641;
                break;
            case 4003: robotImageId = R.drawable.robot_4003;
                break;
            case 4039: robotImageId = R.drawable.robot_4039;
                break;
            case 4096: robotImageId = R.drawable.robot_4096;
                break;
            case 4130: robotImageId = R.drawable.robot_4130;
                break;
            case 4525: robotImageId = R.drawable.robot_4525;
                break;
            case 4541: robotImageId = R.drawable.robot_4541;
                break;
            case 4593: robotImageId = R.drawable.robot_4593;
                break;
            case 4680: robotImageId = R.drawable.robot_4680;
                break;
            case 4728: robotImageId = R.drawable.robot_4728;
                break;
            case 4903: robotImageId = R.drawable.dont_know;
                break;
            case 4946: robotImageId = R.drawable.robot_4946;
                break;
            case 4970: robotImageId = R.drawable.robot_4970;
                break;
            case 5125: robotImageId = R.drawable.robot_5125;
                break;
            case 5724: robotImageId = R.drawable.robot_5724;
                break;
            case 5740: robotImageId = R.drawable.robot_5740;
                break;
            case 5926: robotImageId = R.drawable.robot_5926;
                break;
            case 6016: robotImageId = R.drawable.robot_6016;
                break;
            case 6024: robotImageId = R.drawable.robot_6024;
                break;
            case 6088: robotImageId = R.drawable.robot_6088;
                break;
            case 6237: robotImageId = R.drawable.robot_6237;
                break;
            case 6455: robotImageId = R.drawable.robot_6455;
                break;
            case 7417: robotImageId = R.drawable.robot_7417;
                break;
            case 7491: robotImageId = R.drawable.robot_7491;
                break;
            case 7523: robotImageId = R.drawable.robot_7523;
                break;
            case 7530: robotImageId = R.drawable.robot_7530;
                break;
            case 7645: robotImageId = R.drawable.robot_7645;
                break;
            case 7797: robotImageId = R.drawable.robot_7797;
                break;


                default: robotImageId = R.drawable.dont_know;
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
