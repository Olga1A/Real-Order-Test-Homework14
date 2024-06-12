package delivery.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class OrderDataGenerator {

    public static String generateRandomCustomerName() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static String generateRandomCustomerPhone() {
        return RandomStringUtils.randomNumeric(9);
    }

    public static String generateRandomCustomerComments() {
        return RandomStringUtils.randomAlphabetic(15);
    }
}
