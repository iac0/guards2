package applica.guards.admin.facade;

import applica.guards.domain.data.UsersRepository;
import applica.guards.domain.model.Role;
import applica.guards.domain.model.User;
import applica.framework.library.utils.ProgramException;
import applica.framework.security.authorization.Permissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Applica (www.applicadoit.com)
 * User: bimbobruno
 * Date: 03/02/14
 * Time: 17:29
 */
@Component
public class SetupFacade {

    @Autowired
    private UsersRepository usersRepository;

    public void createAdmin(boolean check) {

        if(check) {
            if(usersRepository.find(null).getTotalRows() > 0) {
                throw new ProgramException("Admin user already created");
            }
        }

        String encodedPassword = new Md5PasswordEncoder().encodePassword("applica", null);

        User user = new User();
        user.setMail("admin@applicadoit.com");
        user.setPassword(encodedPassword);
        user.setUsername("administrator");
        user.setActive(true);

        Role role = new Role();
        role.setRole(Role.ADMIN);
        role.setPermissions(Permissions.instance().allPermissions());

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        usersRepository.save(user);
    }

}
