package delivery.dto;

public class OrderDto {

    public String status;

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

    public int courierId;
    public String customerName;
    public String customerPhone;
    public String comment;

    public String getStatus() {
        return status;
    }

    public int getCourierId() {
        return courierId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getComment() {
        return comment;
    }

    public long getId() {
        return id;
    }

    public long id;

    //Constractor - how the objects will be created
    public OrderDto() {
        this.status = "OPEN";
        this.courierId = 0;
        this.customerName = "customerName";
        this.customerPhone = "12345678";
        this.comment = "comment";
        this.id = 0;
    }
}
