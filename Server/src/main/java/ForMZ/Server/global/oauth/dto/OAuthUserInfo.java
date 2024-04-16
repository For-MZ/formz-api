package ForMZ.Server.global.oauth.dto;

import ForMZ.Server.domain.user.entity.SignType;
import ForMZ.Server.global.oauth.exception.SocialTypeNotFoundException;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OAuthUserInfo {
    private String socialId;

    private SignType socialType;

    private String email;

    private String imageUrl;

    public static OAuthUserInfo getOAuthUserInfoBySocial(String target, Map<String, Object> attribute) {
        target = target.toUpperCase();
        return switch (target) {
            case "google" -> toGoogleUserInfo(target, attribute);
            case "kakao" -> toKakaoUserInfo(target, attribute);
            case "naver" -> toNaverUserInfo(target, attribute);
            default -> throw new SocialTypeNotFoundException();
        };
    }

    private static OAuthUserInfo toGoogleUserInfo(String social, Map<String, Object> attribute) {
        return OAuthUserInfo.builder()
                .socialType(SignType.valueOf(social))
                .socialId((String) attribute.get("id"))
                .email((String) attribute.get("email"))
                .imageUrl((String) attribute.get("picture"))
                .build();
    }

    private static OAuthUserInfo toKakaoUserInfo(String social, Map<String, Object> attribute) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        Map<String, Object> properties = (Map<String, Object>) attribute.get("properties");
        return OAuthUserInfo.builder()
                .socialType(SignType.valueOf(social))
                .socialId(String.valueOf(attribute.get("id")))
                .email((String) kakaoAccount.get("email"))
                .imageUrl((String) properties.get("profile_image"))
                .build();
    }

    private static OAuthUserInfo toNaverUserInfo(String social, Map<String, Object> attribute) {
        Map<String, Object> naverAccount = (Map<String, Object>) attribute.get("response");
        return OAuthUserInfo.builder()
                .socialType(SignType.valueOf(social))
                .socialId((String) naverAccount.get("id"))
                .email((String) naverAccount.get("email"))
                .imageUrl((String) naverAccount.get("profile_image"))
                .build();
    }
}
