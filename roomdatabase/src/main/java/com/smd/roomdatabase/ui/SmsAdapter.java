package com.smd.roomdatabase.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smd.roomdatabase.R;
import com.smd.roomdatabase.model.Sms;

import java.util.List;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsViewHolder> {
    private final LayoutInflater layoutInflater;
    private List<Sms> smsList;

    public SmsAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycle_view_item,parent,false);
        return new SmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsViewHolder holder, int position) {
        if(smsList != null){
            Sms current = smsList.get(position);

            holder.id.setText("Message Id:       " + current.getMessage_id());
            holder.senderName.setText("Sender Name:    " + current.getSender_name());
            holder.msgDetail.setText("Message Description:              " + current.getMessage());
            holder.readState.setText("Read/Unread:      " + current.getReadState());
            holder.time.setText("Time:              " + current.getTime());
            holder.folderName.setText("Inbox/Sent:      " + current.getFolder_name());
        }
    }

    public void setSmsList(List<Sms> smsList){
        this.smsList = smsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(smsList != null)
            return smsList.size();
        return 0;
    }


    public class SmsViewHolder extends RecyclerView.ViewHolder{
        public TextView id;
        public TextView senderName;
        public TextView msgDetail;
        public TextView readState;
        public TextView time;
        public TextView folderName;

        public SmsViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.msg_id);
            senderName = itemView.findViewById(R.id.name);
            msgDetail = itemView.findViewById(R.id.msg);
            readState = itemView.findViewById(R.id.state);
            time = itemView.findViewById(R.id.time);
            folderName = itemView.findViewById(R.id.folder_name);
        }
    }
}
