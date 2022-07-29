package org.prgrms.kdt;

import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderTester {
  public static void main(String[] args) {
    var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

    var environment = applicationContext.getEnvironment();
    var version = environment.getProperty("kdt.version");
    var minimumOrderAmount = environment.getProperty("kdt.minimum-order-amount", Integer.class);
    var support = environment.getProperty("kdt.support-vendors", List.class);
    System.out.println(MessageFormat.format("version -> {0}", version));
    System.out.println(MessageFormat.format("minimumOrderAmount -> {0}", minimumOrderAmount));
    System.out.println(MessageFormat.format("support -> {0}", support));

    var customerId = UUID.randomUUID();

    var voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");
    var voucherRepository2 = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");
    System.out.println(MessageFormat.format("voucherRepository {0}", voucherRepository));
    System.out.println(MessageFormat.format("voucherRepository2 {0}", voucherRepository2));
    System.out.println(MessageFormat.format("voucherRepository == voucherRepository2 => {0}", voucherRepository == voucherRepository2));
    var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

    var orderService = applicationContext.getBean(OrderService.class);
    var order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
      add(new OrderItem(UUID.randomUUID(), 100L, 1));
    }}, voucher.getVoucherId());

    Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0} is not 100L", order.totalAmount()));

    applicationContext.close(); //application.destroy();
  }
}