package applica.guards.admin.fields.renderers;

import applica.framework.widgets.FormField;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.MultiSearchableInputFieldRenderer;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Applica (www.applicadoit.com)
 * User: bimbobruno
 * Date: 3/4/13
 * Time: 11:23 AM
 */
@Component
public class LibraryDemoMultiSearchableInputRenderer extends MultiSearchableInputFieldRenderer {

    @Override
    public String getServiceUrl() {
        return "values/roles";
    }

    @Override
    public List<SimpleItem> getSelectedItems(FormField field, Object value) {
        return Arrays.asList(new SimpleItem("Amministratore", "Amministratore"));
    }

}
