package com.example.yamengwenjing.yiyiguanai;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.yamengwenjing.publicsharedkey.PublicSharedKey;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class ListenerService extends WearableListenerService {


    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        DataMap dataMap;
        for (DataEvent event : dataEvents) {
            Log.v("myTag", "DataMap received on watch: " + DataMapItem.fromDataItem(event.getDataItem()).getDataMap());
            // Check the data type
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // Check the data path
                String path = event.getDataItem().getUri().getPath();
                if (path.equals(PublicSharedKey.HELD_HOLDER_PATH)) {}
                dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();

                //TODO STORE IN DB
                //TODO IF MOA IS REALTIME LEARNING  SHOULD TRIGGER ANOTHER SENSOR

                // Broadcast DataMap contents to wearable activity for display
                // The content has the golf hole number and distances to the front,
                // middle and back pin placements.

//                Intent messageIntent = new Intent();
//                messageIntent.setAction(Intent.ACTION_SEND);
//                messageIntent.putExtra("datamap", dataMap.toBundle());
//                LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);

            }
        }
    }
}
