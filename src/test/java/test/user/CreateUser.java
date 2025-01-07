package test.user;

import baseUrl.PetStoreBaseUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.user.User;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static pojos.user.User.generateInvalidUsers;

public class CreateUser extends PetStoreBaseUrl {


    @Test()
    public void createUser() throws IOException {

        User user = User.createFakeUser();

        System.out.println("Generated User: " + user);

        // POST request to create the user
        Response response = request
                .body(user)
                .post("/v2/user");


        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/java/data/createdUser.json"), user);


        //Do assertions
        Assert.assertEquals(response.getStatusCode(), 200);

        // user logged in the system
        response = request
                .queryParam("username", user.getUsername())
                .queryParam("password", user.getPassword())
                .header("accept", "application/json")
                .when()
                .get("/v2/user/login");

        //Do assertions
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getString("message").contains("logged in user session"));

    }

    @Test
    public void createUserWithInvalidData() {
        List<User> invalidUsers = generateInvalidUsers();

        for (User user : invalidUsers) {
            Response response = request
                    .body(user)
                    .post("/v2/user");

            Assert.assertEquals(response.getStatusCode(), 400);
        }
    }

}