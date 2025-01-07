package test.pet;

import baseUrl.PetStoreBaseUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.pet.Pet;

import java.io.File;
import java.io.IOException;


public class CreatePet extends PetStoreBaseUrl {
    private Pet pet;
    private Response response;


    @Story("User will be able to create pet")
    @Severity(SeverityLevel.BLOCKER)
    @Description("User creates pet")
    @Test()
    public void createPet() throws IOException {

        Allure.step("Creates a fake pet",() -> {

            pet = Pet.createFakePet();
        });

        Allure.step("POST request to create pet",() -> {
             response = request
                    .body(pet)
                    .post("/v2/pet");
        });

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/java/data/createdPet.json"), pet);

        Allure.step("Do assertions to validate",() -> {
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.jsonPath().getLong("id"), pet.getId());
            Assert.assertEquals(response.jsonPath().getString("category.name"),pet.getCategory().getName());
            Assert.assertEquals(response.jsonPath().getString("name"),pet.getName());
        });

    }

    @Test()
    public void createPetNegativeTest(){

        // POST request to create the pet
        Response response = RestAssured.given()
                .header("Content-Type", "text/plain")
                .body("")
                .post("/v2/pet");

        //Do assertions
        Assert.assertEquals(response.getStatusCode(), 415, "Expected HTTP status code 415");

    }
}