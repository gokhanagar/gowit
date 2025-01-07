package test.user;

import baseUrl.PetStoreBaseUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.user.User;


import java.io.File;
import java.io.IOException;

public class GetUserByUserName extends PetStoreBaseUrl {

    @Test()
    public void getUserName() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(new File("src/test/java/data/createdUser.json"), User.class);

        // GET request to get user by username
        Response response = request.when().get("/v2/user/" + user.getUsername());


        // Do assertions
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("id"), user.getId());
        Assert.assertEquals(response.jsonPath().get("username"), user.getUsername());
        Assert.assertEquals(response.jsonPath().get("firstName"), user.getFirstName());
        Assert.assertEquals(response.jsonPath().get("email"), user.getEmail());
        Assert.assertEquals(response.jsonPath().get("lastName"), user.getLastName());
        Assert.assertEquals((Integer) response.jsonPath().get("userStatus"), user.getUserStatus());

    }

    @Test
    public void getUserNameNegativeTest(){

        String nonExistentUsername = "nonexistentUser123";

        // GET request to get user by username
        Response response = request.when().get("/v2/user/" + nonExistentUsername);

        // Do assertions
        Assert.assertEquals(response.getStatusCode(), 404);

        String errorMessage = response.jsonPath().getString("message");
        Assert.assertTrue(errorMessage.contains("User not found"));
    }


}