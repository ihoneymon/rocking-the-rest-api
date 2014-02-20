package kr.pe.ihoney.jco.restapi.domain.type;

import org.springframework.core.Ordered;

public enum GroupType implements Ordered {
    PRIVATE("code.groupType.private", 1),
    PUBLIC("code.groupType.public", 2); 

    private String code;
    private int order;

    GroupType(String code, int order) {
        this.code = code;
        this.order = order;
    }

    public String getCode() {
        return code;
    }

    @Override
    public int getOrder() {
        return this.order;
    }
}
