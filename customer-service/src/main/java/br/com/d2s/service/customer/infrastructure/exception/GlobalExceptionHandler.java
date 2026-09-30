package br.com.d2s.service.customer.infrastructure.exception;


import br.com.d2s.ecommerce.commons.exception.APIException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(APIException.class)
    public ProblemDetail handleApiException(APIException apiException) {
        HttpStatus status = switch (apiException.getType())  {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case BUSINESS_ERROR -> HttpStatus.UNPROCESSABLE_CONTENT;
            case CONFLICT -> HttpStatus.CONFLICT;
        };
        var result = ProblemDetail.forStatusAndDetail(status, apiException.getMessage());
        result.setProperty("code", apiException.getMessage());
        return result;
    }

}
