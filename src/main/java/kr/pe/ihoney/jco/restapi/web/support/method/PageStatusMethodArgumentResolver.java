package kr.pe.ihoney.jco.restapi.web.support.method;

import javax.inject.Inject;

import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatusFactory;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PageStatusMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Inject PageStatusFactory factory;
    
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return PageStatus.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
            ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {
        return factory.create(webRequest);
    }

}
