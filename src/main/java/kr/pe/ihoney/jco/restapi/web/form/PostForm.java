package kr.pe.ihoney.jco.restapi.web.form;

import javax.validation.constraints.NotNull;

import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostForm {
    @NotNull
    private String title;
    @NotNull
    private String article;
    @NotNull
    private Member writer;

    public Post bind(Post target) {
        target.changeTitle(getTitle());
        target.changeArticle(getArticle());
        return target;
    }
}
