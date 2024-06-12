package delivery.dto;

public class OrderDto {

    String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setId(long id) {
        this.id = id;
    }

    int courierId;
    String customerName;
    String customerPhone;
    String comment;
    long id;


    //Constractor - how the objects will be created
    public OrderDto(String customerName, String customerPhone, String comment) {
        this.status = "OPEN";
        this.courierId = 0;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.comment = comment;
        this.id = 0;
    }
}
