package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.io.Serializable;

public class DepositReportTo implements Serializable {
    /**
     * SerialNo : 000001
     * Name : 鲁炳
     * DepartmentID : 00000000-0000-0000-0000-000000000001
     * EmpID :
     * IDCard : 4245646548648
     * Phone : 17557289603
     * Number : 100
     * CardTypeID : 1
     * CardTypeName : 正常卡
     * DetailType : 1
     * DeviceID : 1
     * Finance : 0
     * BeforeBalance : 36.01
     * Amount : 9.01
     * Money : 9.01
     * Donate : 0.01
     * Cost : 0.01
     * UserID : e1809350-d361-4725-98c6-46b9800796b0
     * AfterBalance : 45.01
     * TradeDateTime : 2018-11-08 11:42:28
     * Operator : 机器
     * Description : 机器充值【线上】充值现金9元,充值赠送0元,实际现金交易9元,余额45.00元
     * Channel :
     */
    /**
     * SerialNo	string
     * Name	string
     * Finance	{
     * oneOf ->
     * EnumFinanceTypeinteger
     * Enum:
     * [ 0, 1, 2, 3, 4 ]
     * }
     * DetailType	EnumDepositDetailTypeinteger
     * Enum:
     * [ 1, 2, 3, 4, 5, 6 ]
     * BeforeBalance	number($decimal)
     * Amount	number($decimal)
     * Money	number($decimal)
     * Donate	number($decimal)
     * AfterBalance	number($decimal)
     * Cost	number($decimal)
     * Operator	string
     * DeviceID	integer($int32)
     * TradeDateTime	string($date-time)
     * Description	string
     */
    private String SerialNo;
    private String Name;
    private int Finance;
    private int DetailType;
    private double BeforeBalance;
    private double Amount;
    private double Money;
    private double Donate;
    private double AfterBalance;
    private double Cost;
    private String Operator;
    private int DeviceID;
    private String TradeDateTime;
    private String Description;


    private String IDCard;
    private String Channel;

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getFinance() {
        return Finance;
    }

    public void setFinance(int finance) {
        Finance = finance;
    }

    public int getDetailType() {
        return DetailType;
    }

    public void setDetailType(int detailType) {
        DetailType = detailType;
    }

    public double getBeforeBalance() {
        return BeforeBalance;
    }

    public void setBeforeBalance(double beforeBalance) {
        BeforeBalance = beforeBalance;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public double getMoney() {
        return Money;
    }

    public void setMoney(double money) {
        Money = money;
    }

    public double getDonate() {
        return Donate;
    }

    public void setDonate(double donate) {
        Donate = donate;
    }

    public double getAfterBalance() {
        return AfterBalance;
    }

    public void setAfterBalance(double afterBalance) {
        AfterBalance = afterBalance;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    public int getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(int deviceID) {
        DeviceID = deviceID;
    }

    public String getTradeDateTime() {
        return TradeDateTime;
    }

    public void setTradeDateTime(String tradeDateTime) {
        TradeDateTime = tradeDateTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getChannel() {
        return Channel;
    }

    public void setChannel(String channel) {
        Channel = channel;
    }
}
