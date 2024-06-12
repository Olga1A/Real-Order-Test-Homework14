package delivery.spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Specifications {

    public static RequestSpecification getAuthenticatedRequestSpecification(String bearerToken){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType(ContentType.JSON);
        builder.addHeader("Authorization", "Bearer " + bearerToken);
        return builder.build();
    }
}
