package kr.pe.ihoney.jco.restapi.service.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "title", "article" })
public class PostCondition {
    private String title;
    private String article;
}
