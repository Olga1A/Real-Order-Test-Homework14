package delivery.api;

import delivery.dto.OrderDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import delivery.utils.ApiClient;

import static delivery.spec.Specifications.getAuthenticatedRequestSpecification;
import static delivery.utils.ApiClient.deleteOrderById;

public class OrderTest extends BaseSetupApi {

    @Test
    void getOrderInformationAndCheckResponse() {
        Response response = ApiClient.getOrders(getAuthenticatedRequestSpecification(bearerToken));

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        softly.assertThat(response.getContentType()).isEqualTo(ContentType.JSON.toString());
    }

    @Test
    void createOrderAndCheckResponse() {
        Response response = ApiClient.createOrder(getAuthenticatedRequestSpecification(bearerToken));

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        softly.assertThat(response.getContentType()).isEqualTo(ContentType.JSON.toString());
        softly.assertThat(response.getBody().jsonPath().getString("status")).isEqualTo("OPEN");
        softly.assertThat(response.getBody().jsonPath().getString("id")).isNotEmpty();
        softly.assertThat(response.getBody().jsonPath().getString("customerName")).isNotEmpty();
    }

    @Test
    void getOrderById() {
        Response response = ApiClient.createOrder(getAuthenticatedRequestSpecification(bearerToken));

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        softly.assertThat(response.getContentType()).isEqualTo(ContentType.JSON.toString());
        softly.assertThat(response.getBody().jsonPath().getString("status")).isEqualTo("OPEN");
        softly.assertThat(response.getBody().jsonPath().getString("id"));
    }

    @Test
    void deleteOrderAndCheckStatusCode() {
        Response responseCreatedOrder = ApiClient.createOrder(getAuthenticatedRequestSpecification(bearerToken));
        String orderId = responseCreatedOrder.getBody().jsonPath().getString("id");
        ApiClient.deleteOrder(getAuthenticatedRequestSpecification(bearerToken), orderId);

        Response response = ApiClient.getOrderById(getAuthenticatedRequestSpecification(bearerToken), orderId);

        softly.assertThat(response.getBody().jsonPath().getString("id").isEmpty());
        softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }

    @Test void getOrdersInformationAndCheckResponse() {

        Response response = ApiClient.getOrders(getAuthenticatedRequestSpecification(bearerToken));
        OrderDto[] responseArray = ApiClient.getOrdersAsArray(getAuthenticatedRequestSpecification(bearerToken));

        System.out.println("All existing orders: ");
        for (int loopIndex = 0; loopIndex < responseArray.length; loopIndex++) {
            System.out.println("Iteration # " + loopIndex);
            System.out.println("Order id: " + responseArray[loopIndex].id);
        }

        //Deleting all existing orders
        System.out.println("Delete all orders ");
        for (int loopIndex = 0; loopIndex < responseArray.length; loopIndex++) {
            System.out.println("Iteration # " + loopIndex);
            System.out.println("Delete order with id" + responseArray[loopIndex].id);
            ApiClient.deleteOrderById(getAuthenticatedRequestSpecification(bearerToken), String.valueOf(responseArray[loopIndex].id));
        }

        //Creating new orders
        ApiClient.createOrder(getAuthenticatedRequestSpecification(bearerToken));
        ApiClient.createOrder(getAuthenticatedRequestSpecification(bearerToken));
        ApiClient.createOrder(getAuthenticatedRequestSpecification(bearerToken));

        //Calling all new orders
        OrderDto[] checkCreatedOrdersAfterArrayDeletion = ApiClient.getOrdersAsArray(getAuthenticatedRequestSpecification(bearerToken));
        softly.assertThat(checkCreatedOrdersAfterArrayDeletion.length).isEqualTo(3);

       // softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
       // softly.assertThat(response.getContentType()).isEqualTo(ContentType.JSON.toString());
    }
    }


