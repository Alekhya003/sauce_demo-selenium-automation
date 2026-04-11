package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation for marking test categories/tags
 * Allows selective test execution based on category
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Category {
    String[] value() default {};
    
    // Pre-defined categories
    interface Categories {
        String SMOKE = "smoke";           // Quick validation tests
        String REGRESSION = "regression"; // Full feature tests
        String SANITY = "sanity";         // Basic functionality
        String E2E = "e2e";              // End-to-end tests
        String PERFORMANCE = "performance"; // Performance tests
        String SECURITY = "security";     // Security tests
    }
}
