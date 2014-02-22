package kr.pe.ihoney.jco.restapi.service.condition;

import kr.pe.ihoney.jco.restapi.domain.type.GroupType;

import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class GroupCondition {
    private String name;
    private GroupType type;

    public boolean hasName() {
        return StringUtils.hasText(name);
    }

    public boolean hasType() {
        return null != type;
    }
}
