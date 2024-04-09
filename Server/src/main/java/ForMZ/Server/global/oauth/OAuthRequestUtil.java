package ForMZ.Server.global.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuthRequestUtil {

    private final OAuthProperty oAuthProperty;

    public OAuthUserInfo getOAuthUserInfo(String target, String code) {
        OAuthProperty.PropertyInfo property = getOAuthProperty(target);
        Map<String, Object> userInfo = getUserInfo(property, getOAuthAccessToken(property, code));
        return OAuthUserInfo.getOAuthUserInfoBySocial(target, userInfo);
    }

    // TODO : 비동기 고려 -> 외부 I/O
    private Map<String, Object> getUserInfo(OAuthProperty.PropertyInfo property, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        return new RestTemplate().exchange(property.getUserInfoUri(), HttpMethod.GET, new HttpEntity<>(headers), Map.class).getBody();
    }

    private String getOAuthAccessToken(OAuthProperty.PropertyInfo property, String code) {
        try {
            return new RestTemplate().exchange(property.getTokenUri(), HttpMethod.POST, new HttpEntity<>(setQueryParams(property, code), null), OAuthToken.class).getBody().getAccessToken();
        } catch (Exception e) {
            throw new AuthorizationCodeErrorException();
        }
    }

    private MultiValueMap<String, String> setQueryParams(OAuthProperty.PropertyInfo property, String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", property.getClientId());
        params.add("client_secret", property.getClientSecret());
        params.add("redirect_uri", property.getRedirectUri());
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        return params;
    }

    private OAuthProperty.PropertyInfo getOAuthProperty(String target) {
        OAuthProperty.PropertyInfo propertyInfo = oAuthProperty.getProperties().get(target);
        if (propertyInfo == null) throw new SocialTypeNotFoundException();
        return propertyInfo;
    }
}
