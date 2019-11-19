package com.smd.roomdatabase.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.smd.roomdatabase.util.SmsRepository;

import java.util.List;

public class SmsViewModel {
    SmsRepository smsRepository;
    LiveData<List<Sms>> allSms;

    public SmsViewModel(Context context) {
        smsRepository = new SmsRepository(context);
        allSms = smsRepository.getAllSms();
    }

    public LiveData<List<Sms>> getAllSms(){
        return allSms;
    }

    public void insert(Sms sms){
        smsRepository.insert(sms);
    }
}
