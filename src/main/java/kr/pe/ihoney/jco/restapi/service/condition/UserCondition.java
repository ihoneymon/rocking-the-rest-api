package kr.pe.ihoney.jco.restapi.service.condition;

import org.springframework.util.StringUtils;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(of = { "name", "email" })
public class UserCondition {
    private String name;
    private String email;

    public boolean hasName() {
        return StringUtils.hasText(name);
    }

    public boolean hasEmail() {
        return StringUtils.hasText(email);
    }
}
