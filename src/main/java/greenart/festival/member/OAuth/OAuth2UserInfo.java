package greenart.festival.member.OAuth;

import greenart.festival.member.entity.Social;

public interface OAuth2UserInfo {
    String getProviderId();
    Social getProvider();
    String getEmail();
    String getName();
}
