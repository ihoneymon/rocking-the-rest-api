package kr.pe.ihoney.jco.restapi.service.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of={"email", "name"})
public class MemberCondition {
    private String email;
    private String name;
}
