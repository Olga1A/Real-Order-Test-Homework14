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

public class OrderTest extends BaseSetupApi {

    @Test
    void getOrderInformationAndCheckResponse() {
        Response response = ApiClient.getOrders(getAuthenticatedRequestSpecification(bearerToken) );

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        softly.assertThat(response.getContentType()).isEqualTo(ContentType.JSON.toString());
    }

    @Test
    void createOrderAndCheckResponse() {
        Response response = ApiClient.createOrder(getAuthenticatedRequestSpecification(bearerToken) );

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        softly.assertThat(response.getContentType()).isEqualTo(ContentType.JSON.toString());
        softly.assertThat(response.getBody().jsonPath().getString("status")).isEqualTo("OPEN");
        softly.assertThat(response.getBody().jsonPath().getString("id")).isNotEmpty();
        softly.assertThat(response.getBody().jsonPath().getString("customerName")).isNotEmpty();
    }

    @Test
    void getOrderById() {
        Response response = ApiClient.createOrder(getAuthenticatedRequestSpecification(bearerToken) );

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        softly.assertThat(response.getContentType()).isEqualTo(ContentType.JSON.toString());
        softly.assertThat(response.getBody().jsonPath().getString("status")).isEqualTo("OPEN");
        softly.assertThat(response.getBody().jsonPath().getString("id"));
    }

    @Test
    void deleteOrderAndCheckStatusCode() {
        Response responseCreatedOrder = ApiClient.createOrder(getAuthenticatedRequestSpecification(bearerToken) );
        String orderId = responseCreatedOrder.getBody().jsonPath().getString("id");
        ApiClient.deleteOrder(getAuthenticatedRequestSpecification(bearerToken), orderId);

        Response response = ApiClient.getOrderById(getAuthenticatedRequestSpecification(bearerToken), orderId);

        softly.assertThat(response.getBody().jsonPath().getString("id").isEmpty());
        softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }
}
