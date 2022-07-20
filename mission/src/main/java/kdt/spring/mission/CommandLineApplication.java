package kdt.spring.mission;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class CommandLineApplication {
    public static void main(String[] args) {
        System.out.println(new IntroService().getIntroduction());

        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);
        ArrayList<VoucherList> voucherList = new ArrayList<VoucherList>();

        System.out.print("명령어를 입력하시오 : ");
        Scanner sc = new Scanner(System.in);
        var input = sc.next();
        while (!input.equals("exit")) {
            if (input.equals("create")) {
                System.out.println("어떤 Voucher 를 생성하시겠습니까?\n"
                        + "(FixedAmountVoucher : 1 / PercentAmountVoucher : 2)");
                Integer VoucherTypeInput = Integer.valueOf(sc.next());
                if (VoucherTypeInput.equals(1)) {
                    voucherList.add(new VoucherList(UUID.randomUUID(), "FixedAmountVoucher", 10L));
                    System.out.println("FixedAmountVoucher 가 생성되었습니다 :)");
                } else{
                    voucherList.add(new VoucherList(UUID.randomUUID(), "PercentVoucher", 10L));
                    System.out.println("PercentAmountVoucher 가 생성되었습니다 :)");
                }
            } else if (input.equals("list")){
                System.out.println("Voucher List");
                voucherList.forEach((v)->{
                    System.out.println("Voucher Id : " + v.getVoucherId() + " / Voucher Discount : " + v.getDiscount() + " / Voucher Type : " + v.getVoucherType());
                });
            } else{
                System.out.println("명령어를 잘못입력하였습니다.");
            }

            System.out.print("명령어를 입력하시오 : ");
            input = sc.next();
        }
        System.exit(0);
    }
}
