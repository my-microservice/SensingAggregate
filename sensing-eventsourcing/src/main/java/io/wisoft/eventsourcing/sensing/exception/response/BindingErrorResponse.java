package io.wisoft.eventsourcing.sensing.exception.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Setter
@Getter
public class BindingErrorResponse {

    private String title;
    private String message;
    private List<FieldErrorInfo> errors;

    public void setErrors(final List<FieldError> errors) {
        this.errors = errors
                .stream()
                .map(FieldErrorInfo::new)
                .collect(Collectors.toList());
    }

    @Getter
    @NoArgsConstructor
    static class FieldErrorInfo {

        private String field;
        private String message;

        private FieldErrorInfo(final FieldError fieldError) {
            this.field = fieldError.getField();
            this.message = fieldError.getDefaultMessage();
        }
    }

}
