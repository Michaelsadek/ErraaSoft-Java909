package EWalletSystem.src.main;

import EWalletSystem.src.main.service.impl.EWalletServiceImpl;

public class main {
    public static void main(String[] args) {
        EWalletServiceImpl applicationService = new EWalletServiceImpl();
        applicationService.startApp();

    }

}
