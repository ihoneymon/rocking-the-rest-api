package kr.pe.ihoney.jco.restapi.domain.type;

import java.util.Set;

import org.springframework.core.Ordered;

import com.google.common.collect.Sets;

public enum PostType implements Ordered {
    PRIVATE("code.postType.private", 1), 
    PUBLIC("code.postType.public", 2), 
    PUBLISH("code.postType.publish", 3);

    private String code;
    private int order;

    private PostType(String code, int order) {
        this.code = code;
        this.order = order;
    }

    public String getCode() {
        return code;
    }

    @Override
    public int getOrder() {
        return order;
    }
    
    public static PostType typeFromOrder(final int order) {
        for(PostType postType: values()) {
            if(postType.getOrder() == order) return postType; 
        }
        
        throw new IllegalArgumentException("Unknown order("+order+") for Enum Type " + PostType.class.getSimpleName());
    }
    
    public static Set<PostType> types(boolean sort) {
        Set<PostType> types = Sets.newTreeSet();
        if(sort) {
            for(PostType postType: values())
                types.add(postType);
        } else {
            types = Sets.newHashSet(values());
        }
        return types;
    }
}