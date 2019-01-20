package com.wallet.core.wallet.utils;

import com.google.common.collect.ImmutableList;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;

import javax.annotation.Nonnull;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BIP44Util {

//  public final static String ETHEREUM_PATH = "m/44'/60'/0'/0/0";
public final static String ETHEREUM_PATH = "m/44'/60'/0'/0/";
  public final static String EOS_PATH = "m/44'/194'";
  public final static String EOS_SLIP48 = "m/48'/4'/0'/0'/0',m/48'/4'/1'/0'/0'";
  public final static String EOS_LEDGER = "m/44'/194'/0'/0/0";

  public static ImmutableList<ChildNumber> generatePath(String path) {
    List<ChildNumber> list = new ArrayList<>();
    for (String p : path.split("/")) {
      if ("m".equalsIgnoreCase(p) || "".equals(p.trim())) {
        continue;
      } else if (p.charAt(p.length() - 1) == '\'') {
        list.add(new ChildNumber(Integer.parseInt(p.substring(0, p.length() - 1)), true));
      } else {
        list.add(new ChildNumber(Integer.parseInt(p), false));
      }
    }

    ImmutableList.Builder<ChildNumber> builder = ImmutableList.builder();
    return builder.addAll(list).build();
  }

  /**
   * @param words
   * @return
   */
  public static byte[] getSeed(List<String> words) {
    return MnemonicCode.toSeed(words, "");
  }

  /**
   * @param words
   * @param path  m / purpose' / coin_type' / account' / change / address_index
   * @return
   */
  public static BigInteger getPathPrivateKey(List<String> words, String path) {
    return getDeterministicKey(words, null, path).getPrivKey();
  }

  /**
   * @param words
   * @param seed
   * @param path  m / purpose' / coin_type' / account' / change / address_index
   * @return
   */
  public static BigInteger getPathPrivateKey(List<String> words, byte[] seed, String path) {
    return getDeterministicKey(words, seed, path).getPrivKey();
  }

  /**
   * @param words
   * @param path  m / purpose' / coin_type' / account' / change / address_index
   * @return
   */
  public static byte[] getPathPrivateKeyBytes(List<String> words, String path) {
    return getDeterministicKey(words, null, path).getPrivKeyBytes();
  }

  /**
   * @param words
   * @param seed
   * @param path  m / purpose' / coin_type' / account' / change / address_index
   * @return
   */
  public static byte[] getPathPrivateKeyBytes(List<String> words, byte[] seed, String path) {
    return getDeterministicKey(words, seed, path).getPrivKeyBytes();
  }

  /**
   * @param words
   * @param coinType see https://github.com/satoshilabs/slips/blob/master/slip-0044.md
   * @return
   */
  public static BigInteger getDefaultPathPrivateKey(List<String> words, int coinType) {
    String path = "m/44'/" +
            coinType +
            "'/0'/0/0";
    return getDeterministicKey(words, null, path).getPrivKey();
  }

  /**
   * @param words
   * @param seed
   * @param coinType see https://github.com/satoshilabs/slips/blob/master/slip-0044.md
   * @return
   */
  public static BigInteger getDefaultPathPrivateKey(List<String> words, byte[] seed, int coinType) {
    String path = "m/44'/" +
            coinType +
            "'/0'/0/0";
    return getDeterministicKey(words, seed, path).getPrivKey();
  }

  /**
   * @param words
   * @param coinType see https://github.com/satoshilabs/slips/blob/master/slip-0044.md
   * @return
   */
  public static byte[] getDefaultPathPrivateKeyBytes(List<String> words, int coinType) {
    String path = "m/44'/" +
            coinType +
            "'/0'/0/0";
    return getDeterministicKey(words, null, path).getPrivKeyBytes();
  }

  /**
   * @param words
   * @param seed
   * @param coinType see https://github.com/satoshilabs/slips/blob/master/slip-0044.md
   * @return
   */
  public static byte[] getDefaultPathPrivateKeyBytes(List<String> words, byte[] seed, int coinType) {
    String path = "m/44'/" +
            coinType +
            "'/0'/0/0";
    return getDeterministicKey(words, seed, path).getPrivKeyBytes();
  }

  private static DeterministicKey getDeterministicKey(List<String> words, byte[] seed, String path) {
    DeterministicSeed deterministicSeed = new DeterministicSeed(words, seed, "", 0);
    DeterministicKeyChain deterministicKeyChain = DeterministicKeyChain.builder().seed(deterministicSeed).build();
    return deterministicKeyChain.getKeyByPath(parsePath(path), true);
  }

  private static List<ChildNumber> parsePath(@Nonnull String path) {
    String[] parsedNodes = path.replace("m", "").split("/");
    List<ChildNumber> nodes = new ArrayList<>();

    for (String n : parsedNodes) {
      n = n.replaceAll(" ", "");
      if (n.length() == 0) continue;
      boolean isHard = n.endsWith("'");
      if (isHard) n = n.substring(0, n.length() - 1);
      int nodeNumber = Integer.parseInt(n);
      nodes.add(new ChildNumber(nodeNumber, isHard));
    }

    return nodes;
  }



}
