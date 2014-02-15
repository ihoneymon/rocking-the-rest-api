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
    private String password;
    
    public User createUser() {
        return new User(this.name, this.email, this.password);
    }
    
    public User bind(User target) {
        target.setName(this.name);
        target.setEmail(this.email);
        target.setPassword(password);
        return target;
    }
}
