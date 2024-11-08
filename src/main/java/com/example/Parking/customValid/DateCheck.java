package com.example.Parking.customValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CustomDateCheck.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateCheck {
    String message() default "Arrival date must be before departure date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
