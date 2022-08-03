package org.prgrms.kdt;

import org.prgrms.kdt.order.OrderProperties;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.JdbcVoucherRepository;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.text.MessageFormat;
import java.util.UUID;

// 여기 기준으로 모든 것들이 읽혀서 Bean으로 읽혀진다.
@SpringBootApplication
@ComponentScan(basePackages = {"org.prgrms.kdt.voucher","org.prgrms.kdt.order","org.prgrms.kdt.configuration" })
public class KdtApplication {

	private static final Logger logger = LoggerFactory.getLogger("org.prgrms.kdt.OrderTester");

	public static void main(String[] args) {
		var springApplication = new SpringApplication(KdtApplication.class);
//		springApplication.setAdditionalProfiles("local");
		var applicationContext = springApplication.run(args);
//		var applicationContext = SpringApplication.run(KdtApplication.class, args);

		var orderProperties = applicationContext.getBean(OrderProperties.class);
		logger.error("logger name => {}",logger.getName());
		logger.warn("version -> {}", orderProperties.getVersion());
		logger.warn("minimumOrderAmount -> {}", orderProperties.getMinimumOrderAmount());
		logger.warn("support -> {}", orderProperties.getSupportVendors());
		logger.warn("description -> {}", orderProperties.getDescription());

		var customerId = UUID.randomUUID();

		var voucherRepository = applicationContext.getBean(VoucherRepository.class);
		var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

		System.out.println(MessageFormat.format("is Jdbc Repo -> {0}", voucherRepository instanceof JdbcVoucherRepository));
		System.out.println(MessageFormat.format("is Jdbc Repo -> {0}", voucherRepository.getClass().getCanonicalName() ));
	}

}
