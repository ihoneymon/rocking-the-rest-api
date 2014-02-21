package kr.pe.ihoney.jco.restapi.web.form;

import org.springframework.util.Assert;

import kr.pe.ihoney.jco.restapi.domain.Community;
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
    private Community community;
    
    public Member createMember() {
        Assert.notNull(this.community, "member.require.community");
        return new Member(this.nickName, this.community, this.user);
    }

    public Member bind(Member target) {
        target.changeNickName(this.nickName);
        return target;
    }
}
