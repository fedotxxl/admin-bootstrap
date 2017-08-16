package com.onefactor.dsp.support;

import com.onefactor.dsp.domain.RestResponse;
import io.belov.soyuz.validator.FluentValidator;
import io.belov.soyuz.validator.FvMessageResolverI;
import io.belov.soyuz.validator.FvRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Преобразование Java Exception'ов в REST ответы
 */
@ControllerAdvice
public class RestExceptionHandlers {

    @Autowired
    private FvMessageResolverI fvMessageResolver;

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<RestResponse> e(BadRequestException e) {
        return new ResponseEntity<>(new RestResponse(e.getErrors()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FluentValidator.ValidationException.class)
    ResponseEntity<FvRest.ErrorsResponse> e(FluentValidator.ValidationException e) {
        return new ResponseEntity<>(exceptionToBean(e), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private FvRest.ErrorsResponse exceptionToBean(FluentValidator.ValidationException ex) {
        Object rootObject = ex.getRootObject();
        List<String> common = new ArrayList<>();
        Map<String, String> properties = new HashMap<>();

        for (FluentValidator.Error e : ex.getErrors()) {
            String property = e.getProperty();
            String message = fvMessageResolver.getMessage(rootObject, e);

            if (property != null) {
                properties.put(property, message);
            } else {
                common.add(message);
            }
        }

        return new FvRest.ErrorsResponse(common, properties);
    }
}
