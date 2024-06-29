package delivery.api;

import delivery.dto.OrderDto;
import delivery.dto.OrderNonPrimitiveDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import delivery.utils.ApiClient;

import static delivery.spec.Specifications.getAuthenticatedRequestSpecification;
import static delivery.utils.ApiClient.authorizeAndGetToken;
import static delivery.utils.ApiClient.deleteOrders;

public class OrderTest extends BaseSetupApi {

    //HOMEWORK 14

    @Test
    void deleteOrderAndCheckStatus() {
        Response responseCreateOrder = ApiClient.createOrderAndCheckResponse(getAuthenticatedRequestSpecification(bearerToken));
        String orderId= responseCreateOrder.getBody().jsonPath().getString("id");
        ApiClient.deleteOrders(getAuthenticatedRequestSpecification(bearerToken),orderId);

        Response response = ApiClient.getOrderById(getAuthenticatedRequestSpecification(bearerToken), orderId);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        softly.assertThat(response.getBody().asString()).isEmpty();
    }

    //LESSON 15

    //GET method - get all orders as array
    @Test
    void getOrderAndCheckResponse() {
        Response response = ApiClient.getOrders(getAuthenticatedRequestSpecification(bearerToken));
        OrderDto[] responseArray = ApiClient.getOrdersAsArray(getAuthenticatedRequestSpecification(bearerToken));

        System.out.println("All existing orders: ");
        for (int loopIndex = 0; loopIndex < responseArray.length; loopIndex++) {
            System.out.println("Iteration N " + loopIndex);
            System.out.println("Order id: " + responseArray[loopIndex].id);
        }

    //DETELE method - delete all orders as array
        System.out.println("Delete all existing orders ");
        for (int loopIndex = 0; loopIndex < responseArray.length; loopIndex++) {
            System.out.println("Iteration N " + loopIndex);
            System.out.println("Delete order with id" + responseArray[loopIndex].id);
            ApiClient.deleteOrders(getAuthenticatedRequestSpecification(bearerToken), String.valueOf(responseArray[loopIndex].id));
        }

    //POST method - create new orders
        ApiClient.createOrderAndCheckResponse(getAuthenticatedRequestSpecification(bearerToken));
        ApiClient.createOrderAndCheckResponse(getAuthenticatedRequestSpecification(bearerToken));
        ApiClient.createOrderAndCheckResponse(getAuthenticatedRequestSpecification(bearerToken));
        ApiClient.createOrderAndCheckResponse(getAuthenticatedRequestSpecification(bearerToken));


    //GET method - get new orders as array
        OrderDto[] newOrdersCheckAsArray = ApiClient.getOrdersAsArray(getAuthenticatedRequestSpecification(bearerToken));
        softly.assertThat(newOrdersCheckAsArray.length).isEqualTo(4);
    }

    //POST method
    @Test
    void createOrderAndCheckResponse() {

        Response response = ApiClient.createOrderAndCheckResponse(getAuthenticatedRequestSpecification(bearerToken));
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        softly.assertThat(response.getContentType()).isEqualTo(ContentType.JSON.toString());
        softly.assertThat(response.getBody().jsonPath().getString("id")).isNotNull();
        softly.assertThat(response.getBody().jsonPath().getString("status")).isEqualTo("OPEN");
        softly.assertThat(response.getBody().jsonPath().getString("customerPhone")).isNotBlank();
        softly.assertThat(response.getBody().jsonPath().getString("customerName")).isNotEmpty();
    }
}











