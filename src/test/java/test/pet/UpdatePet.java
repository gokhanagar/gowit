package test.pet;

import baseUrl.PetStoreBaseUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.pet.Pet;

import java.io.File;
import java.io.IOException;

public class UpdatePet extends PetStoreBaseUrl {

    @Test()
    public void updatePet() throws IOException {

        Faker faker = new Faker();

        // Generate updated values
        String updatedName = faker.name().firstName();
        String updatedStatus = "available";

        // Read the pet from createdPet.json
        ObjectMapper mapper = new ObjectMapper();
        Pet pet = mapper.readValue(new File("src/test/java/data/createdPet.json"), Pet.class);

        // Update pet details
        pet.setName(updatedName);
        pet.setStatus(updatedStatus);

        // PUT request to update pet
        Response response = request.body(pet).when().put("/v2/pet");

        // Do assertions
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("id"), pet.getId());
        Assert.assertEquals(response.jsonPath().get("name"), pet.getName());
        Assert.assertEquals(response.jsonPath().get("status"), pet.getStatus());

        // Update pet in createdPet.json
        mapper.writeValue(new File("src/test/java/data/createdPet.json"), pet);

        // GET request to get pet by ID
        response = request.when().get("/v2/pet/" + pet.getId());

        // Do assertions
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("id"), pet.getId());
        Assert.assertEquals(response.jsonPath().get("name"), pet.getName());
        Assert.assertEquals(response.jsonPath().get("status"), pet.getStatus());

    }


}