package de.hsrm.mi.web.projekt.validierung;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = SiebzehnValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Siebzehnhaft {
    
    String message() default "Die Sichtung muss eine 17 geschrieben oder als Zahl enthalten!"; 

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default{ };
}
