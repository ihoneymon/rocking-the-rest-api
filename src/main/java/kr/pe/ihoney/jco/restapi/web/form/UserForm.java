package kr.pe.ihoney.jco.restapi.web.form;

import kr.pe.ihoney.jco.restapi.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {
    private String email;
    private String name;
    
    public User createUser() {
        return new User(getEmail(), getName());
    }

    public User bind(User target) {
        target.changeName(getName());
        return target;
    }
}
