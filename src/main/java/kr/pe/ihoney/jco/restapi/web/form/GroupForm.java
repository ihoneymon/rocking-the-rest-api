package kr.pe.ihoney.jco.restapi.web.form;

import javax.validation.constraints.NotNull;

import kr.pe.ihoney.jco.restapi.domain.Group;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.domain.type.GroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "name", "type", "owner" })
public class GroupForm {
    @NotNull
    private String name; // 커뮤니티명
    @NotNull
    private GroupType type; // 커뮤니티유형
    @NotNull
    private User owner; // 관리자
    
    public Group createGroup() {
        return new Group(getName(), getType(), getOwner());
    }

    public Group bind(Group target) {
        target.setName(this.name);
        return target;
    }
}
