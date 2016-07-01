package com.example.yamengwenjing.yiyiguanai.Entity;

import java.util.Date;

/**
 * Created by yamengwenjing on 2016/3/18.
 */
public class ChattingMessage {

    private String owner;
    private String msg;
    private Type type;
    private Date date;


    public String getOwner() {
        return owner;
    }


    public void setOwner(String owner) {
        this.owner = owner;
    }


    public String getMsg() {
        return msg;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }


    public Type getType() {
        return type;
    }


    public void setType(Type type) {
        this.type = type;
    }


    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }


    public enum Type{
        INCOMING,OUTCOMING;
    }


}
