package accounts.models;

import com.couchbase.client.java.document.json.JsonObject;
import java.util.Date;

/**
 * Session used to authenticate users on REST API calls
 */
public class Session {

  /* Fields */
  private String sessionToken;
  private Date expiresAt;
  private String updateToken;

  /**
   * Standard constructor
   * @param user - The owning user of this session
   */
  public Session(User user) {

  }

  /**
   * Constructor from owning User and JsonObject
   * @param object - JsonObject
   */
  public Session(JsonObject object) {
    this.sessionToken = object.getString("session_token");
    this.expiresAt = new Date(object.getLong("expires_at"));
    this.updateToken = object.getString("update_token");
  }

  /**
   * Convert this user into a JsonObject
   * @return JsonObject
   */
  public JsonObject toJsonObject() {
    JsonObject result = JsonObject.create();
    result.put("session_token", sessionToken);
    result.put("expires_at", expiresAt.getTime()); // Store long
    result.put("update_token", updateToken);
    return result;
  }


}
