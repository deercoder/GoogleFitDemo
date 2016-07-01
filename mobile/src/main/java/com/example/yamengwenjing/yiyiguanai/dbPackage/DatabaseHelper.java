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
public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

    private static final String TABLE_NAME = "SensorData";
    private Dao<SensorDbEntity,Integer> sensorDbEntityDao;

    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }




    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try
        {
            TableUtils.createTable(connectionSource, SensorDbEntity.class);
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


    private static DatabaseHelper instance;
    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getHelper(Context context)
    {
        if (instance == null)
        {
            synchronized (DatabaseHelper.class)
            {
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }

        return instance;
    }

    public Dao<SensorDbEntity,Integer> getSensorDbEntityDao() throws SQLException{
        if(sensorDbEntityDao == null){
            sensorDbEntityDao = getDao(SensorDbEntity.class);
        }
        return sensorDbEntityDao;
    }


    @Override
    public void close()
    {
        super.close();
        sensorDbEntityDao = null;
    }

}
