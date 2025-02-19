package com.app.utility.annotations;

import jakarta.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "Password must be 8-20 characters long and include uppercase, lowercase, digit, and special character.";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

    // Define your password validation criteria here.  This is an example.
    // Customize as needed.
//    String regexp() default "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"; //Example

    // Or, if you prefer, you can provide individual criteria messages:
    // String lengthMessage() default "Password must be at least 8 characters long.";
    // String uppercaseMessage() default "Password must contain at least one uppercase letter.";
    // String lowercaseMessage() default "Password must contain at least one lowercase letter.";
    // String numberMessage() default "Password must contain at least one number.";
    // String specialCharacterMessage() default "Password must contain at least one special character (@#$%^&+=).";
    // String whitespaceMessage() default "Password must not contain whitespace.";

}
