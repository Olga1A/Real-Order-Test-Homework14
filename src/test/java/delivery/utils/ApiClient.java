package delivery.utils;

import com.google.gson.Gson;
import delivery.api.BaseSetupApi;
import delivery.dto.LoginDto;
import delivery.dto.OrderDto;
import delivery.dto.OrderNonPrimitiveDto;
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

    public static Response createOrderAndCheckResponse(RequestSpecification spec){

        Gson gson = new Gson();
        OrderDto requestOrder = new OrderDto();

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
                .path (orderId);
    }

    public static void deleteOrders(RequestSpecification spec,String orderId){

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

    //Lesson 15
    public static OrderNonPrimitiveDto[] getAllOrderAsArray(RequestSpecification authorizedSpecWithToken) {

        return given()
                .spec(authorizedSpecWithToken)
                .log()
                .all()
                .get()
                .then()
                .log()
                .all()
                .extract()
                .as(OrderNonPrimitiveDto[].class);
    }

    public static OrderDto[] getOrdersAsArray(RequestSpecification authorizedSpecWithToken){

        return given()
                .spec(authorizedSpecWithToken)
                .log()
                .all()
                .get("orders")
                .then()
                .log()
                .all()
                .extract()
                .as(OrderDto[].class);
    }

    public static void deleteOrder(RequestSpecification spec,String orderId){

        given()
                .spec(spec)
                .log()
                .all()
                .delete("orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);

    }

    public  Response createOrder(RequestSpecification spec){

        Gson gson = new Gson();
        OrderDto requestOrder = new OrderDto();
        OrderDataGenerator orderDtoRequest = new OrderDataGenerator();
        String comment = RandomStringUtils.randomAlphabetic(8);
        String customerName = RandomStringUtils.randomAlphabetic(5);
        String customerPhone = RandomStringUtils.randomNumeric(8);

        return given()
                .contentType(ContentType.JSON)
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

    public static OrderDto[] getNewOrdersAsArray(RequestSpecification authorizedSpecWithToken){

        return given()
                .spec(authorizedSpecWithToken)
                .log()
                .all()
                .get("orders")
                .then()
                .log()
                .all()
                .extract()
                .as(OrderDto[].class);
    }
    }

