package org.prgrms.kdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 여기 기준으로 모든 것들이 읽혀서 Bean으로 읽혀진다.
@SpringBootApplication
public class KdtApplication {

	public static void main(String[] args) {
		SpringApplication.run(KdtApplication.class, args);
	}

}
