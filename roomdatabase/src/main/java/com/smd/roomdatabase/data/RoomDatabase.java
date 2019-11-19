package com.smd.roomdatabase.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.smd.roomdatabase.model.Sms;

@Database(entities = {Sms.class}, version = 1)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {
    private static volatile RoomDatabase INSTANCE;
    public abstract Dao dao();

    public static RoomDatabase getDatabase(Context context){
        if(INSTANCE == null){
            synchronized (RoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabase.class, "dbSms").addCallback(roomdbCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomdbCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final Dao dao;
        public PopulateDbAsync(RoomDatabase db) {
            dao = db.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //dao.deleteAllSms();//removes all item for table

            //Sms sms = new Sms(1,"FAIZAN","Hello world",
                  //  "1","12:23","Inbox");
            //dao.insert(sms);

            return null;
        }
    }
}
