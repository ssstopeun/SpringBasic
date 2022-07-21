package org.prgrams.kdt;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {
    //Order는 Entity니까 식별자는 UUID로 만든다.
    private final UUID orderid;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private Optional<Voucher> voucher;

    //이미 설정되있으므로 생성자에서 받을 필요가 없다. setter로 변경가능
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID orderid, UUID customerId, List<OrderItem> orderItems) {
        this.orderid = orderid;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.empty();
    }

    public Order(UUID orderid, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderid = orderid;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
    }


    public long totalAmount() {
        var beforeDiscount = orderItems.stream()
                .map(v->v.getProductPrice() * v.getQuantity())//orderItems를 돌면서 v의 price와 quantity를 곱한다.
                .reduce(0L,Long::sum); //초기값 0L에서 시작해 long타입만 다 더한다.
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }


    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}