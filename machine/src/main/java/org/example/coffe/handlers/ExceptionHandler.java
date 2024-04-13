package org.example.coffe.handlers;

import lombok.extern.slf4j.Slf4j;
import org.example.coffe.exceptions.ResourcesExceptions;
import org.example.coffe.model.ResultModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ResourcesExceptions.class)
    public ResponseEntity<ResultModel<String>> resourceException(ResourcesExceptions resourcesExceptions){
        log.info("Недостаточно ресурсов {}", resourcesExceptions.getMessage());
        ResultModel<String> resultModel = new ResultModel<>();
        resultModel.getErrors().add(resourcesExceptions.getMessage());
        return ResponseEntity.badRequest().body(resultModel);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ResultModel<String>> exceptions(Exception e){
        log.error("Ошибка: ", e);
        ResultModel<String> resultModel = new ResultModel<>();
        resultModel.getErrors().add("Произошла непредвиденная ошибка");
        return ResponseEntity.internalServerError().body(resultModel);
    }
}
