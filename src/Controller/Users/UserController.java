package Controller.Users;

import Model.User.Role;
import Model.User.UserEntity;
import Model.User.UserRepository;
import java.util.List;
public class UserController {
    private UserRepository userRepository;
    private UserEntity userEntity;

    public UserController(UserRepository userRepository, UserEntity userEntity) {
        this.userRepository = userRepository;
        this.userEntity = userEntity;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.getAllUsersAsObjects();
    }

    public void createUser(double salary, String name, String role) {
        Role parsedRole = Role.fromString(role);
        this.userEntity.setAttributes(salary, name, parsedRole);
        var user = userRepository.create(this.userEntity);
        System.out.println(user);
    }

    public void readUser(int id) {
        String userEntity = userRepository.read(id);
        System.out.println(userEntity);
    }

    public void updateUser(int id, String newJsonRecord) {
        userRepository.update(id, newJsonRecord);
    }

    public void deleteUser(int id) {
        userRepository.delete(id);
    }
}
