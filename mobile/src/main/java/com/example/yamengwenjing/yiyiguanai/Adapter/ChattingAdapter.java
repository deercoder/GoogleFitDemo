package com.example.yamengwenjing.yiyiguanai.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yamengwenjing.yiyiguanai.Entity.ChattingMessage;
import com.example.yamengwenjing.yiyiguanai.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by yamengwenjing on 2016/3/18.
 */
public class ChattingAdapter extends BaseAdapter {

    private LayoutInflater thisInFlater;
    private List<ChattingMessage> chattingData;


    public ChattingAdapter(Context context,List<ChattingMessage> dataList){
        this.thisInFlater = LayoutInflater.from(context);
        this.chattingData = dataList;

    }


    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        ChattingMessage thisMessage = chattingData.get(position);
        if(thisMessage.getType()== ChattingMessage.Type.INCOMING){
            return 0;
        }
        return 1;
    }


    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 2;
    }

    @Override
    public int getCount() {
        return chattingData.size();
    }

    @Override
    public Object getItem(int position) {
        return chattingData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChattingMessage chatMessage  = chattingData.get(position);
        ViewHolder viewHolder;
        if(convertView == null){
            if(getItemViewType(position)==0){
                convertView = thisInFlater.inflate(R.layout.fragment_qa_incmoing_message, parent,false);
                viewHolder = new ViewHolder();
                viewHolder.messageDate = (TextView)convertView.findViewById(R.id.fragment_qa_incoming_messgage_date);
                viewHolder.messageContent =(TextView)convertView.findViewById(R.id.fragment_qa_incoming_messgage_content);
            }else{
                convertView = thisInFlater.inflate(R.layout.fragment_qa_outgoing_message, parent,false);
                viewHolder = new ViewHolder();
                viewHolder.messageContent = (TextView)convertView.findViewById(R.id.fragment_qa_outgoingmessage_content);
                viewHolder.messageDate = (TextView)convertView.findViewById(R.id.fragment_qa_outgoingmessage_date);
            }
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        SimpleDateFormat messageTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.messageDate.setText(messageTime.format(chatMessage.getDate()));
        viewHolder.messageContent.setText(chatMessage.getMsg());
        return convertView;
    }


    private class ViewHolder{
        TextView messageDate;
        TextView messageContent;
    }
}
