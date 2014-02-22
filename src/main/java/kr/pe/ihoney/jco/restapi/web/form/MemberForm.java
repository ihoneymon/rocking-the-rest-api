package kr.pe.ihoney.jco.restapi.web.form;

import org.springframework.util.Assert;

import kr.pe.ihoney.jco.restapi.domain.Group;
import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberForm {
    private String nickName;
    private User user;
    private Group group;

    public Member createMember() {
        return new Member(this.nickName, this.group, this.user);
    }

    public Member createMember(Group group) {
        Assert.notNull(group, "member.require.group");
        return new Member(this.nickName, group, this.user);
    }

    public Member bind(Member target) {
        target.changeNickName(this.nickName);
        return target;
    }
}
