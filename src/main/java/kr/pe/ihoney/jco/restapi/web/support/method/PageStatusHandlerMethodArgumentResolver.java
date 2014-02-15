package kr.pe.ihoney.jco.restapi.web.support.method;

import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatusFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PageStatusHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private PageStatusFactory pageStatusFactory;
    
    public void setPageStatusFactory(PageStatusFactory pageStatusFactory) {
        this.pageStatusFactory = pageStatusFactory;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return PageStatus.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
            ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {
        return pageStatusFactory.create(webRequest);
    }


}
