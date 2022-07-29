package org.prgrms.kdt;

import org.prgrms.kdt.order.Order;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Optional;
import java.util.UUID;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.kdt.voucher","org.prgrms.kdt.order","org.prgrms.kdt.configuration" })
@PropertySource("application.properties")
public class AppConfiguration {

}