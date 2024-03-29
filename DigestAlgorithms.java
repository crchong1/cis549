
/**
 * Enumerates the digest algorithms for the supported signature algorithms.
 */
public enum DigestAlgorithms {
  /**
   * Digest algorithm name for SHA1.
   */
  SHA1("SHA-1"),
  /**
   * Digest algorithm name for SHA224.
   */
  SHA224("SHA-224"),
  /**
   * Digest algorithm name for SHA256.
   */
  SHA256("SHA-256"),
  /**
   * Digest algorithm name for SHA384.
   */
  SHA384("SHA-384"),
  /**
   * Digest algorithm name for SHA512.
   */
  SHA512("SHA-512");

  private final String digestName;

  /**
   * Constructor.
   */
  DigestAlgorithms(String digestName) {
    this.digestName = digestName;
  }

  /**
   * Returns digest name.
   *
   * @return Digest name.
   */
  public String getDigestName() {
    return digestName;
  }

//Code found at https://github.com/Trustpoint/tpm2m/tree/master/java/src/ca/trustpoint/m2m
//credit goes towards TrustPoint Innovation Technologies, Ltd.
  /**
   * Returns the enumeration value that corresponds to the given digestName.
   *
   * @param digestName A digest algorithm name.
   *
   * @return An instance of object in the enum associated with the given digestName.
   * @throws IllegalArgumentException if digestName is invalid.
   */
  public static DigestAlgorithms getInstance(String digestName) throws IllegalArgumentException {
    if (digestName.equals(SHA1.digestName)) {
      return SHA1;
    }

    if (digestName.equals(SHA224.digestName)) {
      return SHA224;
    }

    if (digestName.equals(SHA256.digestName)) {
      return SHA256;
    }

    if (digestName.equals(SHA384.digestName)) {
      return SHA384;
    }

    if (digestName.equals(SHA512.digestName)) {
      return SHA512;
    }

    throw new IllegalArgumentException("unknow digest name: " + digestName);
  }
}