package greenart.festival.member.OAuth;

import greenart.festival.member.entity.Social;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class GoogleUserDetails implements OAuth2UserInfo{

    private Map<String, Object> attr;

    @Override
    public String getProviderId() {
        return attr.get("sub").toString();
    }

    @Override
    public Social getProvider() {
        return Social.GOOGLE;
    }

    @Override
    public String getEmail() {
        System.out.println(attr);
        return attr.get("email").toString();
    }

    @Override
    public String getName() {
        return (String) attr.get("name");
    }
}
