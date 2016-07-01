package com.example.yamengwenjing.yiyiguanai;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class TestBattery extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_battery);

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);

        BatteryManager mBatteryManger = (BatteryManager) getSystemService(BATTERY_SERVICE);

        double remainingCapacity = mBatteryManger.getIntProperty(
                BatteryManager.BATTERY_PROPERTY_CAPACITY);
        double batteryCapacityMicroAh = mBatteryManger.getIntProperty(
                BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
        double averageCurrentMicroA = mBatteryManger.getIntProperty(
                BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);
        double batteryPropetyCurrentNow = mBatteryManger.getIntProperty(
                BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
        double remainingEnergyCounter = mBatteryManger.getIntProperty(
                BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER);





        Log.d("battery","remainingCapacity"+remainingCapacity);
        Log.d("battery","batteryCapacityMicroAh"+batteryCapacityMicroAh);
        Log.d("battery","averageCurrentMicroA"+averageCurrentMicroA);
        Log.d("battery","batteryPropetyCurrentNow"+batteryCapacityMicroAh);
        Log.d("battery","remainingEnergyCounter"+averageCurrentMicroA);


    }
}
