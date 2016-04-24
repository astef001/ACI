package com.example.xmlpullparsing;

/**
 * Created by Bungau Calin on 4/24/2016.
 */
public class Transaction {
    private String MsgDateTime;
    private int PAN;
    private String UserId;


    public String getMsgDateTime() {
        return MsgDateTime;
    }


    public void setMsgDateTime(String MsgDateTime) {
        this.MsgDateTime = MsgDateTime;
    }


    public String getUserId() {
        return UserId;
    }


    public void setUserId(String UserId) {
        this.UserId = UserId;
    }


    public int getPAN() {
        return PAN;
    }


    public void setPAN(int PAN) {
        this.PAN = PAN;
    }


    @Override
    public String toString() {
        return " Transactions date: "+MsgDateTime + "\n User: " + UserId + "\n Salary= " + PAN;
    }
}

