package kr.pe.ihoney.jco.restapi.web.form;

import javax.validation.constraints.NotNull;

import kr.pe.ihoney.jco.restapi.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of={"name", "email"})
public class UserForm {
    @NotNull
    private String name;
    @NotNull
    private String email;
    
    public User createUser() {
        return new User(this.name, this.email);
    }
    
    public User bind(User target) {
        target.setName(this.name);
        target.setEmail(this.email);
        return target;
    }
}
