package applica.guards.admin.controllers;

import applica.guards.domain.data.RolesRepository;
import applica.guards.domain.model.Role;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.library.responses.ValueResponse;
import applica.framework.security.authorization.Permissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Applica (www.applicadoit.com)
 * User: bimbobruno
 * Date: 3/3/13
 * Time: 11:11 PM
 */
@Controller
@RequestMapping("/values")
public class ValuesController {

    @Autowired
    RolesRepository rolesRepository;

    @RequestMapping("/roles")
    public @ResponseBody ValueResponse roles(String keyword) {
        List<Role> roles = rolesRepository.find(
                LoadRequest.build()
                        .like("role", keyword)
        ).getRows();

        return new ValueResponse(SimpleItem.createList(roles, "role", "id"));
    }

    @RequestMapping("/permissions")
    public @ResponseBody ValueResponse permissions(String keyword) {
        return new ValueResponse(
            SimpleItem.createList(Permissions.instance().allPermissions(), (p) -> (String) p, (p) -> (String) p)
        );
    }

}
