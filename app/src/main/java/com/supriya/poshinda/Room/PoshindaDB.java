package com.supriya.poshinda.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {ProductEntity.class,SoilTestFormEntity.class,CartEntity.class},version = 2,exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class PoshindaDB extends RoomDatabase {

    private static PoshindaDB poshindaDB = null;

    public abstract DataDAO dataDAO();

    public static synchronized PoshindaDB getInstance(Context context){
        if (poshindaDB == null){
            poshindaDB = Room.databaseBuilder(
                    context.getApplicationContext(),
                    PoshindaDB.class,
                    "Poshinda")//database name
                    .allowMainThreadQueries()//allow DAO queries
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return poshindaDB;
    }

}
