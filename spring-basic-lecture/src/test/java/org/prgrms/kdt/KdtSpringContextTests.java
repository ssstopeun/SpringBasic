package org.prgrms.kdt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.kdt.order.MemoryOrderRepository;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.order.OrderStatus;
import org.prgrms.kdt.voucher.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringJUnitConfig
@ActiveProfiles("test") //test 관련 bean들 생성, Mock을 사용하지않고 Spring제공 test framework로 사용한 것
public class KdtSpringContextTests {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdt.voucher","org.prgrms.kdt.order"})
    static class Config{

    }

    @Autowired
    ApplicationContext context;

    @Autowired
    OrderService orderService;

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("applicationContext가 생성되야 한다.")
    public void testApplicationContext(){
        assertThat(context, notNullValue());

    }

    @Test
    @DisplayName("VoucherRepository가 빈으로 등록되어 있어야 한다.")
    public void testVoucherRepositoryCreation(){
        var bean = context.getBean(VoucherRepository.class);
        assertThat(bean,notNullValue());
    }
    @Test
    @DisplayName("orderService를 사용하여 주문을 생성할 수 있다.")
    public void testOrderService(){
        //Given
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(fixedAmountVoucher);

        //When
        var order = orderService.createOrder(
                UUID.randomUUID(),
                List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                fixedAmountVoucher.getVoucherId());

        //Then
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }
}
