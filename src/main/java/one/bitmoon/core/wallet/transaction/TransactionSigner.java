package one.bitmoon.core.wallet.transaction;

import one.bitmoon.core.wallet.model.Wallet;

public interface TransactionSigner {
  TxSignResult signTransaction(String chainId, String password, Wallet wallet);
}
