package kr.pe.ihoney.jco.restapi.web.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import kr.pe.ihoney.jco.restapi.domain.Community;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name="communities")
public class CommunityDto {
    private List<Community> community;

    public CommunityDto(List<Community> community) {
        this.community = community;
    }
}
