package kr.pe.ihoney.jco.restapi.domain.type;

public enum UserGradeType {
    ADMIN("code.user.grade.admin"), USER("code.user.grade.user");
    
    private String code;

    private UserGradeType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }  
}
