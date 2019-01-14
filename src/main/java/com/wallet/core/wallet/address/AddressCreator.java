package com.wallet.core.wallet.address;

/**
 * Created by wlg on 2019/1/14.
 */
public interface AddressCreator {
    /**
     * 从私钥生成地址
     * @param prvKeyHex
     * @return
     */
    String fromPrivateKey(String prvKeyHex);
}
