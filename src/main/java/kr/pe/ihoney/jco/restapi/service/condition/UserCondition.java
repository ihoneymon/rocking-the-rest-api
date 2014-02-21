package kr.pe.ihoney.jco.restapi.service.condition;

import kr.pe.ihoney.jco.restapi.domain.Group;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(of={"name", "email", "community"})
public class UserCondition {
    private String name;
    private String email;
    private Group community;    
}
