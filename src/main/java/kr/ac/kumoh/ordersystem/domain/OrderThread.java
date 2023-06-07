package kr.ac.kumoh.ordersystem.domain;

public class OrderThread extends Thread {

    private Order order;

    public OrderThread(Order order) {
        super();
        this.order = order;
    }

    public Integer getOrderId() {
        return order.getId();
    }

}
