package com.smd.roomdatabase.util;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.smd.roomdatabase.data.Dao;
import com.smd.roomdatabase.data.RoomDatabase;
import com.smd.roomdatabase.model.Sms;

import java.util.List;

public class SmsRepository {

    private Dao dao;


    public SmsRepository(Context context) {
        RoomDatabase db = RoomDatabase.getDatabase(context);
        dao = db.dao();

    }

    public LiveData<List<Sms>> getAllSms() {

        return dao.getAllSms();
    }

    public void insert(Sms sms) {
        new insertAsyncTask(dao).execute(sms);

    }
    private class insertAsyncTask extends AsyncTask<Sms, Void, Void> {
        private Dao asyncTaskDao;

        public insertAsyncTask(Dao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Sms... sms) {
            asyncTaskDao.insert(sms[0]);
            return null;
        }
    }



    public void deleteAll() {
        new deleteAsyncTask().execute();

    }

    private class deleteAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllSms();
            return null;
        }
    }


}
