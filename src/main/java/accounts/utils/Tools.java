package accounts.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.UUID;

public class Tools {

  /* Singletons */
  private static Cipher encryptionCipher = null;
  private static Cipher decryptionCipher = null;
  /* ^ helps these */
  private static Key aesKey = new SecretKeySpec(Constants.SECRET_KEY.getBytes(), "AES");

  /**
   * Cipher singleton getter helper (parameterized by mode, ENCYPT_MODE
   * or DECRYPT_MODE)
   * @param mode - int
   * @return - Cipher
   */
  private static Cipher getCipher (Cipher cipher, int mode) {
    if (cipher == null) {
      try {
        Cipher newCipher = Cipher.getInstance("AES");
        cipher.init(mode, Tools.aesKey);
        return cipher;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    } else {
      return cipher;
    }
  }

  /**
   * Cipher singleton getter for encryption cipher
   * @return - Cipher
   */
  private static Cipher getEncryptionCipher () {
    Tools.encryptionCipher = Tools.getCipher(Tools.encryptionCipher, Cipher.ENCRYPT_MODE);
    return Tools.encryptionCipher;
  }

  /**
   * Cipher singleton getter for decryption cipher
   * @return - Cipher
   */
  private static Cipher getDecryptionCipher () {
    Tools.decryptionCipher = Tools.getCipher(Tools.decryptionCipher, Cipher.DECRYPT_MODE);
    return Tools.decryptionCipher;
  }

  /**
   * Generates a URL-safe random token that's 20 bytes in size
   * (citing: https://goo.gl/ziO6sE)
   * @return - String (token)
   */
  public static String urlSafeRandomToken() {
    SecureRandom random = new SecureRandom();
    byte bytes[] = new byte[20];
    random.nextBytes(bytes);
    Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    String token = encoder.encodeToString(bytes);
    return token;
  }


  public static String encryptUUID() {
    // TODO
    return null;
  }

  public static UUID decryptUUID() {
    // TODO
    return null;
  }

}
