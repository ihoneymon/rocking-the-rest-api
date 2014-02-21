package kr.pe.ihoney.jco.restapi.web.form;

import javax.validation.constraints.NotNull;

import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.domain.type.CommunityType;
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
    private CommunityType type; // 커뮤니티유형
    @NotNull
    private User manager; // 관리자
    
    public Community createCommunity() {
        return new Community(getName(), getType(), getManager());
    }

    public Community bind(Community target) {
        target.setName(this.name);
        return target;
    }
}
