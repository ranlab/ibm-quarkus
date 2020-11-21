package de.consol.dus;

/**
 * Control des Users.
 * Interessant:<ul>
 * <li>Mapping in Zeile 27 über UserMapper</li>
 * <li>ColorRestClient</li>
 * </ul>
 * 
 * @author promyx
 */
@javax.enterprise.context.ApplicationScoped
public class UserService {

    private final UserRepository userRepository;
    private final de.consol.dus.entity.UserMapper userMapper;
    private final de.consol.dus.boundary.color.ColorRestClient colorRestClient;

    public UserService(UserRepository userRepository, de.consol.dus.entity.UserMapper userMapper,
        @org.eclipse.microprofile.rest.client.inject.RestClient de.consol.dus.boundary.color.ColorRestClient colorRestClient) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.colorRestClient = colorRestClient;
    }

    public de.consol.dus.boundary.response.UserResponse getUserByUsername(String username) {
        return userRepository
            .findByUsername(username)
                .map(userMapper::entityToResponse)
                .orElseThrow(() -> new de.consol.dus.exception.NoSuchEntityException(
                    String.format("User with username %s does not exist", username)));
    }

    @javax.transaction.Transactional
    public de.consol.dus.boundary.response.UserResponse createUser(de.consol.dus.boundary.request.CreateUserRequest request) {
        final String username = request.getUsername();
        if (userRepository.findByUsername(username).isPresent()) {
            throw new de.consol.dus.exception.EntityAlreadyExistsException(String.format("User with username %s already exists", username));
        }
        final String email = request.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new de.consol.dus.exception.EntityAlreadyExistsException(String.format("User with email %s already exists", email));
        }

        final String favoriteColorName = request.getFavoriteColor();
        try {
            colorRestClient.getColorByName(favoriteColorName);
        } catch (javax.ws.rs.WebApplicationException e) {
            throw new de.consol.dus.exception.NoSuchEntityException(String.format("Color with name %s does not exist", favoriteColorName));
        }
        final de.consol.dus.entity.User toCreate = userMapper.requestToUser(request);
        userRepository.persist(toCreate);
        return userMapper.entityToResponse(toCreate);
    }
}
