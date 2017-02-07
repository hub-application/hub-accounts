package accounts.services;

import accounts.utils.Constants;
import com.wrapper.spotify.Api;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class SpotifyService {

  @Getter private String clientID;
  @Getter private String clientSecret;
  @Getter private String redirectURI;
  @Getter private Api api;

  /**
   * Constructor
   */
  public SpotifyService() {
    this.clientID = Constants.HUB_SPOTIFY_CLIENT_ID;
    this.clientSecret = Constants.HUB_SPOTIFY_SECRET;
    this.redirectURI = Constants.HUB_SPOTIFY_REDIRECT_URI;
    this.api = Api.builder()
      .clientId(this.clientID)
      .clientSecret(this.clientSecret)
      .redirectURI(this.redirectURI)
      .build();
  }


}
