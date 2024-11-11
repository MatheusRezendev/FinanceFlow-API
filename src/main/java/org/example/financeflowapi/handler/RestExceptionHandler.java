package org.example.financeflowapi.handler;

import org.example.financeflowapi.common.ConversorData;
import org.example.financeflowapi.domain.model.repository.service.exception.exception.ResourceBadRequestException;
import org.example.financeflowapi.domain.model.repository.service.exception.exception.ResourceNotFoundException;
import org.example.financeflowapi.domain.model.repository.service.exception.model.ErrorResposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResposta> handleResourceNotFoundException(ResourceNotFoundException ex) {

        String dataHora = ConversorData.converterDateParaDataEHora(new Date());     //Cria uma data

        ErrorResposta erro = new ErrorResposta(                                     //Monta o objeto de erro
                dataHora,
                HttpStatus.NOT_FOUND.value(),
                "Not Found", ex.getMessage());

        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);                    //Retorna a exceção
    }

    @ExceptionHandler(ResourceBadRequestException.class)
    public ResponseEntity<ErrorResposta> handleResourceBadRequestException(ResourceBadRequestException ex) {

        String dataHora = ConversorData.converterDateParaDataEHora(new Date());

        ErrorResposta erro = new ErrorResposta(
                dataHora,
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request", ex.getMessage());

        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResposta> handleRequestException(Exception ex) {

        String dataHora = ConversorData.converterDateParaDataEHora(new Date());

        ErrorResposta erro = new ErrorResposta(
                dataHora,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error", ex.getMessage());

        return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
