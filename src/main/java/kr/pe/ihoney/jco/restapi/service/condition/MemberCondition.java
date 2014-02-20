package kr.pe.ihoney.jco.restapi.service.condition;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(of={"nickName"})
public class MemberCondition {
    private String nickName; 
}
