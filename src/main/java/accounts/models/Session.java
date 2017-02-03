package accounts.models;

import java.util.Date;

/**
 * Session used to authenticate users on REST API calls
 */
public class Session {

  /* Fields */
  private String sessionToken;
  private Date expiresAt;
  private String updateToken;

}
