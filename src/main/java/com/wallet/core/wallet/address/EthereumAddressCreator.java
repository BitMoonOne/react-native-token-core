package com.wallet.core.wallet.address;

import com.wallet.core.wallet.crypto.Hash;
import com.wallet.core.wallet.utils.BIP44Util;
import com.wallet.core.wallet.utils.NumericUtil;
import org.bitcoinj.core.ECKey;

import java.math.BigInteger;
import java.util.*;
import java.util.Arrays;

public class EthereumAddressCreator implements AddressCreator {

  private static final int PUBLIC_KEY_SIZE = 64;
  private static final int PUBLIC_KEY_LENGTH_IN_HEX = PUBLIC_KEY_SIZE << 1;
  private static final int ADDRESS_LENGTH = 20;
  private static final int ADDRESS_LENGTH_IN_HEX = ADDRESS_LENGTH << 1;
  private static final String ETH_ADDRESS_PIX = "0x";


  /**
   * 从私钥生成地址
   * @param prvKeyHex
   * @return
   */
  @Override
  public String fromPrivateKey(String prvKeyHex) {
    ECKey key = ECKey.fromPrivate(NumericUtil.hexToBytes(prvKeyHex), false);
    return ETH_ADDRESS_PIX + fromECKey(key);
  }

  /**
   *
   * 根据助记词和地址索引生成地址
   * @param words
   * @param index
   * @return
   */
  public String fromMnemonic(List<String> words, Integer index){
    BigInteger privateKey = BIP44Util.getPathPrivateKey(words, BIP44Util.ETHEREUM_PATH + index.toString());
    return fromPrivateKey(privateKey.toString(16));
  }

  private String fromECKey(ECKey key) {
    byte[] pubKeyBytes = key.getPubKey();
    return publicKeyToAddress(Arrays.copyOfRange(pubKeyBytes, 1, pubKeyBytes.length));
  }

  private String publicKeyToAddress(byte[] pubKeyBytes) {
    byte[] hashedBytes = Hash.keccak256(pubKeyBytes);
    byte[] addrBytes = Arrays.copyOfRange(hashedBytes, hashedBytes.length - 20, hashedBytes.length);
    return NumericUtil.bytesToHex(addrBytes);
  }

  public String fromPublicKey(BigInteger publicKey) {
    byte[] pubKeyBytes = NumericUtil.bigIntegerToBytesWithZeroPadded(publicKey, PUBLIC_KEY_SIZE);
    return publicKeyToAddress(pubKeyBytes);
  }
}
