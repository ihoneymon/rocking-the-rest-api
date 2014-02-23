package kr.pe.ihoney.jco.restapi.web.form;

import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.domain.type.CommunityType;
import lombok.Data;

@Data
public class CommunityForm {
    private String name;
    private CommunityType type;
    private User user;

    public Community createGroup() {
        return new Community(name, type, user);
    }
    
    public Community bind(Community target) {
        target.setName(name);
        return target;
    }
}
