package delivery.utils;

import com.google.gson.Gson;
import delivery.api.BaseSetupApi;
import delivery.dto.LoginDto;
import delivery.dto.OrderDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.reset;

public class ApiClient extends BaseSetupApi {

    public static String authorizeAndGetToken(String username, String password){

        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .body( new Gson().toJson( new LoginDto(username,password) ) )
                .post("login/student" )
                .then()
                .log()
                .all()
                .extract()
                .response()
                .asString();
    }

    public static Response createOrder(RequestSpecification spec){

        Gson gson = new Gson();
        OrderDto requestOrder = new OrderDto("Ivan", "58575554", "Ring my doorbell");

        return given()
                .spec(spec)
                .log()
                .all()
                .body(gson.toJson(requestOrder))
                .post( "orders")
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    public static Response getOrders(RequestSpecification spec){

        return given()
                .spec(spec)
                .log()
                .all()
                .get( "orders")
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    public static Response getOrderById(RequestSpecification spec, String orderId){

        return given()
                .spec(spec)
                .log()
                .all()
                .get("orders/" + orderId)
                .then()
                .log()
                .all()
                .extract()
                .response()
                .path("id");
    }

    public static void deleteOrder(RequestSpecification spec,String orderId){

        given()
                .spec(spec)
                .log()
                .all()
                .delete("orders/" + orderId)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);
    }



}