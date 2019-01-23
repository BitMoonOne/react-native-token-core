package one.bitmoon.core.wallet.transaction.EOS;

import one.bitmoon.core.wallet.crypto.Hash;
import one.bitmoon.core.wallet.model.Wallet;
import one.bitmoon.core.wallet.transaction.TransactionSigner;
import one.bitmoon.core.wallet.transaction.TxMultiSignResult;
import one.bitmoon.core.wallet.transaction.TxSignResult;
import one.bitmoon.core.wallet.utils.ByteUtil;
import one.bitmoon.core.wallet.utils.NumericUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EOSTransaction implements TransactionSigner {

  private byte[] txBuf;
  private List<ToSignObj> txsToSign;

  public EOSTransaction(byte[] txBuf) {
    this.txBuf = txBuf;
  }

  public EOSTransaction(List<ToSignObj> txsToSign) {
    this.txsToSign = txsToSign;
  }


  @Deprecated
  @Override
  public TxSignResult signTransaction(String chainId, String password, Wallet wallet) {
    String transactionID = NumericUtil.bytesToHex(Hash.sha256(txBuf));
    txBuf = ByteUtil.concat(NumericUtil.hexToBytes(chainId), txBuf);

    byte[] zeroBuf = new byte[32];
    Arrays.fill(zeroBuf, (byte) 0);
    txBuf = ByteUtil.concat(txBuf, zeroBuf);

    String wif = wallet.exportPrivateKey(password);
    String signed = EOSSign.sign(Hash.sha256(txBuf), wif);

    return new TxSignResult(signed, transactionID);
  }

  public List<TxMultiSignResult> signTransactions(String chainId, String password, Wallet wallet) {
    List<TxMultiSignResult> results = new ArrayList<>(txsToSign.size());
    for (ToSignObj toSignObj : txsToSign) {

      byte[] txBuf = NumericUtil.hexToBytes(toSignObj.txHex);
      String transactionID = NumericUtil.bytesToHex(Hash.sha256(txBuf));

      byte[] txChainIDBuf = ByteUtil.concat(NumericUtil.hexToBytes(chainId), txBuf);

      byte[] zeroBuf = new byte[32];
      Arrays.fill(zeroBuf, (byte) 0);
      byte[] fullTxBuf = ByteUtil.concat(txChainIDBuf, zeroBuf);

      byte[] hashedTx = Hash.sha256(fullTxBuf);

      List<String> signatures = new ArrayList<>(toSignObj.publicKeys.size());
      for (String pubKey : toSignObj.publicKeys) {
        String signed;
        signed = EOSSign.sign(hashedTx, wallet.decryptPrvKeyFor(pubKey, password));

        signatures.add(signed);
      }

      TxMultiSignResult signedResult = new TxMultiSignResult(transactionID, signatures);
      results.add(signedResult);
    }
    return results;
  }

  public static class ToSignObj {
    private String txHex;
    private List<String> publicKeys;

    public String getTxHex() {
      return txHex;
    }

    public void setTxHex(String txHex) {
      this.txHex = txHex;
    }

    public List<String> getPublicKeys() {
      return publicKeys;
    }

    public void setPublicKeys(List<String> publicKeys) {
      this.publicKeys = publicKeys;
    }
  }
}
