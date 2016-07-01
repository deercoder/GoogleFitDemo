package com.example.yamengwenjing.yiyiguanai.dbPackage;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yamengwenjing on 2016/2/23.
 */

@DatabaseTable(tableName = "SensorInfo")
public class SensorDbEntity {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "SensorName")
    private String sensorName;

    @DatabaseField(columnName = "SensorData")
    private  String sensorData;

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

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorData() {
        return sensorData;
    }

    public void setSensorData(String sensorData) {
        this.sensorData = sensorData;
    }


    public SensorDbEntity() {

    }

    public SensorDbEntity(String timeStamp, String sensorName, String sensorData) {
        this.timeStamp = timeStamp;
        this.sensorName = sensorName;
        this.sensorData = sensorData;
    }

    @Override
    public String toString() {
        return "SensorDbEntity{" +
                "sensorName='" + sensorName + '\'' +
                ", sensorData='" + sensorData + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
