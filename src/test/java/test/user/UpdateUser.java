package test.user;

import baseUrl.PetStoreBaseUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.user.User;

import java.io.File;
import java.io.IOException;

public class UpdateUser extends PetStoreBaseUrl {

    @Test()
    public void updateUserByUsername() throws IOException {

        Faker faker = new Faker();
        String updatedUsername = faker.name().username();
        String updatedFirstName = faker.name().firstName();
        String updatedLastName = faker.name().lastName();
        String updatedEmail = faker.internet().emailAddress();
        String updatedPassword = faker.internet().password();
        String updatedPhone = faker.phoneNumber().phoneNumber();

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(new File("src/test/java/data/createdUser.json"), User.class);

        user.setUsername(updatedUsername);
        user.setFirstName(updatedFirstName);
        user.setLastName(updatedLastName);
        user.setEmail(updatedEmail);
        user.setPassword(updatedPassword);
        user.setPhone(updatedPhone);

        // PUT request to update user by username
        Response response = request.body(user).when().put("/v2/user/" + user.getUsername());


        // Do assertions
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("message"), user.getId());

        // Update user in createdUser.json
        mapper.writeValue(new File("src/test/java/data/createdUser.json"), user);

        // GET request to get user by username
        response = request.when().get("/v2/user/" + user.getUsername());

        // Do assertions
        Assert.assertEquals(response.jsonPath().getInt("id"), user.getId());
        Assert.assertEquals(response.jsonPath().get("username"), user.getUsername());
        Assert.assertEquals(response.jsonPath().get("firstName"), user.getFirstName());
        Assert.assertEquals(response.jsonPath().get("email"), user.getEmail());
        Assert.assertEquals(response.jsonPath().get("lastName"), user.getLastName());

    }
}