package com.example.yamengwenjing.yiyiguanai.dbPackage;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by cliu on 7/2/16.
 *
 *
 * Database that stores the activity information, which is transmitted from the wear
 *
 * the activity includes: Walking, sitting, house-keeping etc...
 * Also, the start time and end time is stored.
 *
 */
public class ActivityInfoDbEntity {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "ActivityName")
    private String activityName;

    //// may be used later, as the name is data right now...
    /*
    @DatabaseField(columnName = "SensorData")
    private  String sensorData;
    */

    @DatabaseField(columnName = "TimeStamp")
    private  String timeStamp;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String sensorName) {
        this.activityName = sensorName;
    }

    /*
    public String getSensorData() {
        return sensorData;
    }

    public void setSensorData(String sensorData) {
        this.sensorData = sensorData;
    }
    */

    public ActivityInfoDbEntity() {

    }

    public ActivityInfoDbEntity(String timeStamp, String actName) {
        this.timeStamp = timeStamp;
        this.activityName = actName;
    }

    @Override
    public String toString() {
        return "ActivityInfoDbEntity{" +
                "activityName='" + activityName + '\''
                + ", timeStamp='" + timeStamp + '\'' +
                '}';
    }



}
