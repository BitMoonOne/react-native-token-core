package com.wallet.core.wallet;

import com.wallet.core.wallet.address.EthereumAddressCreator;
import com.wallet.core.wallet.model.ChainType;
import com.wallet.core.wallet.model.EthAccount;
import com.wallet.core.wallet.model.Wallet;
import com.wallet.core.wallet.utils.JsonMapper;
import com.wallet.core.wallet.utils.MnemonicUtil;

import java.util.*;

/**
 * Created by wlg on 2019/1/18.
 */
public class WalletManager {

    /**
     * 初始化钱包
     * @param mnemonic
     */
    public static void init(String mnemonic){
        //TODO 1.加载wallet.info文件2判断wallet.info 是否有内容 有按照wallet.info初始化钱包 没有，初始化钱包，并在wallet.info中记录账户的索引
        //TODO 2.按照wallet.info 初始化钱包，根据当前索引，从0开始加载addr信息（eth）
        //TODO 3.从0开始加载addr，并保存到文件里面

        //1加载wallet.info
        //-    [eth:{index:1}]
        String wallet_info = "wallet_info";
        String walletInfo = wallet_info;

        if(walletInfo != null && !"".equals(walletInfo)){

            List<String> words = getWordsFromWalletInfo(walletInfo);

            //加载ETH
            Integer index_eth = getIndexFromWalletInfo(walletInfo, ChainType.ETHEREUM);
            //获取addr

            EthereumAddressCreator ethGreat = new EthereumAddressCreator();
            //["0x", "0x"]
            List<String> addrListEth = new ArrayList<>();
            for (int i = 0; i <= index_eth; i++) {
                String addr = ethGreat.fromMnemonic(words, i);
                addrListEth.add(addr);
            }

            //ower public key
            // user public key  其他钱包都一样







        }









    }

    private static List<String> getWordsFromWalletInfo(String walletInfo) {
        return new ArrayList<String>();
    }

    private static Integer getIndexFromWalletInfo(String info, String chainType) {
        return 0;
    }

    /**
     * 生成助记词字符串
     * @return
     */
    public static String creatMnemonic(){
        List<String> mns = MnemonicUtil.randomMnemonicCodes();
        //TODO 保存一份文件wallet.info(1.记录当前账户的索引)
        return JsonMapper.toJsonString(mns);
    }

    /**
     * 签署交易
     */
    public static void signTx(){
        //1.读取助记词 2.生成私钥 3.签名
    }

    /**
     * 创建新的账户
     */
    public static void newAccount(){
        //1.加载wallet.info 2.查看index 3.index+1 创建账户 4.记录到wallet.info
    }

}
