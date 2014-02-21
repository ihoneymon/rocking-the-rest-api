package kr.pe.ihoney.jco.restapi.web.support.view;

import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.core.convert.ConversionException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Component
public class PageStatusFactory {

    private final static Logger logger = LoggerFactory.getLogger(PageStatusFactory.class);

    private static final String[] ignoreParameters = new String[] { "_method" };
    private static final String[] pageableFields = new String[] { "pageNumber", "pageSize", "sort" };
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String REQUEST_ATTRIBUTE_NAME = PageStatusFactory.class.getName();
    private static final String DEFAULT_PREFIX = "ps";
    private static final String DEFAULT_SEPARATOR = ".";
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_SIZE_NUMBER = 10;

    private String prefix = DEFAULT_PREFIX;
    private String separator = DEFAULT_SEPARATOR;
    private int defaultPageNumber = DEFAULT_PAGE_NUMBER;
    private int defaultSizeNumber = DEFAULT_SIZE_NUMBER;

    public void setPrefix(String prefix) {
        this.prefix = null == prefix ? DEFAULT_PREFIX : prefix;
    }

    public void setSeparator(String separator) {
        this.separator = null == separator ? DEFAULT_SEPARATOR : separator;
    }

    public void setDefaultPageNumber(int defaultPageNumber) {
        this.defaultPageNumber = defaultPageNumber <= 0 ? DEFAULT_PAGE_NUMBER : defaultPageNumber;
    }

    public void setDefaultSizeNumber(int defaultSizeNumber) {
        this.defaultSizeNumber = defaultSizeNumber <= 0 ? DEFAULT_SIZE_NUMBER : defaultSizeNumber;
    }

    public PageStatus create(WebRequest request) {
        NativeWebRequest webRequest = (NativeWebRequest) request;
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        // request에 저장된 pageStatus가 있으면 꺼내기
        PageStatus pageStatus = (PageStatus) request.getAttribute(REQUEST_ATTRIBUTE_NAME,
                WebRequest.SCOPE_REQUEST);

        // FlashScope에서 pageStatus 생성
        if (pageStatus == null) {
            pageStatus = createFromFlashMap(servletRequest);

            request.setAttribute(REQUEST_ATTRIBUTE_NAME, pageStatus, WebRequest.SCOPE_REQUEST);

            logger.debug("found pageStatus in flashMap: {}", pageStatus);
        }

        if (pageStatus == null) {
            // pageable 값 초기화 및 prefix가 붙은 파라메터들을 추출
            pageStatus = initPageableValueFromServletRequest(servletRequest, prefix, separator);
            Map<String, Object> parameters = WebUtils.getParametersStartingWith(servletRequest,
                    prefix + separator);
            if (CollectionUtils.isEmpty(parameters)) {
                // prefix가 붙은 파라메터들이 없으면 request에 담겨있는 모든 파라메터를 대상으로 추출
                pageStatus = initPageableValueFromServletRequest(servletRequest, null, null);
                parameters = WebUtils.getParametersStartingWith(servletRequest, null);
            }

            // 불필요한 값은 제거
            Map<String, Object> attributes = Maps.filterKeys(parameters, new Predicate<String>() {
                @Override
                public boolean apply(String input) {
                    return !CollectionUtils.containsInstance(Lists.newArrayList(ignoreParameters),
                            input);
                }
            });

            String queryString = createQueryString(attributes, pageableFields);
            String pageableQueryString = createQueryString(attributes, new String[0]);
            String jsonString = createJsonString(attributes, pageableFields);
            String pageableJsonString = createJsonString(attributes, new String[0]);

            pageStatus = new PageStatus(pageStatus.getPageNumber(), pageStatus.getPageSize(),
                    pageStatus.getSort(), attributes, queryString, pageableQueryString, jsonString,
                    pageableJsonString);

            request.setAttribute(REQUEST_ATTRIBUTE_NAME, pageStatus, WebRequest.SCOPE_REQUEST);

            logger.debug("created pageStatus from request: {}", pageStatus);
        }

        return pageStatus;
    }

    // FlashScope로 넘어온 PageStatus 값을 찾아서 생성
    private PageStatus createFromFlashMap(HttpServletRequest request) {
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (!CollectionUtils.isEmpty(flashMap)) {
            for (Object value : flashMap.values()) {
                if (ClassUtils.isAssignableValue(PageStatus.class, value)) {
                    return (PageStatus) value;
                }
            }
        }
        return null;
    }

    private PageStatus createFromDefaultConfig() {
        return new PageStatus(defaultPageNumber, defaultSizeNumber, null, null, "{}", "{}", "{}",
                "{}");
    }

    private PageStatus initPageableValueFromServletRequest(HttpServletRequest request,
            String prefix, String separator) {
        PageStatus pageStatus = createFromDefaultConfig();

        ServletRequestParameterPropertyValues propertyValues = new ServletRequestParameterPropertyValues(
                request, prefix, separator);

        DataBinder binder = new ServletRequestDataBinder(pageStatus);

        binder.initDirectFieldAccess();
        binder.registerCustomEditor(Sort.class, new SortPropertyEditor("sort.dir", propertyValues));
        binder.bind(propertyValues);

        return pageStatus;
    }

    private String createQueryString(Map<String, Object> attributes, String[] ignoreFields) {
        StringBuffer queryStringBuffer = new StringBuffer();

        for (Entry<String, Object> entry : attributes.entrySet()) {
            if (!CollectionUtils.containsInstance(Lists.newArrayList(ignoreFields), entry.getKey())) {
                if (queryStringBuffer.length() != 0)
                    queryStringBuffer.append("&");
                queryStringBuffer.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }

        return queryStringBuffer.toString();
    }

    @SuppressWarnings("serial")
    private String createJsonString(Map<String, Object> attributes, String[] ignoreFields) {
        try {
            Map<String, Object> clone = new HashMap<String, Object>(attributes);
            for (String ifnoreField : ignoreFields)
                clone.remove(ifnoreField);
            return objectMapper.writeValueAsString(clone);
        } catch (Exception e) {
            throw new ConversionException("convert error - pageStatus attributes to json string", e) {
            };
        }
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
