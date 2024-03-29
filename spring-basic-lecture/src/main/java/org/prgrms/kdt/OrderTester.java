package org.prgrms.kdt;

import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderProperties;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.JdbcVoucherRepository;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.prgrms.kdt.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderTester {
  private static final Logger logger = LoggerFactory.getLogger("org.prgrms.kdt.OrderTester");
  public static void main(String[] args) throws IOException {
    AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
    var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
//    System.out.println(MessageFormat.format("version -> {0}", orderProperties.getVersion()));
//    System.out.println(MessageFormat.format("minimumOrderAmount -> {0}", orderProperties.getMinimumOrderAmount()));
//    System.out.println(MessageFormat.format("support -> {0}", orderProperties.getSupportVendors()));
//    System.out.println(MessageFormat.format("description -> {0}", orderProperties.getDescription()));

//    var resource = applicationContext.getResource("application.yaml");
//    var resource2 = applicationContext.getResource("file:sample.txt");
//    var resource3 = applicationContext.getResource("https://stackoverflow.com/");
//    System.out.println(MessageFormat.format("Resource -> {0}", resource3.getClass().getCanonicalName()));
//
//    var readableByteChannel = Channels.newChannel(resource3.getURL().openStream());
//    var bufferedReader = new BufferedReader(Channels.newReader(readableByteChannel, StandardCharsets.UTF_8));
//    var contents = bufferedReader.lines().collect(Collectors.joining("\n"));
//    System.out.println(contents);
//    var strings = Files.readAllLines(resource2.getFile().toPath());
//    System.out.println(strings.stream().reduce("",(a, b)-> a + "\n" + b));

    var orderProperties = applicationContext.getBean(OrderProperties.class);
    logger.error("logger name => {}",logger.getName());
    logger.warn("version -> {}", orderProperties.getVersion());
    logger.warn("minimumOrderAmount -> {}", orderProperties.getMinimumOrderAmount());
    logger.warn("support -> {}", orderProperties.getSupportVendors());
    logger.warn("description -> {}", orderProperties.getDescription());


    var customerId = UUID.randomUUID();
    var voucherRepository = applicationContext.getBean(VoucherRepository.class);
    var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

    var orderService = applicationContext.getBean(OrderService.class);
    var order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
      add(new OrderItem(UUID.randomUUID(), 100L, 1));
    }}, voucher.getVoucherId());

    Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0} is not 100L", order.totalAmount()));

   // applicationContext.close(); //application.destroy();
  }
}
