package com.fullcycle.admin.catalogo.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler aHandler);

    public interface Validation {
        void validate();
    }
    ValidationHandler validate(Validation aValidation);

    List<Error> getErrors();
    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError() {
        if (hasError()) {
            return getErrors().get(0);
        } else {
            return null;
        }
    }
}
