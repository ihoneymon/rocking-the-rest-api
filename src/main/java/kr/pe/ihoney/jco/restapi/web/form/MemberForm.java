package kr.pe.ihoney.jco.restapi.web.form;

import kr.pe.ihoney.jco.restapi.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberForm {
    private String email;
    private String name;
    
    public Member createMember() {
        return new Member(getEmail(), getName());
    }

    public Member bind(Member target) {
        target.changeName(getName());
        return target;
    }
}
