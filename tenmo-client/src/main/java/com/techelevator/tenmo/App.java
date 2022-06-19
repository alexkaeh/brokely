package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = Url.BASE.toString();

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final UserService userService = new UserService();
    private final  AccountService accountService = new AccountService();
    private final TransferService transferService = new TransferService();

    private AuthenticatedUser currentUser;
    private Account currentAccount;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);

        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
        else {
            //sets authToken for all services
            ApiService.setAuthToken(currentUser.getToken());
            currentAccount = accountService.getCurrentAccount();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
        System.out.println(accountService.getBalance());
	}

	private void viewTransferHistory() {
        System.out.println(transferService.getAllTransfers());
	}

	private void viewPendingRequests() {
		// TODO TEST THIS LATER
        TransferDto[] transferDtos = transferService.getPendingTransfers();
        for(TransferDto td : transferDtos) {
            System.out.println(td);
        }
	}

	private void sendBucks() {
        UserDto[] users = userService.getUsers();
        consoleService.displayUsers(users);

        //get recipient id
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter ID of user you are requesting from (0 to cancel): ");
        int recipientId = sc.nextInt();

        //get transfer amount
        System.out.println("Enter amount: ");
        BigDecimal transferAmount = sc.nextBigDecimal();

        BigDecimal updatedBalance = transferService.sendMoney(recipientId,transferAmount);
        if(updatedBalance == null) {
            updatedBalance = accountService.getBalance();
        }

        currentAccount.setBalance(updatedBalance);
	}

	private void requestBucks() {
		// TODO Auto-generated method stub

    }
}
