package kr.pe.ihoney.jco.restapi.web.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import kr.pe.ihoney.jco.restapi.domain.Group;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name="communities")
public class CommunityDto {
    private List<Group> community;

    public CommunityDto(List<Group> community) {
        this.community = community;
    }
}
