package kr.pe.ihoney.jco.restapi.domain.type;

import org.springframework.core.Ordered;

public enum CommunityType implements Ordered {
    PRIVATE("code.community-type.private", 1),
    PUBLIC("code.community-type.public", 2); 

    private String code;
    private int order;

    CommunityType(String code, int order) {
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
