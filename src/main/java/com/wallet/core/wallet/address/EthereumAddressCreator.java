package com.wallet.core.wallet.address;

import com.wallet.core.wallet.crypto.Hash;
import com.wallet.core.wallet.utils.NumericUtil;
import org.bitcoinj.core.ECKey;
import java.util.Arrays;

public class EthereumAddressCreator implements AddressCreator {

  private static final int PUBLIC_KEY_SIZE = 64;
  private static final int PUBLIC_KEY_LENGTH_IN_HEX = PUBLIC_KEY_SIZE << 1;
  private static final int ADDRESS_LENGTH = 20;
  private static final int ADDRESS_LENGTH_IN_HEX = ADDRESS_LENGTH << 1;

  @Override
  public String fromPrivateKey(String prvKeyHex) {
    ECKey key = ECKey.fromPrivate(NumericUtil.hexToBytes(prvKeyHex), false);
    return fromECKey(key);
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
}
