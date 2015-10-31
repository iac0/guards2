package applica.guards.admin;

import applica.framework.LoadRequest;
import applica.framework.widgets.fields.renderers.*;
import applica.guards.admin.data.UsersRepositoryWrapper;
import applica.guards.admin.fields.renderers.PermissionsFieldRenderer;
import applica.guards.admin.fields.renderers.RolesFieldRenderer;
import applica.guards.admin.fields.renderers.UserImageFieldRenderer;
import applica.guards.admin.search.RoleSearchForm;
import applica.guards.admin.search.MailSearchForm;
import applica.guards.domain.data.GuardRepository;
import applica.guards.domain.data.PlacesRepository;
import applica.guards.domain.data.RolesRepository;
import applica.guards.domain.data.UsersRepository;
import applica.guards.domain.model.Guard;
import applica.guards.domain.model.Place;
import applica.guards.domain.model.Role;
import applica.guards.domain.model.User;
import applica.framework.library.utils.NullableDateConverter;
import applica.framework.widgets.CrudConfiguration;
import applica.framework.widgets.CrudConstants;
import applica.framework.widgets.CrudFactory;
import applica.framework.widgets.Grid;
import applica.framework.widgets.builders.FormConfigurator;
import applica.framework.widgets.builders.GridConfigurator;
import applica.framework.widgets.cells.renderers.DefaultCellRenderer;
import applica.framework.widgets.acl.CrudPermission;
import applica.framework.widgets.acl.CrudSecurityConfigurer;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.forms.processors.DefaultFormProcessor;
import applica.framework.widgets.forms.renderers.DefaultFormRenderer;
import applica.framework.widgets.grids.renderers.DefaultGridRenderer;
import applica.framework.security.authorization.Permissions;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;

/**
 * Applica (www.applicadoit.com)
 * User: bimbobruno
 * Date: 2/21/13
 * Time: 3:37 PM
 */
public class Bootstrapper {

    private Log logger = LogFactory.getLog(getClass());

    @Autowired
    private CrudFactory crudFactory;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private GuardRepository guardRepository;

    @Autowired
    private PlacesRepository placesRepository;

    public void init() {
        logger.info("Applica Framework app started");

        NullableDateConverter dateConverter = new NullableDateConverter();
        dateConverter.setPatterns(new String[] { "dd/MM/yyyy HH:mm", "MM/dd/yyyy HH:mm", "yyyy-MM-dd HH:mm", "dd/MM/yyyy", "MM/dd/yyyy", "yyyy-MM-dd", "HH:mm" });
        ConvertUtils.register(dateConverter, Date.class);

        CrudConfiguration.instance().setCrudFactory(crudFactory);

        Package pack = Bootstrapper.class.getPackage();
        try {
            CrudConfiguration.instance().scan(pack);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error scanning crud configuration: " + e.getMessage());
        }

        CrudConfiguration.instance().registerGridRenderer(CrudConstants.DEFAULT_ENTITY_TYPE, DefaultGridRenderer.class);
        CrudConfiguration.instance().registerFormRenderer(CrudConstants.DEFAULT_ENTITY_TYPE, DefaultFormRenderer.class);
        CrudConfiguration.instance().registerFormFieldRenderer(CrudConstants.DEFAULT_ENTITY_TYPE, "", DefaultFieldRenderer.class);
        CrudConfiguration.instance().registerFormProcessor(CrudConstants.DEFAULT_ENTITY_TYPE, DefaultFormProcessor.class);
        CrudConfiguration.instance().registerCellRenderer(CrudConstants.DEFAULT_ENTITY_TYPE, "", DefaultCellRenderer.class);

        CrudConfiguration.instance().setParam(CrudConfiguration.DEFAULT_ENTITY_TYPE, Grid.PARAM_ROWS_PER_PAGE, "20");

        registerGrids();
        registerForms();
        configureCrudSecurity();

      //  testData();

    }

    private void testData(){
        for (int i = 0; i < 70; i++ ){
            Guard guard = new Guard();
            guard.setName(String.format("%s %d","Nome",i));
            guard.setSurname(String.format("%s %d", "Cognome", i));
            guardRepository.save(guard);
            Place place = new Place();
            place.setName(String.format("%s %d","Posto",i));
            place.setAddress("Via nazionale");
            place.setDateOfGuarding(new Date());
            placesRepository.save(place);
        }
    }

    private void configureCrudSecurity() {
        Permissions.instance().registerStatic("users:new");
        Permissions.instance().registerStatic("users:list");
        Permissions.instance().registerStatic("users:save");
        Permissions.instance().registerStatic("users:edit");
        Permissions.instance().registerStatic("users:delete");

        CrudSecurityConfigurer.instance().configure("user", CrudPermission.NEW, "users:new");
        CrudSecurityConfigurer.instance().configure("user", CrudPermission.LIST, "users:list");
        CrudSecurityConfigurer.instance().configure("user", CrudPermission.SAVE, "users:save");
        CrudSecurityConfigurer.instance().configure("user", CrudPermission.EDIT, "users:edit");
        CrudSecurityConfigurer.instance().configure("user", CrudPermission.DELETE, "users:delete");

        Permissions.instance().registerStatic("roles:new");
        Permissions.instance().registerStatic("roles:list");
        Permissions.instance().registerStatic("roles:save");
        Permissions.instance().registerStatic("roles:edit");
        Permissions.instance().registerStatic("roles:delete");

        CrudSecurityConfigurer.instance().configure("role", CrudPermission.NEW, "roles:new");
        CrudSecurityConfigurer.instance().configure("role", CrudPermission.LIST, "roles:list");
        CrudSecurityConfigurer.instance().configure("role", CrudPermission.SAVE, "roles:save");
        CrudSecurityConfigurer.instance().configure("role", CrudPermission.EDIT, "roles:edit");
        CrudSecurityConfigurer.instance().configure("role", CrudPermission.DELETE, "roles:delete");
    }

    private void registerForms() {
        FormConfigurator.configure(User.class, "user")
                .repository(UsersRepositoryWrapper.class)
                .tab("label.user_info")
                .fieldSet("label.account")
                .field("mail", "label.mail", MailFieldRenderer.class).param("mail", Params.PLACEHOLDER, "mail@example.com")
                .field("password", "label.password", PasswordFieldRenderer.class).param("password", Params.PLACEHOLDER, "msg.leave_blank_password")
                .field("active", "label.active")
                .fieldSet("label.profile")
                .field("registrationDate", "label.registration_date", DatePickerRenderer.class)
                .field("image", "label.image", UserImageFieldRenderer.class)
                .fieldSet("label.roles")
                .field("roles", null, RolesFieldRenderer.class);

        FormConfigurator.configure(Role.class, "role")
                .repository(RolesRepository.class)
                .field("role", "label.name")
                .field("permissions", "label.permissions", PermissionsFieldRenderer.class);

        FormConfigurator.configure(Guard.class,"guard")
                .repository(GuardRepository.class)
                .field("name","label.guard.name")
                .field("surname","label.guard.surname");

        FormConfigurator.configure(Place.class,"place")
                .repository(PlacesRepository.class)
                .field("name","label.place.name")
                .field("address","label.place.address")
                .field("dateOfGuarding","label.place.dateOfGuarding", TimePickerRenderer.class)
                .field("finishOfGuarding","label.place.finishOfGuarding", TimePickerRenderer.class);

    }

    private void registerGrids() {
        GridConfigurator.configure(User.class, "user")
                .repository(UsersRepository.class)
                .searchForm(MailSearchForm.class)
                .column("mail", "label.mail", true)
                .column("active", "label.active", false);

        GridConfigurator.configure(Role.class, "role")
                .repository(RolesRepository.class)
                .searchForm(RoleSearchForm.class)
                .column("role", "label.name", true);

        GridConfigurator.configure(Guard.class,"guard")
                .repository(GuardRepository.class)
                .column("name","label.guard.name",true)
                .column("surname","label.guard.surname",true);

        GridConfigurator.configure(Place.class,"place")
                .repository(PlacesRepository.class)
                .column("name","label.place.name",true)
                .column("address","label.place.address",false)
                .column("timeOfGuarding","label.place.dateOfGuarding",false)
                .column("finishOfGuarding", "label.place.finishOfGuarding", false);
    }
}
