package kr.pe.ihoney.jco.restapi.service.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.util.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "title", "article" })
public class PostCondition {
    private String title;
    private String article;
    private String creatorName;
    
    public boolean hasTitle() {
        return StringUtils.hasText(title);
    }
    
    public boolean hasArticle() {
        return StringUtils.hasText(article);
    }
    
    public boolean hasCreatorName() {
        return StringUtils.hasText(creatorName);
    }
}
