package greenart.festival.member.dto;

import greenart.festival.member.entity.Social;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class MemberAuthDTO extends User implements OAuth2User{


    private String email;
    private String name;
    private String password;
    private Social provider;

    private Map<String, Object> attr;

    public MemberAuthDTO(String username, String name, String password, Collection<? extends GrantedAuthority> authorities , Social provider) {
        super(username, password, authorities);
        this.email = username;
        this.name = name;
        this.password = password;
        this.provider = provider;
    }

    public MemberAuthDTO(String username, String password, String name, Collection<? extends GrantedAuthority> authorities, Social provider, Map<String, Object> attr) {
        this(username, name, password, authorities, provider);
        this.attr = attr;

    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
