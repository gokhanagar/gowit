package test.pet;

import baseUrl.PetStoreBaseUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.pet.Pet;

import java.io.File;
import java.io.IOException;

public class FindPet extends PetStoreBaseUrl {

    @Test()
    public void getPetById() throws IOException {

        // Read the pet from createdPet.json
        ObjectMapper mapper = new ObjectMapper();
        Pet pet = mapper.readValue(new File("src/test/java/data/createdPet.json"), Pet.class);

        // GET request to get pet by ID
        Response response = request.when().get("/v2/pet/" + pet.getId());


        // Do assertions
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("id"), pet.getId());
        Assert.assertEquals(response.jsonPath().get("name"), pet.getName());
        Assert.assertEquals(response.jsonPath().get("status"), pet.getStatus());
        Assert.assertEquals(response.jsonPath().getString("category.name"),pet.getCategory().getName());

        }

    @Test()
    public void getPetByIdNegativeTest() {

        int nonExistencePet = 789023422;

        // GET request to get pet by ID
        Response response = request.when().get("/v2/pet/" + nonExistencePet);
        System.out.println("get" + response.asString());

        // Do assertions
        Assert.assertEquals(response.getStatusCode(), 404);

    }


}