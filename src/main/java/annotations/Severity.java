package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation for marking test severity
 * Useful for filtering and prioritizing tests
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Severity {
    SeverityLevel value() default SeverityLevel.NORMAL;
    
    enum SeverityLevel {
        CRITICAL,    // Highest priority - core functionality
        HIGH,        // Important features
        NORMAL,      // Standard features
        LOW,         // Nice to have features
        TRIVIAL      // Cosmetic issues
    }
}
