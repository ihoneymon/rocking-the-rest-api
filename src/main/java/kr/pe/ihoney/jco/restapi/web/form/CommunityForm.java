package kr.pe.ihoney.jco.restapi.web.form;

import javax.validation.constraints.NotNull;

import kr.pe.ihoney.jco.restapi.domain.Group;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.domain.type.GroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "name" })
@ToString(of = { "name", "type", "manager" })
public class CommunityForm {
    @NotNull
    private String name; // 커뮤니티명
    @NotNull
    private GroupType type; // 커뮤니티유형
    @NotNull
    private User manager; // 관리자
    
    public Group createCommunity() {
        return new Group(getName(), getType(), getManager());
    }

    public Group bind(Group target) {
        target.setName(this.name);
        return target;
    }
}
