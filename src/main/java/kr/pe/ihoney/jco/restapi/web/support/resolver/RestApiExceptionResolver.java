package kr.pe.ihoney.jco.restapi.web.support.resolver;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Slf4j
public class RestApiExceptionResolver extends SimpleMappingExceptionResolver {
    private static final String MESSAGE = "message";
    @Inject
    private MessageSourceAccessor messageSourceAccessor;

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        String viewName = determineViewName(ex, request);
        log.debug("View Name: {}", viewName);

        if (viewName != null) {
            Integer statusCode = null;
            String message = null;
            if (ex instanceof RestApiException) {
                RestApiException restApiException = (RestApiException) ex;
                try {
                    message = messageSourceAccessor.getMessage(restApiException.getMessage(),
                            restApiException.getMessageParams());
                } catch (NoSuchMessageException nsme) {
                    message = restApiException.getMessage();
                }
                statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            } else {
                message = ex.getMessage();
                statusCode = determineStatusCode(request, viewName);
            }

            log.debug(">> Occur RestApiException: {}", ex.getMessage());

            if (statusCode != null) {
                applyStatusCodeIfPossible(request, response, statusCode);
            }

            ModelAndView mv = new ModelAndView(viewName);
            mv.addObject(MESSAGE, message);
            return mv;
        } else {
            return null;
        }
    }
}
