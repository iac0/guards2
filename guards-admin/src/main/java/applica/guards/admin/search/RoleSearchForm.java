package applica.guards.admin.search;

import applica.framework.widgets.annotations.Form;
import applica.framework.widgets.annotations.FormField;
import applica.framework.widgets.annotations.FormRenderer;
import applica.framework.widgets.annotations.SearchCriteria;
import applica.framework.Filter;
import applica.framework.IEntity;
import applica.framework.widgets.forms.renderers.SearchFormRenderer;

/**
 * Applica (www.applicadoit.com)
 * User: bimbobruno
 * Date: 3/4/13
 * Time: 4:18 PM
 */
@Form(RoleSearchForm.EID)
@FormRenderer(SearchFormRenderer.class)
public class RoleSearchForm extends IEntity {
    public static final String EID = "rolesearchform";

    @FormField(description = "label.name")
    @SearchCriteria(Filter.LIKE)
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
