package com.robotronix3550.robotronix_scouting_app.data;

/**
 * Created by tsinanyp on 2/16/2018.
 */

public class ScoutInfo {

    Integer db_id;
    Integer Match;
    Integer Robot;
    String  Scouter;

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

    public ScoutInfo(Integer id, Integer match, Integer robot) {
        db_id = id;
        Match = match;
        Robot = robot;
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
