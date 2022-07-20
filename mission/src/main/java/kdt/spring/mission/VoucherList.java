package kdt.spring.mission;

import java.util.ArrayList;
import java.util.UUID;

public class VoucherList {
    private final UUID voucherId;
    private final long discount;
    private final String voucherType;


    public VoucherList(UUID voucherId, String voucherType, long discount) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = voucherType;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscount() {
        return discount;
    }

    public String getVoucherType() {
        return voucherType;
    }


}


