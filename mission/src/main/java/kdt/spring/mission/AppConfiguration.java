package kdt.spring.mission;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;

@Configuration
public class AppConfiguration {
    @Bean
    public VoucherRepository voucherRepository(){
        return new VoucherRepository(){
            @Override
            public void create(VoucherList voucherList) {
            }
        };
    }
    @Bean
    public VoucherService voucherService(){
        return new VoucherService(voucherRepository());
    }
}
