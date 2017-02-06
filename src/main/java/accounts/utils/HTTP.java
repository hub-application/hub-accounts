package accounts.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class HTTP {

  public static JsonNode post (String uri, List<BasicNameValuePair> params) {
    try {
      /* HTTP client */
      CloseableHttpClient client = HttpClients.createDefault();

      /* POST URL */
      HttpPost post = new HttpPost(uri);

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

}
