package com.example.yamengwenjing.yiyiguanai.UI.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yamengwenjing.yiyiguanai.MainActivity;
import com.example.yamengwenjing.yiyiguanai.R;
import com.google.android.gms.common.api.GoogleApiClient;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataUpdateRequest;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.fitness.result.DataReadResult;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.text.DateFormat.getDateInstance;
import static java.text.DateFormat.getTimeInstance;



/**
 * A simple {@link Fragment} subclass.
 */
public class monitorFragment extends Fragment {


    private Spinner monitorSpinner ;
    private ListView dateListView;
    Context currentContext;


    /// textview for showing the content of [Chang Liu]
    private TextView calorieView;
    private TextView stepcountView;
    private TextView activitysummaryView;

    /// Necessary info [Chang Liu]
    public static final String TAG = "BasicHistoryApi";
    private static final int REQUEST_OAUTH = 1;
    private static final String DATE_FORMAT = "yyyy.MM.dd HH:mm:ss";
    private static final String AUTH_PENDING = "auth_state_pending";
    private static boolean authInProgress = false;
    public static GoogleApiClient mClient = null;

    private String CalorieInfo = "当日消耗卡路里信息: ";
    private String StepCounterInfo = "当日走路步数信息: ";
    private String ActivityInfo = "当日运动信息统计: ";

    public static int mTotalStepCount = 0;
    public static double mTotalColorie = 0;
    private int futureActivitySummary = 0;

    //adapter for spinner
    private ArrayAdapter<String> spinnerAdapter = null;
    private static final String[] monitorCategory  = {"","实时健康监控","健康预警"};
    private ArrayAdapter<String> listViewSimpleAdapter;
    private String[] dateStingArray = {"2015/4/26","2015/4/27","2015/4/28","2015/4/29"};

    public monitorFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisView = inflater.inflate(R.layout.fragment_monitor,container,false);
        currentContext = getContext();
        initView(thisView);
        initEvent();

        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }
        return thisView;
    }


    private void initView(View thisView) {
         monitorSpinner = (Spinner) thisView.findViewById(R.id.moniter_fragment_chosen_spinner);
        spinnerAdapter = new ArrayAdapter<String>(currentContext,R.layout.moniter_fragment_spinner_item,monitorCategory);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monitorSpinner.setAdapter(spinnerAdapter);
//        monitorSpinner.setVisibility(View.VISIBLE);

        dateListView = (ListView)thisView.findViewById(R.id.moniter_fragment_chosen_listView);
        listViewSimpleAdapter = new ArrayAdapter<String>(currentContext,android.R.layout.simple_list_item_1,android.R.id.text1,dateStingArray);
        dateListView.setAdapter(listViewSimpleAdapter);

        /// init the item of textview
        calorieView =  (TextView)thisView.findViewById(R.id.health_alert_calorie);
        stepcountView = (TextView)thisView.findViewById(R.id.health_alert_stepcount);
        activitysummaryView = (TextView) thisView.findViewById(R.id.health_alert_activitysummary);

        /// [Chang Liu]
        buildFitnessClient();


    }

    private void initEvent() {
        monitorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position !=0) {
                    Toast.makeText(currentContext, "your choice is " + monitorCategory[position], Toast.LENGTH_SHORT).show();
                }

                if (position == 2) {
                    Log.e("AAAA", "Jian Kang Yu jing!!!");
                    setStatisticViewContent();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setStatisticViewContent() {

        calorieView.setText(getCalorieInfo());
        stepcountView.setText(getStepInfo());

        /// for future extension...
      //  activitysummaryView.setText(getActivitySummaryInfo());

    }


    private String getCalorieInfo() {
        String result = CalorieInfo + mTotalColorie + " Cal";
        Log.e("CCCC", ""+ mTotalColorie);
        Log.e("CCCC", result);
        int goal = 3000;
        double rate = mTotalColorie * 1.0 / goal;

        if (rate >= 1) {
            result += "(很好! 您已经完成目标, 继续保持!)";
        }
        else if (rate > 0.5) {
            result += "(您已经完成一半以上的目标, 继续加油!)";
        }
        else if (rate > 0.3) {
            result += "(您还未达到一半目标, 请多运动减少卡路里!)";
        }
        else if (rate < 0.3) {
            result += "(您的运动量严重不足, 请多运动!)";
        }

        return result;
    }

    private String getStepInfo() {
        String result = StepCounterInfo + mTotalStepCount;
        Log.e("DDDD", ""+ mTotalStepCount);
        Log.e("DDDD", result);

        int goal = 2000;
        double rate = mTotalColorie * 1.0 / goal;

        if (rate >= 1) {
            result += "(很好! 您已经完成目标, 继续保持!)";
        }
        else if (rate > 0.5) {
            result += "(您已经完成一半以上的目标, 继续加油!)";
        }
        else if (rate > 0.3) {
            result += "(您还未达到一半目标, 请多运动减少卡路里!)";
        }
        else if (rate < 0.3) {
            result += "(您的运动量严重不足, 请多运动!)";
        }

        return result;
    }

    private String getActivitySummaryInfo() {
        String result = ActivityInfo;

        return result;
    }

    /// [Chang Liu] for setting up the request of info request, and set content
    private void buildFitnessClient() {
        // Create the Google API Client
        mClient = new GoogleApiClient.Builder(this.getContext())
                .addApi(Fitness.HISTORY_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                                Log.i(TAG, "Connected!!!");
                                // Now you can make calls to the Fitness APIs.  What to do?
                                // Look at some data!!
                                new InsertAndVerifyDataTask().execute();
                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                                // If your connection to the sensor gets lost at some point,
                                // you'll be able to determine the reason and react to it here.
                                if (i == ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                    Log.i(TAG, "Connection lost.  Cause: Network Lost.");
                                } else if (i == ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                    Log.i(TAG, "Connection lost.  Reason: Service Disconnected");
                                }
                            }
                        }
                )
                .enableAutoManage(this.getActivity(), 0, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.i(TAG, "Google Play services connection failed. Cause: " +
                                result.toString());
                       Toast.makeText(getContext(),
                                "Exception while connecting to Google Play services: ",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }



    /// this is executed in the background, it seesm that our data is correct, but cannot
    /// set to the mXXXX, so that the shows information is still 0.
    /// need to know the multi-class setting of values.
    private class InsertAndVerifyDataTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            // Begin by creating the query.
            DataReadRequest readRequest = queryFitnessData();

            DataReadResult dataReadResult =
                    Fitness.HistoryApi.readData(mClient, readRequest).await(1, TimeUnit.MINUTES);
            printData(dataReadResult);

            /// begin calorie query
            DataReadRequest readCalorieRequest = queryFitnessCalorieData();
            DataReadResult dataReadCalorieResult =
                    Fitness.HistoryApi.readData(mClient, readCalorieRequest).await(1, TimeUnit.MINUTES);
            printData(dataReadCalorieResult);

            /// NOW that we set the calorie information.[Chang Liu]
            mTotalColorie = parseCalorieData(dataReadCalorieResult);
            /// end calorie query

            /// calculate the steps for a day
            setStepsToday();

            /// calculate the power information(PROBLEM!!!!!)
            //DataReadRequest readPowerRequest  = queryFitnessPowerData();
            //DataReadResult dataReadPowerResult =
            //         Fitness.HistoryApi.readData(mClient, readPowerRequest).await(1, TimeUnit.MINUTES);
            //printData(dataReadPowerResult);
            /// ends of power information

            return null;
        }
    }

    public static DataReadRequest queryFitnessData() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        long startTime = cal.getTimeInMillis();

        java.text.DateFormat dateFormat = getDateInstance();
        Log.i(TAG, "Range Start: " + dateFormat.format(startTime));
        Log.i(TAG, "Range End: " + dateFormat.format(endTime));

        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        return readRequest;
    }

    public static DataReadRequest queryFitnessCalorieData() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        long startTime = cal.getTimeInMillis();

        java.text.DateFormat dateFormat = getDateInstance();
        Log.i(TAG, "Range Start: " + dateFormat.format(startTime));
        Log.i(TAG, "Range End: " + dateFormat.format(endTime));

        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_CALORIES_EXPENDED, DataType.AGGREGATE_CALORIES_EXPENDED)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        return readRequest;
    }

    private static void setStepsToday() {
        PendingResult<DailyTotalResult> result = Fitness.HistoryApi.readDailyTotal(mClient, DataType.TYPE_STEP_COUNT_DELTA);
        DailyTotalResult totalResult = result.await(30, TimeUnit.SECONDS);
        if (totalResult.getStatus().isSuccess()) {
            DataSet totalSet = totalResult.getTotal();
            int total = totalSet.isEmpty()
                    ? 0
                    : totalSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();

            /// set the total calorie information into memeber variables
            mTotalStepCount = total;
            Log.e(TAG, "today's step counters are " + total);
            dumpDataSet(totalSet);
        } else {
            Log.e(TAG, "Error, so we record it as 0 step counter!");
        }
    }

    public static DataReadRequest queryFitnessPowerData() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        long startTime = cal.getTimeInMillis();

        java.text.DateFormat dateFormat = getDateInstance();
        Log.i(TAG, "Range Start: " + dateFormat.format(startTime));
        Log.i(TAG, "Range End: " + dateFormat.format(endTime));

        DataReadRequest readRequest = new DataReadRequest.Builder()
                /// what the fuck here!!!!, what is the base type???
                .aggregate(DataType.TYPE_POWER_SAMPLE, DataType.AGGREGATE_POWER_SUMMARY)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        return readRequest;
    }


    public static void printData(DataReadResult dataReadResult) {
        if (dataReadResult.getBuckets().size() > 0) {
            Log.i(TAG, "Number of returned buckets of DataSets is: "
                    + dataReadResult.getBuckets().size());
            for (Bucket bucket : dataReadResult.getBuckets()) {
                List<DataSet> dataSets = bucket.getDataSets();
                for (DataSet dataSet : dataSets) {
                    dumpDataSet(dataSet);
                }
            }
        } else if (dataReadResult.getDataSets().size() > 0) {
            Log.i(TAG, "Number of returned DataSets is: "
                    + dataReadResult.getDataSets().size());
            for (DataSet dataSet : dataReadResult.getDataSets()) {
                dumpDataSet(dataSet);
            }
        }
        // [END parse_read_data_result]
    }

    public static double parseCalorieData(DataReadResult dataReadResult) {

        double result = 0;

        if (dataReadResult.getBuckets().size() > 0) {
            Log.i(TAG, "(parse calorie)Number of returned buckets of DataSets is: "
                    + dataReadResult.getBuckets().size());
            for (Bucket bucket : dataReadResult.getBuckets()) {
                List<DataSet> dataSets = bucket.getDataSets();
                for (DataSet dataSet : dataSets) {
                    //dumpDataSet(dataSet);

                    for(DataPoint dp: dataSet.getDataPoints()) {
                        for(Field field: dp.getDataType().getFields()) {
                            result  += Double.valueOf(dp.getValue(field).toString());
                            String aaa = dp.getValue(field).toString();
                            Log.e("BBBB", aaa + Double.valueOf(aaa));
                        }
                    }

                }
            }
        } else if (dataReadResult.getDataSets().size() > 0) {
            Log.e(TAG, "THIS IS the parseCalorieData's second loop");
            for (DataSet dataSet : dataReadResult.getDataSets()) {
                dumpDataSet(dataSet);
            }
        }
        Log.e("AAAA", ""+ result);
        return result;
    }

    private static void dumpDataSet(DataSet dataSet) {
        Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
        DateFormat dateFormat = getTimeInstance();

        for (DataPoint dp : dataSet.getDataPoints()) {
            Log.i(TAG, "Data point:");
            Log.i(TAG, "\tType: " + dp.getDataType().getName());
            Log.i(TAG, "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
            Log.i(TAG, "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));
            for(Field field : dp.getDataType().getFields()) {
                Log.i(TAG, "\tField: " + field.getName() +
                        " Value: " + dp.getValue(field));
            }
        }
    }
    /// [Chang Liu] ends


}
