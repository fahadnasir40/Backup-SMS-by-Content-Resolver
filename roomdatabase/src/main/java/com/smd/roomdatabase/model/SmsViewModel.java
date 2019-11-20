package com.smd.roomdatabase.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.smd.roomdatabase.util.SmsRepository;

import java.util.List;

public class SmsViewModel {
    private SmsRepository smsRepository;
    private LiveData<List<Sms>> allSms;

    public SmsViewModel(Context context) {
        smsRepository = new SmsRepository(context);

    }

    public void deleteAllSms(){
        smsRepository.deleteAll();
    }

    public LiveData<List<Sms>> getAllSms(){
        allSms = smsRepository.getAllSms();
        return allSms;

    }

    public void insert(Sms sms){
        smsRepository.insert(sms);
        allSms = smsRepository.getAllSms();
    }
}
