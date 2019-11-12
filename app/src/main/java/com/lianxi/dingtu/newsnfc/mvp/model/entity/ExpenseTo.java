package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.util.List;

public class ExpenseTo {
    private int Count;
    private List<ExpenseReportTo> Rows;

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public List<ExpenseReportTo> getRows() {
        return Rows;
    }

    public void setRows(List<ExpenseReportTo> rows) {
        Rows = rows;
    }
}
