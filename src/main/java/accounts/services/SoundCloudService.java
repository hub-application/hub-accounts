package accounts.services;

import accounts.utils.Constants;
import accounts.utils.HTTP;
import lombok.Getter;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class SoundCloudService {

  @Getter private String clientID;
  @Getter private String clientSecret;
  @Getter private String redirectURI;

  /**
   * Constructor
   */
  public SoundCloudService() {
    this.clientID = Constants.HUB_SOUNDCLOUD_CLIENT_ID;
    this.clientSecret = Constants.HUB_SOUNDCLOUD_SECRET;
    this.redirectURI = Constants.HUB_SOUNDCLOUD_REDIRECT_URI;
  }

  /**
   * POST request to SoundCloud
   * @param params - List of BasicNameValuePair for form request
   * @return - JsonNode
   */
  private JsonNode soundcloudPOST(List<BasicNameValuePair> params) {
    return HTTP.post("https://api.soundcloud.com/oauth2/token", params);
  }

  /**
   * SoundCloud POST to swap code for OAuth 2.0 token
   * @param soundcloudCode - String
   * @return - JsonNode
   */
  public JsonNode swap(String soundcloudCode) {
    List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
    params.add(new BasicNameValuePair("client_id", getClientID()));
    params.add(new BasicNameValuePair("client_secret", getClientSecret()));
    params.add(new BasicNameValuePair("redirect_uri", getRedirectURI()));
    params.add(new BasicNameValuePair("grant_type", "authorization_code"));
    params.add(new BasicNameValuePair("scope", "non-expiring"));
    params.add(new BasicNameValuePair("code", soundcloudCode));
    return soundcloudPOST(params);
  }

  /**
   * SoundCloud POST to refresh OAuth 2.0 token
   * @param refreshToken - String
   * @return - JsonNode
   */
  public JsonNode refresh(String refreshToken) {
    List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
    params.add(new BasicNameValuePair("client_id", getClientID()));
    params.add(new BasicNameValuePair("client_secret", getClientSecret()));
    params.add(new BasicNameValuePair("redirect_uri", getRedirectURI()));
    params.add(new BasicNameValuePair("grant_type", "refresh_token"));
    params.add(new BasicNameValuePair("scope", "non-expiring"));
    params.add(new BasicNameValuePair("refresh_token", refreshToken));
    return soundcloudPOST(params);
  }

}
