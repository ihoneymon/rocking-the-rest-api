package kr.pe.ihoney.jco.restapi.service.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.util.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "email", "name" })
public class UserCondition {
    private String email;
    private String name;

    public boolean hasName() {
        return StringUtils.hasText(name);
    }

    public boolean hasEmail() {
        return StringUtils.hasText(email);
    }
}
