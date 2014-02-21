package kr.pe.ihoney.jco.restapi.service.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.validator.internal.xml.GroupsType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "name", "type" })
public class GroupCondition {
    private String name;
    private GroupsType type;
}
