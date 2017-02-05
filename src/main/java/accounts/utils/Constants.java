package accounts.utils;

/**
 * Class containing solely static fields reflecting
 * global constants of the service
 */
public class Constants {
  public static String SECRET_KEY = System.getenv("SECRET_KEY");
  public static String HUB_SOUNDCLOUD_CLIENT_ID = System.getenv("HUB_SOUNDCLOUD_CLIENT_ID");
  public static String HUB_SOUNDCLOUD_SECRET = System.getenv("HUB_SOUNDCLOUD_SECRET");
  public static String HUB_SOUNDCLOUD_REDIRECT_URI = "hubapp://soundcloudReturnAfterLogin";
}
