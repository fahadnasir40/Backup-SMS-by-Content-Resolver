package com.smd.roomdatabase.data;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import com.smd.roomdatabase.model.Sms;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    //------------------------------------------------------------
    //Sms operations
    @Insert
    void insert(Sms sms);

    @Query("Delete from sms_table")
    void deleteAllSms();

    @Query("Delete from sms_table where id=:id")
    void deleteCol(int id);

    @Query("Select* from sms_table")
    LiveData<List<Sms>> getAllSms();
}
