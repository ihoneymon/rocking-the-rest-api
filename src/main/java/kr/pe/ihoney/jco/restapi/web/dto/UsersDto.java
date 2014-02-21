package kr.pe.ihoney.jco.restapi.web.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import kr.pe.ihoney.jco.restapi.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name="users")
public class UsersDto {

    private List<User> user;

    public UsersDto(List<User> allUsers) {
        this.user = allUsers;
    }
}
