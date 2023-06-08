package kr.ac.kumoh.ordersystem.domain;

import lombok.Getter;

@Getter
public class OrderThread {

    private final Order order;
    private final Thread thread;

    public OrderThread(Order order, Thread thread){
        this.order = order;
        this.thread = thread;
        thread.start();
    }

}
