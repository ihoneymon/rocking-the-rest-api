package kr.pe.ihoney.jco.restapi.service.condition;

import kr.pe.ihoney.jco.restapi.domain.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.util.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "nickName", "group" })
public class MemberCondition {
    private String nickName;
    private Group group;
    
    public boolean hasNickName() {
        return StringUtils.hasText(nickName);
    }
    
    public boolean hasGroup() {
        return null != group;
    }
}
