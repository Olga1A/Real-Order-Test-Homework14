package delivery.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import delivery.utils.ApiClient;
import org.junit.jupiter.api.BeforeEach;

public class BaseSetupApi {
    private final static String PATH_TO_CONFIG = "application.yaml";
    protected static PropertiesConfiguration configuration;
    protected static String bearerToken;
    protected SoftAssertions softly;

    @BeforeAll
    public static void setUp() throws ConfigurationException {

        configuration = new PropertiesConfiguration();
        configuration.load(PATH_TO_CONFIG);
        RestAssured.baseURI = configuration.getString("base-url");
        String u = configuration.getString("username");
        String p = configuration.getString("password");
        bearerToken = ApiClient.authorizeAndGetToken(u, p);
    }

    @BeforeEach
    public void beforeTest() {
        softly = new SoftAssertions();
    }

    @AfterEach
    public void afterTest(){
        softly.assertAll();
    }

}
