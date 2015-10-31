package applica.guards.admin.fields.renderers;

import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Applica (www.applicadoit.com)
 * User: bimbobruno
 * Date: 3/4/13
 * Time: 11:20 AM
 */
@Component
public class LibraryDemoSelectRenderer extends SelectFieldRenderer {

    @Override
    public List<SimpleItem> getItems() {
        return Arrays.asList(
                new SimpleItem("Option1", "1"),
                new SimpleItem("Option2", "2"),
                new SimpleItem("Option3", "3"),
                new SimpleItem("Option4", "4")
        );
    }


}
