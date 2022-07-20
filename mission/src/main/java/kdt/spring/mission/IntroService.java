package kdt.spring.mission;

public class IntroService {
    private String Introduction;

    public IntroService() {
        Introduction = "=========== Voucher Program ===========\n"
                + "Type exit to exit the program.\n"
                + "Type create to create a new voucher.\n"
                + "Type list to list all vouchers.\n"
                + "=======================================";
    }

    public String getIntroduction() {
        return Introduction;
    }
}
