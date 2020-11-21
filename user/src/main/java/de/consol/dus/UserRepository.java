package de.consol.dus;

/**
 * Repository verwaltet das User-Entity
 * @author promyx
 *
 */
@javax.enterprise.context.ApplicationScoped
public class UserRepository implements io.quarkus.hibernate.orm.panache.PanacheRepository<de.consol.dus.entity.User> {

    public java.util.Optional<de.consol.dus.entity.User> findByUsername(String username) {
        return find("LOWER(username)", username.toLowerCase()).firstResultOptional();
    }

    public java.util.Optional<de.consol.dus.entity.User> findByEmail(String email) {
        return find("LOWER(email)", email.toLowerCase()).firstResultOptional();
    }
}
