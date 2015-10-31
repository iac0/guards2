package applica.guards.data.hibernate;

import applica.guards.domain.data.UsersRepository;
import applica.guards.domain.model.Filters;
import applica.guards.domain.model.UserDetails;
import applica.framework.builders.LoadRequestBuilder;
import applica.framework.security.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Applica (www.applicadoit.com)
 * User: bimbobruno
 * Date: 2/26/13
 * Time: 6:18 PM
 */
@Repository
public class UserDetailsHibernateRepository implements UserDetailsRepository {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails getByMail(String mail) {
        try {
            return usersRepository
                    .find(LoadRequestBuilder.build().eq(Filters.USER_MAIL, mail))
                    .findFirst()
                    .map(u -> new UserDetails(u))
                    .orElse(null);
        } catch(Throwable t) {
            t.printStackTrace();
        }

        return null;
    }
}
