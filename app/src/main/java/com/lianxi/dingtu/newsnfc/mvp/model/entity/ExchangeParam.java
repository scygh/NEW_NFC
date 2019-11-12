package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.io.Serializable;

public class ExchangeParam implements Serializable {

    /**
     * ID : 00000000-0000-0000-0000-000000000000
     * UserID : 00000000-0000-0000-0000-000000000000
     * Number : 0
     * DeviceID : 0
     * DetailType : 1
     * Finance : 0
     * PayCount : 0
     * BeforeBalance : 0.01
     * Amount : 0.01
     * Money : 0.01
     * Donate : 0.01
     * Cost : 0.01
     * AfterBalance : 0.01
     * Operator : string
     * TradeDateTime : 2018-12-05T00:45:06.684Z
     * CreateTime : 2018-12-05T00:45:06.684Z
     * Description : string
     * Channel : 0
     */

    private String ID;
    private String UserID;
    private int Number;
    private int DeviceID;
    private int DetailType;
    private int Finance;
    private int PayCount;
    private double BeforeBalance;
    private double Amount;
    private double Money;
    private double Donate;
    private double Cost;
    private double AfterBalance;
    private String Operator;
    private String TradeDateTime;
    private String CreateTime;
    private String Description;
    private int Channel;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

    public int getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(int DeviceID) {
        this.DeviceID = DeviceID;
    }

    public int getDetailType() {
        return DetailType;
    }

    public void setDetailType(int DetailType) {
        this.DetailType = DetailType;
    }

    public int getFinance() {
        return Finance;
    }

    public void setFinance(int Finance) {
        this.Finance = Finance;
    }

    public int getPayCount() {
        return PayCount;
    }

    public void setPayCount(int PayCount) {
        this.PayCount = PayCount;
    }

    public double getBeforeBalance() {
        return BeforeBalance;
    }

    public void setBeforeBalance(double BeforeBalance) {
        this.BeforeBalance = BeforeBalance;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public double getMoney() {
        return Money;
    }

    public void setMoney(double Money) {
        this.Money = Money;
    }

    public double getDonate() {
        return Donate;
    }

    public void setDonate(double Donate) {
        this.Donate = Donate;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double Cost) {
        this.Cost = Cost;
    }

    public double getAfterBalance() {
        return AfterBalance;
    }

    public void setAfterBalance(double AfterBalance) {
        this.AfterBalance = AfterBalance;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String Operator) {
        this.Operator = Operator;
    }

    public String getTradeDateTime() {
        return TradeDateTime;
    }

    public void setTradeDateTime(String TradeDateTime) {
        this.TradeDateTime = TradeDateTime;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getChannel() {
        return Channel;
    }

    public void setChannel(int Channel) {
        this.Channel = Channel;
    }
}
