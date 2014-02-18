package kr.pe.ihoney.jco.restapi.common.mapper;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * Jackson JSON processor which handles Hibernate 
 * <a href="http://blog.pastelstudios.com/2012/03/12/spring-3-1-hibernate-4-jackson-module-hibernate/">
 * Spring 3.1, Hibernate 4 and Jackson-Module-Hibernate</a>
 * 
 * @author ihoneymon
 * 
 */
public class HibernateAwareObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = -8821453185971012130L;

    public HibernateAwareObjectMapper() {
        configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        
        registerModule(getHibernate4Module());
    }

    private Module getHibernate4Module() {
        Hibernate4Module hibernateModule = new Hibernate4Module();
        hibernateModule.disable(Hibernate4Module.Feature.FORCE_LAZY_LOADING);
        return hibernateModule;
    }
}
