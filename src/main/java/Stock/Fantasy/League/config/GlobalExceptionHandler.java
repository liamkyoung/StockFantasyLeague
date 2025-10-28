package Stock.Fantasy.League.config;


import Stock.Fantasy.League.auth.EmailAlreadyExistsException;
import Stock.Fantasy.League.league.exception.LeagueFullException;
import Stock.Fantasy.League.league.exception.LeagueNotFoundException;
import Stock.Fantasy.League.league.exception.UserAlreadyInLeagueException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle email uniqueness / data integrity errors
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", "Invalid input");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Handle invalid arguments (e.g. bad UUID in URL)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Invalid Parameter");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // IllegalArgumentException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        Map<String, Object> body = Map.of(
                "timestamp", Instant.now(),
                "status", 400,
                "errors", errors
        );

        return ResponseEntity.badRequest().body(body);
    }

    // Catch-all fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEmailExists(EmailAlreadyExistsException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LeagueNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleLeagueNotFound(LeagueNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "timestamp", Instant.now(),
                "status", 404,
                "error", "League Not Found",
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(LeagueFullException.class)
    public ResponseEntity<Map<String, Object>> handleLeagueFull(LeagueFullException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "timestamp", Instant.now(),
                "status", 409,
                "error", "League Full",
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(UserAlreadyInLeagueException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyJoined(UserAlreadyInLeagueException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "timestamp", Instant.now(),
                "status", 409,
                "error", "Already Joined",
                "message", ex.getMessage()
        ));
    }
}