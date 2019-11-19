package com.smd.roomdatabase.util;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.smd.roomdatabase.data.Dao;
import com.smd.roomdatabase.data.RoomDatabase;
import com.smd.roomdatabase.model.Sms;

import java.util.List;

public class SmsRepository {
    Dao dao;
    private LiveData<List<Sms>> allSms;

    public SmsRepository(Context context) {
        RoomDatabase db = RoomDatabase.getDatabase(context);
        dao = db.dao();
        allSms = dao.getAllSms();
    }

    public LiveData<List<Sms>> getAllSms() {
        return allSms;
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
}
