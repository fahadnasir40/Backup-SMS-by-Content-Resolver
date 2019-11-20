package com.smd.smsbackup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.smd.roomdatabase.model.Sms;
import com.smd.roomdatabase.model.SmsViewModel;
import com.smd.roomdatabase.ui.SmsAdapter;

import java.util.List;

public class BackupMessages extends AppCompatActivity {

    public static SmsAdapter smsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_messages);


        RecyclerView recyclerView = findViewById(R.id.recycle_view_id);
        smsAdapter = new SmsAdapter(this);

        recyclerView.setAdapter(smsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MainActivity.smsViewModel.getAllSms().observe(this, new Observer<List<Sms>>() {
            @Override
            public void onChanged(List<Sms> smsList) {
                //update cached copy of sms
                smsAdapter.setSmsList(smsList);
            }
        });
    }
}
