package one.bitmoon.core.wallet.transaction.Ethereum;


import one.bitmoon.core.wallet.crypto.Hash;
import one.bitmoon.core.wallet.model.Wallet;
import one.bitmoon.core.wallet.rlp.RlpEncoder;
import one.bitmoon.core.wallet.rlp.RlpList;
import one.bitmoon.core.wallet.rlp.RlpString;
import one.bitmoon.core.wallet.rlp.RlpType;
import one.bitmoon.core.wallet.transaction.SignatureData;
import one.bitmoon.core.wallet.transaction.TransactionSigner;
import one.bitmoon.core.wallet.transaction.TxSignResult;
import one.bitmoon.core.wallet.utils.ByteUtil;
import one.bitmoon.core.wallet.utils.NumericUtil;

import java.util.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class EthereumTransaction implements TransactionSigner {

  private BigInteger nonce;
  private BigInteger gasPrice;
  private BigInteger gasLimit;
  private String to;
  private BigInteger value;
  private String data;

  public EthereumTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
                             BigInteger value, String data) {
    this.nonce = nonce;
    this.gasPrice = gasPrice;
    this.gasLimit = gasLimit;
    this.to = to;
    this.value = value;

    if (data != null) {
      this.data = NumericUtil.cleanHexPrefix(data);
    }
  }

  public BigInteger getNonce() {
    return nonce;
  }

  public BigInteger getGasPrice() {
    return gasPrice;
  }

  public BigInteger getGasLimit() {
    return gasLimit;
  }

  public String getTo() {
    return to;
  }

  public BigInteger getValue() {
    return value;
  }

  public String getData() {
    return data;
  }

  @Override
  public TxSignResult signTransaction(String chainID, String password, Wallet wallet) {

    String signedTx = signTransaction(Integer.parseInt(chainID), wallet.decryptMainKey(password));
    String txHash = this.calcTxHash(signedTx);
    return new TxSignResult(signedTx, txHash);
  }

  String signTransaction(int chainId, byte[] privateKey) {
    SignatureData signatureData = new SignatureData(chainId, new byte[]{}, new byte[]{});
    byte[] encodedTransaction = encodeToRLP(signatureData);
    signatureData = EthereumSign.signMessage(encodedTransaction, privateKey);

    SignatureData eip155SignatureData = createEip155SignatureData(signatureData, chainId);
    byte[] rawSignedTx = encodeToRLP(eip155SignatureData);
    return NumericUtil.bytesToHex(rawSignedTx);
  }

  String calcTxHash(String signedTx) {
    return NumericUtil.prependHexPrefix(Hash.keccak256(signedTx));
  }

  private static SignatureData createEip155SignatureData(SignatureData signatureData, int chainId) {
    int v = signatureData.getV() + (chainId * 2) + 8;

    return new SignatureData(v, signatureData.getR(), signatureData.getS());
  }

  byte[] encodeToRLP(SignatureData signatureData) {
    List<RlpType> values = asRlpValues(signatureData);
    RlpList rlpList = new RlpList(values);
    return RlpEncoder.encode(rlpList);
  }

  List<RlpType> asRlpValues(SignatureData signatureData) {
    List<RlpType> result = new ArrayList<>();

    result.add(RlpString.create(getNonce()));
    result.add(RlpString.create(getGasPrice()));
    result.add(RlpString.create(getGasLimit()));

    // an empty to address (contract creation) should not be encoded as a numeric 0 value
    String to = getTo();
    if (to != null && to.length() > 0) {
      // addresses that start with zeros should be encoded with the zeros included, not
      // as numeric values
      result.add(RlpString.create(NumericUtil.hexToBytes(to)));
    } else {
      result.add(RlpString.create(""));
    }

    result.add(RlpString.create(getValue()));

    // value field will already be hex encoded, so we need to convert into binary first
    byte[] data = NumericUtil.hexToBytes(getData());
    result.add(RlpString.create(data));

    if (signatureData != null && signatureData.getV() > 0) {
      result.add(RlpString.create(signatureData.getV()));
      result.add(RlpString.create(ByteUtil.trimLeadingZeroes(signatureData.getR())));
      result.add(RlpString.create(ByteUtil.trimLeadingZeroes(signatureData.getS())));
    }

    return result;
  }
}
