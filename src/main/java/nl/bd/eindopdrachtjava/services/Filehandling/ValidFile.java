//package nl.bd.eindopdrachtjava.services.Filehandling;
//
//import javax.validation.Constraint;
//import javax.validation.Payload;
//import java.lang.annotation.*;
//
//@Documented
//@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE_USE})
//@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = {FileValidator.class})
//public @interface ValidFile {
//    String message() default "Only PNG, JPEG or GIFF images are allowed";
//
//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
//}