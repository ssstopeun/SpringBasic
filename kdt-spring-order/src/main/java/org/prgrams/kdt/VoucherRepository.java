package org.prgrams.kdt;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findId(UUID voucherId);
}
