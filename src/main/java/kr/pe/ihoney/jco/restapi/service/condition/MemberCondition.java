package kr.pe.ihoney.jco.restapi.service.condition;

import org.springframework.util.StringUtils;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(of = { "nickName" })
public class MemberCondition {
    private String nickName;

    public boolean hasNickName() {
        return StringUtils.hasText(nickName);
    }
}
