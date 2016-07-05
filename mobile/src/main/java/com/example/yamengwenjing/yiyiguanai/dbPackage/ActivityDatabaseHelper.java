package com.example.yamengwenjing.yiyiguanai.dbPackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by yamengwenjing on 2016/2/23.
 */
public class ActivityDatabaseHelper extends OrmLiteSqliteOpenHelper{

    private static final String TABLE_NAME = "ActivityData";
    private Dao<ActivityInfoDbEntity,Integer> ActivityInfoDbEntityDao;

    public ActivityDatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }




    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try
        {
            TableUtils.createTable(connectionSource, ActivityInfoDbEntity.class);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try
        {
            TableUtils.dropTable(connectionSource, SensorDbEntity.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    private static ActivityDatabaseHelper instance;
    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized ActivityDatabaseHelper getHelper(Context context)
    {
        if (instance == null)
        {
            synchronized (ActivityDatabaseHelper.class)
            {
                if (instance == null)
                    instance = new ActivityDatabaseHelper(context);
            }
        }

        return instance;
    }

    public Dao<ActivityInfoDbEntity, Integer> getSensorDbEntityDao() throws SQLException{
        if(ActivityInfoDbEntityDao == null){
            ActivityInfoDbEntityDao = getDao(ActivityInfoDbEntity.class);
        }
        return ActivityInfoDbEntityDao;
    }


    @Override
    public void close()
    {
        super.close();
        ActivityInfoDbEntityDao = null;
    }

}
