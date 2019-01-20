package com.wallet.core.wallet.model;

import com.wallet.core.wallet.common.Messages;
import com.wallet.core.wallet.common.TokenException;

/**
 * 链类型model
 */
public class ChainType {
  public final static String ETHEREUM = "ETHEREUM";
  public final static String EOS = "EOS";

  public static void validate(String type) {
    if (!ETHEREUM.equals(type) && !EOS.equals(type)) {
      throw new TokenException(Messages.WALLET_INVALID_TYPE);
    }
  }
}