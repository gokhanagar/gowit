package baseUrl;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;

public class PetStoreBaseUrl {

    protected RequestSpecification request;

    @BeforeMethod
    public void setUp(){

        RestAssured.baseURI = "https://petstore.swagger.io";
        request = RestAssured.given().contentType("application/json");

    }

}