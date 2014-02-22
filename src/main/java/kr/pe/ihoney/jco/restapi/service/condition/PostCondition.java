package kr.pe.ihoney.jco.restapi.service.condition;

import org.springframework.util.StringUtils;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(of = { "title", "article" })
public class PostCondition {
    private String title;
    private String article;

    public boolean hasTitle() {
        return StringUtils.hasText(title);
    }

    public boolean hasArticle() {
        return StringUtils.hasText(article);
    }
}
