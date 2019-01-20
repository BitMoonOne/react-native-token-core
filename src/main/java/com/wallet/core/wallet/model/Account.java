package com.wallet.core.wallet.model;

/**
 * Created by wlg on 2019/1/18.
 */
public class Account {
    private String chainType; //链类型
    private Integer index;  //地址索引

    public String getChainType() {
        return chainType;
    }

    public void setChainType(String chainType) {
        this.chainType = chainType;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
