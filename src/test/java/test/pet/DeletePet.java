package test.pet;

import baseUrl.PetStoreBaseUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.pet.Pet;
import java.io.File;
import java.io.IOException;

public class DeletePet extends PetStoreBaseUrl {

    @Test()
    public void deletePet() throws IOException {

        // Read the pet from createdPet.json
        ObjectMapper mapper = new ObjectMapper();
        Pet pet = mapper.readValue(new File("src/test/java/data/createdPet.json"), Pet.class);

        // Delete request to delete pet by pedId
        Response response = request.when().delete("/v2/pet/" + pet.getId());

        // Do assertions
        Assert.assertEquals(response.getStatusCode(), 200);

        // GET request to get pet by ID
        response = request.when().get("/v2/pet/" + pet.getId());

        // Do assertions
        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertTrue(response.jsonPath().getString("message").contains("Pet not found"));

        // PUT request to update pet
        String updatedStatus = "sold";
        pet.setStatus(updatedStatus);
        response = request.body(pet).when().put("/v2/pet");

        // Do assertions
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void deletePetNegativeTest(){

        int nonExistentPetName = 789123789;

        // Delete request to delete user by username
        Response response = request.when().delete("/v2/pet/" + nonExistentPetName);

        // Do assertions
        Assert.assertEquals(response.getStatusCode(), 404);

    }

}