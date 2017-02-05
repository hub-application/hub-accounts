package accounts.services;

import accounts.utils.Constants;
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
    try {
      /* HTTP client */
      CloseableHttpClient client = HttpClients.createDefault();

      /* POST URL */
      HttpPost post = new HttpPost("https://api.soundcloud.com/oauth2/token");

      /* Set form entity based on params */
      post.setEntity(new UrlEncodedFormEntity(params));

      /* Make the request */
      HttpResponse response = client.execute(post);

      /* Stream */
      BufferedReader rd = new BufferedReader(
        new InputStreamReader(response.getEntity().getContent()));

      /* Build a JsonNode */
      StringBuffer result = new StringBuffer();
      String line;
      while ((line = rd.readLine()) != null) {
        result.append(line);
      }
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readTree(result.toString());
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

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
