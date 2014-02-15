package kr.pe.ihoney.jco.restapi.web.support.method;

import java.beans.PropertyEditorSupport;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.support.RequestContextUtils;

@Slf4j
public class PageableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    public static final String PAGEABL_VARIABLE = "pageRequest";

    private static final String DEFAULT_PREFIX = "page";
    private static final String DEFAULT_SEPARATOR = ".";
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_SIZE_NUMBER = 10;

    private String prefix = DEFAULT_PREFIX;
    private String separator = DEFAULT_SEPARATOR;
    private int defaultPageNumber = DEFAULT_PAGE_NUMBER;
    private int defaultSizeNumber = DEFAULT_SIZE_NUMBER;

    public void setDefaultPageNumber(int defaultPageNumber) {
        this.defaultPageNumber = defaultPageNumber <= 0 ? DEFAULT_PAGE_NUMBER : defaultPageNumber;
    }

    public void setDefaultSizeNumber(int defaultSizeNumber) {
        this.defaultSizeNumber = defaultSizeNumber <= 0 ? DEFAULT_SIZE_NUMBER : defaultSizeNumber;
    }

    public void setPrefix(String prefix) {
        this.prefix = null == prefix ? DEFAULT_PREFIX : prefix;
    }

    public void setSeparator(String separator) {
        this.separator = null == separator ? DEFAULT_SEPARATOR : separator;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Pageable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Pageable pageable = createFromDefaultConfig();

        ServletRequest servletRequest = (ServletRequest) webRequest.getNativeRequest();
        PropertyValues propertyValues = new ServletRequestParameterPropertyValues(servletRequest,
                prefix, separator);

        DataBinder binder = new ServletRequestDataBinder(pageable);

        binder.initDirectFieldAccess();
        binder.registerCustomEditor(Sort.class, new SortPropertyEditor("sort.dir", propertyValues));
        binder.bind(propertyValues);

        if (pageable.getPageNumber() > 0)
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                    pageable.getSort());

        HttpServletRequest httpServletRequest = webRequest
                .getNativeRequest(HttpServletRequest.class);
        RequestContextUtils.getOutputFlashMap(httpServletRequest).put(PAGEABL_VARIABLE, pageable);

        log.debug("pageable instantiated: {}-{}.{}", new Object[] { pageable.getPageNumber(),
                pageable.getPageSize(), pageable.getSort() });

        return pageable;
    }

    private Pageable createFromDefaultConfig() {
        return new PageRequest(defaultPageNumber, defaultSizeNumber);
    }

    /**
     * {@link org.springframework.data.web.PageableArgumentResolver.SortPropertyEditor}
     * 코드를 가져옴.
     */
    private static class SortPropertyEditor extends PropertyEditorSupport {

        private final String orderProperty;
        private final PropertyValues values;

        public SortPropertyEditor(String orderProperty, PropertyValues values) {
            this.orderProperty = orderProperty;
            this.values = values;
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            PropertyValue rawOrder = values.getPropertyValue(orderProperty);
            Direction order = null == rawOrder ? Direction.ASC : Direction.fromString(rawOrder
                    .getValue().toString());

            setValue(new Sort(order, text));
        }
    }

}
