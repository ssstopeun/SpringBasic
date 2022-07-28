package org.prgrms.kdt.voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
  Voucher insert(Voucher voucher);
  Optional<Voucher> findById(UUID voucherId);

}
