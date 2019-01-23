import one.bitmoon.core.wallet.address.EthereumAddressCreator;
import one.bitmoon.core.wallet.utils.BIP44Util;
import one.bitmoon.core.wallet.utils.MnemonicUtil;
import org.junit.Test;

import java.math.BigInteger;
import java.util.*;

/**
 * Created by wlg on 2019/1/14.
 */
public class CommonTest {

    @Test
    public void testMnemonic(){
        System.out.println("test 助记词生成");
        List<String> mns = MnemonicUtil.randomMnemonicCodes();
        System.out.println("助记词:" + mns);
    }
    @Test
    public void testEthAddrGen(){
        List<String> mns = MnemonicUtil.randomMnemonicCodes();
        System.out.println("助记词:" + mns);
        BigInteger bigInteger = BIP44Util.getPathPrivateKey(mns, BIP44Util.ETHEREUM_PATH);
        System.out.println(bigInteger.toString(16));

        EthereumAddressCreator eac = new EthereumAddressCreator();
        System.out.println(eac.fromPrivateKey(bigInteger.toString(16)));

        //"m/44'/60'/0'/0/0"
//        BigInteger bigInteger2 = BIP44Util.getPathPrivateKey(mns, "m/44'/60'/0'/0/1");
//        System.out.println(bigInteger2.toString(16));
    }


    @Test
    public void testEthAddr(){
        List<String> mns = MnemonicUtil.randomMnemonicCodes();

        EthereumAddressCreator eac = new EthereumAddressCreator();
        String addr0 = eac.fromMnemonic(mns, 0);
        System.out.println(addr0);

        String addr1 = eac.fromMnemonic(mns, 1);
        System.out.println(addr1);
    }



}
