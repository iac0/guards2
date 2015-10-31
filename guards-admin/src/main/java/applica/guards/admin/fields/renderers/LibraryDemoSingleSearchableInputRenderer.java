package applica.guards.admin.fields.renderers;

import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.SingleSearchableInputFieldRenderer;
import org.springframework.stereotype.Component;

/**
 * Applica (www.applicadoit.com)
 * User: bimbobruno
 * Date: 3/4/13
 * Time: 11:23 AM
 */
@Component
public class LibraryDemoSingleSearchableInputRenderer extends SingleSearchableInputFieldRenderer {

    @Override
    public String getLabel(FormField field, Object value) {
        return String.valueOf(value);
    }

    @Override
    public String getServiceUrl() {
        return "values/roles";
    }

}
