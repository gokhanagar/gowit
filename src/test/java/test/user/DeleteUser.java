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

public class DeleteUser extends PetStoreBaseUrl {

    @Test()
    public void deleteUserByUserName() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(new File("src/test/java/data/createdUser.json"), User.class);

        // Delete request to delete user by username
        Response response = request.when().delete("/v2/user/" + user.getUsername());

        // Do assertions
        Assert.assertEquals(response.getStatusCode(), 200);

        // GET request to get user by username
        response = request.when().get("/v2/user/" + user.getUsername());

        // Do assertions
        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertTrue(response.jsonPath().getString("message").contains("User not found"));

        // Put request to update by username
        Faker faker = new Faker();
        String updatedLastName = faker.name().lastName();
        user.setLastName(updatedLastName);
        response = request.body(user).when().put("/v2/user/" + user.getUsername());

        // Do assertions
        Assert.assertEquals(response.getStatusCode(), 404);

    }

    @Test
    public void deleteUserNegativeTest() throws IOException {

        String nonExistentUsername = "nonexistentUser123";

        // Delete request to delete user by username
        Response response = request.when().delete("/v2/user/" + nonExistentUsername);

        // Do assertions
        Assert.assertEquals(response.getStatusCode(), 404);

    }
}
