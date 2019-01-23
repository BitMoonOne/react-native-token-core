package one.bitmoon.core.wallet.model;

import java.util.*;
import java.util.Map;

/**
 * Created by wlg on 2019/1/18.
 */
public class Wallet {
    private Map<ChainType, List<Account>> accountMap;

    public Map<ChainType, List<Account>> getAccountMap() {
        return accountMap;
    }

    public void setAccountMap(Map<ChainType, List<Account>> accountMap) {
        this.accountMap = accountMap;
    }

    public String exportPrivateKey(String password) {
        return null;
    }

    public byte[] decryptPrvKeyFor(String pubKey, String password) {
        return null;
    }

    public byte[] decryptMainKey(String password) {
        return null;
    }
}
