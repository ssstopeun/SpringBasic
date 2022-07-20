package org.prgrams.kdt.kdt;

import java.util.UUID;
// 개별 product를 가져야 된다. -> productId를 식별자로
public class OrderItem {
    public final UUID productId;
    public final long productPrice;
    public final int quantity;  // 수량

    public OrderItem(UUID productId, long productPrice, int quantity) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}