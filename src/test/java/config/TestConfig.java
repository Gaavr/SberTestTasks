package config;

import static constants.Constants.API_URL;
import static constants.Constants.API_USERS_PATH;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class TestConfig {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = API_URL;
        RestAssured.basePath = API_USERS_PATH;
    }
}
