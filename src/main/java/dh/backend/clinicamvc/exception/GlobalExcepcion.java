package dh.backend.clinicamvc.exception;


import dh.backend.clinicamvc.service.impl.OdontologoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExcepcion {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExcepcion.class);
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> RecuersoNoencontrado(ResourceNotFoundException e){
        LOGGER.info("Excepción encontrada: " + e);
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(e.getMessage());
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> BadRequest(BadRequestException e){
        LOGGER.info("Excepción encontrada: " + e);
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(e.getMessage());
    }
}
