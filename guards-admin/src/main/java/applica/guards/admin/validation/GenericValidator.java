package applica.guards.admin.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Applica (www.applicadoit.com)
 * User: bimbobruno
 * Date: 20/02/14
 * Time: 13:25
 */
@Component("validator-params_test")
public class GenericValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
