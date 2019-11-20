package com.smd.smsbackup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.smd.roomdatabase.model.Sms;
import com.smd.roomdatabase.model.SmsViewModel;
import com.smd.roomdatabase.util.SmsRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    SmsRepository smsRepository;
    Button addBackupBtn;
    Button showBackupMsgBtn;
    Button deleteBackupBtn;

    public static SmsViewModel smsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        smsViewModel = new SmsViewModel(getApplicationContext());

        addBackupBtn = findViewById(R.id.backupBtn);
        showBackupMsgBtn = findViewById(R.id.showMessages);
        deleteBackupBtn = findViewById(R.id.deleteMessages);

        smsRepository = new SmsRepository(getApplicationContext());

        addBackupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backupSms();
                Toast.makeText(getApplicationContext(), "SMS Backed",
                        Toast.LENGTH_LONG).show();
            }
        });

        showBackupMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BackupMessages.class);
                startActivity(intent);
            }
        });

        deleteBackupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smsViewModel !=null)
                   smsViewModel.deleteAllSms();


                Toast.makeText(getApplicationContext(), "SMS Backup deleted",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void backupSms(){
        LiveData<List<Sms>> backedSmsList;
        List<Sms> smsList;

        //backedSmsList = model.getAllSms().;
        //smsList = backedSmsList.getValue();



//        List<String> lstSms = new ArrayList<>();
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);

        while (c.moveToNext()) {

            String id = (c.getString(c.getColumnIndexOrThrow("_id")));
            String address = (c.getString(c
                    .getColumnIndexOrThrow("address")));
            String msg = (c.getString(c.getColumnIndexOrThrow("body")));
            String statee = (c.getString(c.getColumnIndex("read")));
            if(statee.equals("1"))
                statee = "Read";
            else statee = "Unread";

            String time = (c.getString(c.getColumnIndexOrThrow("date")));
            //String msgTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(time);

            if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                //objSms.setFolderName("inbox");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    String finalStatee = statee;
                    smsViewModel.getAllSms().observe(this, new Observer<List<Sms>>() {
                        @Override
                        public void onChanged(List<Sms> locationTrackings) {
                            //update cached copy of locations
                            if(smsViewModel.getAllSms() == null) {
                                smsRepository.insert(new Sms(id, address, msg, finalStatee, time, "inbox"));
                                Log.d("qwerty", "In first if null");
                            }
                            else if(!smsViewModel.getAllSms().getValue().stream().filter(o -> o.getMessage_id().equals(id)).findFirst().isPresent()){
                                smsRepository.insert(new Sms(id, address, msg, finalStatee, time, "inbox"));
                            }
                        }
                    });

                }
            } else {
                //objSms.setFolderName("sent");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    String finalStatee = statee;
                    smsViewModel.getAllSms().observe(this, new Observer<List<Sms>>() {
                        @Override
                        public void onChanged(List<Sms> locationTrackings) {
                            //update cached copy of locations
                            if(smsViewModel.getAllSms() == null) {
                                smsRepository.insert(new Sms(id, address, msg, finalStatee, time, "sent"));
                                Log.d("qwerty", "In first if null");
                            }
                            else if(!smsViewModel.getAllSms().getValue().stream().filter(o -> o.getMessage_id().equals(id)).findFirst().isPresent()){
                                smsRepository.insert(new Sms(id, address, msg, finalStatee, time, "sent"));
                            }
                        }
                    });
                }
            }

            //lstSms.add(id+"\n"+address+"\n"+msg+"\n"+state+"\n"+time);
        }

        c.close();
//
//        ListView l = ((ListView)findViewById(R.id.listview));
//
//        l.setAdapter(new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, lstSms));
    }


}
