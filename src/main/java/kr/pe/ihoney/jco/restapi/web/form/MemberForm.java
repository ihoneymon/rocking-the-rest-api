package kr.pe.ihoney.jco.restapi.web.form;

import kr.pe.ihoney.jco.restapi.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberForm {
    private String nickName;
    private User user;
}
