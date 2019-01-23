package one.bitmoon.core.wallet.transaction;

/**
 * Created by wlg on 2019年1月23日20:54:56
 */
public class TxSignResult {
  private String signedTx;
  private String txHash;
  private String wtxID;

  public String getWtxID() {
    return wtxID;
  }

  public void setWtxID(String wtxID) {
    this.wtxID = wtxID;
  }

  public String getSignedTx() {
    return signedTx;
  }

  public void setSignedTx(String signedTx) {
    this.signedTx = signedTx;
  }

  public String getTxHash() {
    return txHash;
  }

  public void setTxHash(String txHash) {
    this.txHash = txHash;
  }

  public TxSignResult(String signedTx, String txHash) {
    this.signedTx = signedTx;
    this.txHash = txHash;
  }

  public TxSignResult(String signedTx, String txHash, String wtxID) {
    this.signedTx = signedTx;
    this.txHash = txHash;
    this.wtxID = wtxID;
  }
}
