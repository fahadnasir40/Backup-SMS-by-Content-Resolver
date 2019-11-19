package com.smd.roomdatabase.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sms_table")
public class Sms {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "msg_id_col")
    private String message_id;

    @ColumnInfo(name = "sender_name_col")
    private String sender_name;

    @ColumnInfo(name = "message_col")
    private String message;

    @ColumnInfo(name = "readState_col")
    private String readState;

    @ColumnInfo(name = "time_col")
    private String time;

    @ColumnInfo(name = "folder_name_col")
    private String folder_name;

    public Sms(String message_id, String sender_name, String message, String readState, String time, String folder_name) {
        this.message_id = message_id;
        this.sender_name = sender_name;
        this.message = message;
        this.readState = readState;
        this.time = time;
        this.folder_name = folder_name;
    }

    public int getId() {
        return id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public String getMessage() {
        return message;
    }

    public String getReadState() {
        return readState;
    }

    public String getTime() {
        return time;
    }

    public String getFolder_name() {
        return folder_name;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReadState(String readState) {
        this.readState = readState;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setFolder_name(String folder_name) {
        this.folder_name = folder_name;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }
}
