package accounts.models;

/**
 * Hub user (Facebook, Spotify, and SoundCloud credentials)
 */
public class User {

  /* Fields */
  private String handle;
  private String facebookID;
  private String spotifyID;
  private Integer soundcloudID;
  private Boolean isGuest;
  private Session session;

}
