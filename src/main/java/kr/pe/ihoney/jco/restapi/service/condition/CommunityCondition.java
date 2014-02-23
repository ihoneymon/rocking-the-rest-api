package kr.pe.ihoney.jco.restapi.service.condition;

import kr.pe.ihoney.jco.restapi.domain.type.CommunityType;

import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class CommunityCondition {
    private String name;
    private CommunityType type;

    public boolean hasName() {
        return StringUtils.hasText(name);
    }

    public boolean hasType() {
        return null != type;
    }
}
