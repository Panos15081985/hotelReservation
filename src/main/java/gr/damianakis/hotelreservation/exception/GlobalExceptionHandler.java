package gr.damianakis.hotelreservation.exception;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleNotFound(ResourceNotFoundException ex){
        log.warn(ex.getMessage());
        return problem(HttpStatus.NOT_FOUND, "Resource not found", ex.getMessage());
    }

    @ExceptionHandler(ReservationOverlapException.class)
    public ProblemDetail handleOverlap(ReservationOverlapException ex){
        log.warn(ex.getMessage());
        return problem(HttpStatus.CONFLICT, "Room not available", ex.getMessage());
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ProblemDetail handleInvalidDates(InvalidDateRangeException ex){
        log.warn(ex.getMessage());
        return problem(HttpStatus.BAD_REQUEST, "Invalid date range", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception ex) {
        log.error("Unexpected error", ex);
        return problem(HttpStatus.INTERNAL_SERVER_ERROR,"Internal Server Error","An unexpected error occurred. Please try again later.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrity(DataIntegrityViolationException ex){
        log.warn(ex.getMessage());
        return problem(HttpStatus.CONFLICT,"Data conflict","The request conflicts with existing data");
    }

    @ExceptionHandler(RoomAlreadyExistException.class)
    public ProblemDetail handleRoomAlreadyExists(RoomAlreadyExistException ex){
        log.warn(ex.getMessage());
        return problem(HttpStatus.CONFLICT,"Room Already exist", "Room already exist with this Number");
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException methodArgumentNotValidException, HttpHeaders headers,
            HttpStatusCode statusCode, WebRequest request
    ){
        Map<String, String> errors = new LinkedHashMap<>();

        methodArgumentNotValidException.getBindingResult().getFieldErrors()
                .forEach(error -> errors.putIfAbsent(error.getField(), error.getDefaultMessage()));

        ProblemDetail problem = problem(
                HttpStatus.BAD_REQUEST, "Validation failed","One or more fields are invalid");
        problem.setProperty("errors", errors);

        return ResponseEntity.badRequest().body(problem);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if(body instanceof ProblemDetail){
            ((ProblemDetail) body).setProperty("timeStamp", Instant.now());
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    private ProblemDetail problem(HttpStatus status, String title, String detail){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status,detail);
        problemDetail.setTitle(title);
        problemDetail.setProperty("timeStamp", Instant.now());
        return problemDetail;
    }
}
