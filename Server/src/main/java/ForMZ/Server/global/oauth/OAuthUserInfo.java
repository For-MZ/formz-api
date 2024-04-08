package ForMZ.Server.global.oauth;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OAuthUserInfo {
    private String socialId;

    private String socialType;

    private String email;

    // TODO : 예외처리 추가
    public static OAuthUserInfo getOAuthUserInfoBySocial(String target, Map<String, Object> attribute) {
        return switch (target) {
            case "google" -> toGoogleUserInfo(target, attribute);
            case "kakao" -> toKakaoUserInfo(target, attribute);
            case "naver" -> toNaverUserInfo(target, attribute);
            default -> null;
        };
    }

    private static OAuthUserInfo toGoogleUserInfo(String social, Map<String, Object> attribute) {
        return OAuthUserInfo.builder()
                .socialType(social.toUpperCase())
                .socialId((String) attribute.get("id"))
                .email((String) attribute.get("email"))
                .build();
    }

    private static OAuthUserInfo toKakaoUserInfo(String social, Map<String, Object> attribute) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        return OAuthUserInfo.builder()
                .socialType(social.toUpperCase())
                .socialId((String) attribute.get("id"))
                .email((String) kakaoAccount.get("email"))
                .build();
    }

    private static OAuthUserInfo toNaverUserInfo(String social, Map<String, Object> attribute) {
        Map<String, Object> naverAccount = (Map<String, Object>) attribute.get("response");
        return OAuthUserInfo.builder()
                .socialType(social.toUpperCase())
                .socialId((String) naverAccount.get("id"))
                .email((String) naverAccount.get("email"))
                .build();
    }
}
