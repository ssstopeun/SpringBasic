package kdt.spring.mission;

import java.util.ArrayList;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;


    public VoucherService(VoucherRepository voucherRepository){
        this.voucherRepository = voucherRepository;
    }

}
