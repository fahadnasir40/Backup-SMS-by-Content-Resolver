package com.smd.smsbackup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.smd.roomdatabase.model.Sms;
import com.smd.roomdatabase.model.SmsViewModel;
import com.smd.roomdatabase.util.SmsRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.Manifest.permission_group.SMS;

public class MainActivity extends AppCompatActivity {

    Button addBackupBtn;
    Button showBackupMsgBtn;
    Button deleteBackupBtn;
    ProgressBar progressBar;
    private int PERMISSION_READ_SMS_CODE = 1;
    public static SmsViewModel smsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED)
        {
            ShowData();
        }
        else
        {
            requestReadSMSPermission();
        }

    }


    public void ShowData()
    {
        smsViewModel = new SmsViewModel(getApplicationContext());

        addBackupBtn = findViewById(R.id.backupBtn);
        showBackupMsgBtn = findViewById(R.id.showMessages);
        deleteBackupBtn = findViewById(R.id.deleteMessages);
        progressBar = findViewById(R.id.progressbar);

        addBackupBtn.setOnClickListener(v -> {

            backupSms();
            Toast.makeText(getApplicationContext(),"Backing up.",Toast.LENGTH_SHORT).show();
        });

        showBackupMsgBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,BackupMessages.class);
            startActivity(intent);
        });

        deleteBackupBtn.setOnClickListener(v -> {
            if(smsViewModel !=null)
                smsViewModel.deleteAllSms();


            Toast.makeText(getApplicationContext(), "SMS Backup deleted",
                    Toast.LENGTH_LONG).show();
        });
    }

    //Requesting Permission for read contacts
    private void requestReadSMSPermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_SMS)){

            new AlertDialog.Builder(this).setTitle("Permission required").setMessage("Read sms permission is required to show messages.\n")
                    .setPositiveButton("ok", (dialogInterface, i) -> ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_SMS},PERMISSION_READ_SMS_CODE))
                    .setNegativeButton("cancel", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        Toast.makeText(getApplicationContext(),"Permission Not Granted. Exiting...",Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .create()
                    .show();


        }
        else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},PERMISSION_READ_SMS_CODE);
        }
    }


    //Check permission grant result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_READ_SMS_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
                ShowData();
            }
            else
            {

                Toast.makeText(this,"Permission Not Granted",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void backupSms(){


        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);

        progressBar.setVisibility(View.VISIBLE);
        while (c.moveToNext()) {

            final String[] id = {(c.getString(c.getColumnIndexOrThrow("_id")))};
            String address = (c.getString(c
                    .getColumnIndexOrThrow("address")));
            String msg = (c.getString(c.getColumnIndexOrThrow("body")));
            String state = (c.getString(c.getColumnIndex("read")));

            if(state.equals("1"))
                state = "Read";
            else state = "Unread";

            String time = (c.getString(c.getColumnIndexOrThrow("date")));
            final boolean[] smsPresent = {false};
            if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    String finalState = state;
                    smsViewModel.getAllSms().observe(this,new Observer<List<Sms>> (){

                        @Override
                        public void onChanged(List<Sms> sms) {
                            progressBar.setVisibility(View.VISIBLE);
                            for(int i = 0;i<sms.size();i++)
                            {
                                if(id[0].equals(String.valueOf(sms.get(i).getMessage_id())))
                                {
                                    smsPresent[0] = true;
                                    break;
                                }
                            }
                            if(smsPresent[0] == false) {
                                smsViewModel.insert(new Sms(id[0], address, msg, finalState, time, "inbox"));
                                smsPresent[0] = true;
                            }
                            progressBar.setVisibility(View.GONE);


                        }
                      });

                }
            } else {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    String finalState = state;

                    smsViewModel.getAllSms().observe(this,new Observer<List<Sms>> (){

                        @Override
                        public void onChanged(List<Sms> sms) {
                            progressBar.setVisibility(View.VISIBLE);
                            for(int i = 0;i<sms.size();i++)
                            {
                                if(id[0].equals(String.valueOf(sms.get(i).getMessage_id())))
                                {
                                    smsPresent[0] = true;
                                    break;
                                }
                            }
                            if(smsPresent[0] == false) {
                                smsViewModel.insert(new Sms(id[0], address, msg, finalState, time, "sent"));
                                smsPresent[0] = true;
                            }
                            progressBar.setVisibility(View.GONE);

                        }
                    });
                }
            }
        }


        c.close();


    }


}
