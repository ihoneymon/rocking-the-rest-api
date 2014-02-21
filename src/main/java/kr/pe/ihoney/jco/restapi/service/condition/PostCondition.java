package kr.pe.ihoney.jco.restapi.service.condition;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(of = { "title", "article" })
public class PostCondition {
    private String title;
    private String article;
}
