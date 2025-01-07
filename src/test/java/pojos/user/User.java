package pojos.user;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;
    public static Faker faker;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", userStatus=" + userStatus +
                '}';
    }


    public static User createFakeUser() {
        faker = new Faker();
        User user = new User();
        user.setId(faker.number().numberBetween(1, 1000));
        user.setUsername(faker.name().username());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setPhone(faker.phoneNumber().cellPhone());
        user.setUserStatus(faker.number().numberBetween(0, 1));
        return user;
    }

    public static List<User> generateInvalidUsers() {
        faker = new Faker();
        List<User> invalidUsers = new ArrayList<>();

        // 1. invalid iD
        invalidUsers.add(createInvalidUser(faker, id -> id.setId(-1)));

        // 2. empty username
        invalidUsers.add(createInvalidUser(faker, user -> user.setUsername("")));

        // 3. invalid email
        invalidUsers.add(createInvalidUser(faker, user -> user.setEmail("invalid-email")));

        // 4. empty password
        invalidUsers.add(createInvalidUser(faker, user -> user.setPassword("")));

        // 5. empty phone number
        invalidUsers.add(createInvalidUser(faker, user -> user.setPhone("")));

        return invalidUsers;
    }


    private static User createInvalidUser(Faker faker, java.util.function.Consumer<User> invalidator) {
        User user = createFakeUser();

        invalidator.accept(user);
        return user;
    }




}
