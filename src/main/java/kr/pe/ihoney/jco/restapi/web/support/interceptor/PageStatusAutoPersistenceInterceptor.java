package kr.pe.ihoney.jco.restapi.web.support.interceptor;

import java.util.Collection;

import javax.inject.Inject;

import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatusFactory;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

@Slf4j
public class PageStatusAutoPersistenceInterceptor implements WebRequestInterceptor {
    public static final String DEFAULT_ATTRIBUTE_NAME = "pageStatus";
     
    
    @Inject PageStatusFactory pageStatusFactory;
    

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
        if(model != null) {
            PageStatus pageStatus = findPageStatus(model.values());
            if(pageStatus == null) {
                pageStatus = pageStatusFactory.create(request);
                model.addAttribute(DEFAULT_ATTRIBUTE_NAME, pageStatus);
                
                log.debug("model add {}", pageStatus);
            }
        }
    }
    
    private PageStatus findPageStatus(Collection<?> values) {
        for(Object value : values) {
            if(ClassUtils.isAssignableValue(PageStatus.class, value)) {
                return (PageStatus) value;
            }
        }
        return null;
    }

    @Override
    public void preHandle(WebRequest request) throws Exception {
        // nothing
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex)
            throws Exception {
        // nothing
    }

}
