package kr.pe.ihoney.jco.restapi.web.form;

import kr.pe.ihoney.jco.restapi.domain.Group;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.domain.type.GroupType;
import lombok.Data;

@Data
public class GroupForm {
    private String name;
    private GroupType type;
    private User user;

    public Group createGroup() {
        return new Group(name, type, user);
    }
    
    public Group bind(Group target) {
        target.setName(name);
        return target;
    }
}
