package accounts.models;

import com.couchbase.client.java.document.json.JsonObject;
import com.fasterxml.uuid.Generators;

import java.util.UUID;

/**
 * Hub user (Facebook, Spotify, and SoundCloud credentials)
 */
public class User {

  private UUID uuid;
  private String handle;
  private String facebookID;
  private String spotifyID;
  private Integer soundcloudID;
  private Boolean isGuest;
  private Session session;

  /**
   * Constructor from UserBuilder
   * @param builder - UserBuilder
   */
  public User(UserBuilder builder) {
    this.uuid = builder.uuid;
    this.handle = builder.handle;
    this.facebookID = builder.facebookID;
    this.spotifyID = builder.spotifyID;
    this.soundcloudID = builder.soundcloudID;
    this.isGuest = builder.isGuest;
    this.session = builder.session;
  }

  /**
   * Constructor from JsonObject
   * @param object - JsonObject
   */
  public User(JsonObject object) {
    this.uuid = UUID.fromString(object.getString("uuid"));
    this.handle = object.getString("handle");
    this.isGuest = object.getBoolean("is_guest");
    this.facebookID = object.containsKey("spotify_id") ? object.getString("spotify_id") : null;
    this.soundcloudID = object.containsKey("soundcloud_id") ? object.getInt("soundcloud_id") : null;
    this.session = object.containsKey("session") ? new Session(object.getObject("session")) : null;
  }

  /**
   * Convert this user into a JsonObject
   * @return JsonObject
   */
  public JsonObject toJsonObject() {
    JsonObject result = JsonObject.create();
    result.put("uuid", uuid);
    result.put("handle", handle);
    result.put("is_guest", isGuest);
    if (session != null) { result.put("session", session.toJsonObject()); }
    if (facebookID != null) { result.put("facebook_id", facebookID); }
    if (spotifyID != null) { result.put("spotify_id", spotifyID); }
    if (soundcloudID != null) { result.put("soundcloud_id", soundcloudID); }
    return result;
  }

  /**
   * Hub user builder
   */
  public static class UserBuilder {

    private UUID uuid;
    private String handle;
    private String facebookID;
    private String spotifyID;
    private Integer soundcloudID;
    private Boolean isGuest;
    private Session session;

    /**
     * Constructor required fields
     * @param handle - String
     * @param isGuest - Boolean
     */
    public UserBuilder(String handle, Boolean isGuest) {
      this.uuid = Generators.timeBasedGenerator().generate(); // Generate this
      this.handle = handle;
      this.isGuest = isGuest;
    }

    public UserBuilder facebookID(String facebookID) {
      this.facebookID = facebookID;
      return this;
    }

    public UserBuilder spotifyID(String spotifyID) {
      this.spotifyID = spotifyID;
      return this;
    }

    public UserBuilder soundcloudID(Integer soundcloudID) {
      this.soundcloudID = soundcloudID;
      return this;
    }

    public UserBuilder session(Session session) {
      this.session = session;
      return this;
    }

    public User build() {
      return new User(this);
    }

  }

}
