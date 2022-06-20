package com.techelevator.tenmo;

import com.techelevator.tenmo.info.MenuArrays;
import com.techelevator.tenmo.info.Url;
import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = Url.BASE.toString();

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final UserService userService = new UserService();
    private final AccountService accountService = new AccountService();
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
            menuSelection = consoleService.getChoiceFromOptions(new String[]{"Register", "Login"});
            if (menuSelection == 0) {
                handleRegister();
            } else if (menuSelection == 1) {
                handleLogin();
            } else if (menuSelection != -1) {
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
        } else {
            //sets authToken for all services
            ApiService.setAuthToken(currentUser.getToken());
            currentAccount = accountService.getCurrentAccount();
        }
    }

    private void mainMenu() {
        int menuSelection = -2;
        while (menuSelection != -1) {
            menuSelection = consoleService.getChoiceFromOptions(MenuArrays.MAIN_MENU_OPTIONS);
            if (menuSelection == 0) { // "View your current balance"
                viewCurrentBalance();
            } else if (menuSelection == 1) { // "View your past transfers"
                viewTransferHistory();
            } else if (menuSelection == 2) { // "View your pending requests"
                viewPendingRequests();
            } else if (menuSelection == 3) { // "Send TE bucks"
                sendBucks();
            } else if (menuSelection == 4) { // "Request TE bucks"
                requestBucks();
            } else if (menuSelection == -1) { // "Exit"
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
        TransferDto[] transfers = transferService.getAllTransfers();

        if(transfers == null || transfers.length == 0) {
            System.out.println("No transfers found.");
            return;
        }

        consoleService.printTable(
                new String[]{"ID", "From/To", "Amount"},
                transferService.getAllTransfers(),
                currentUser.getUser().getUsername()
        );
    }

    private void viewPendingRequests() {
        TransferDto[] pendingTransfers = transferService.getPendingTransfers();

        if(pendingTransfers == null || pendingTransfers.length == 0) {
            System.out.println("No pending transfers found.");
            return;
        }

        consoleService.printTable(
                new String[]{"ID", "To", "Amount"},
                pendingTransfers,
                currentUser.getUser().getUsername()
        );

        // prompt to approve or reject
        int transferId = consoleService.promptForInt("Please enter transfer ID to approve/reject (0 to cancel): ");

        if(transferId == 0) {
            return;
        }

        consoleService.printCurrentBalance(currentAccount);

        int choice = consoleService.getChoiceFromOptions(new String[]{"Approve", "Reject"});

        if(choice == 0) {
            BigDecimal updatedBalance = transferService.approveRequest(transferId);

            if(updatedBalance.equals(currentAccount.getBalance())) {
                System.out.println("Request failed.");
                return;
            }

            currentAccount.setBalance(updatedBalance);
            consoleService.printCurrentBalance(currentAccount);
        } else if(choice == 1) {
            boolean successfulReject = transferService.rejectRequest(transferId);
            if(successfulReject) {
                System.out.println("Transfer rejected.");
            } else {
                System.out.println("Request failed.");
            }
        }
    }

    private void sendBucks() {
        UserDto[] users = userService.getUsers();
        // Get user to send money to
        consoleService.printTable(
                new String[] {"ID","Name"},
                users,
                currentUser.getUser().getUsername()
        );
        int recipientId = consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel): ");


        //get transfer amount
        BigDecimal transferAmount = consoleService.promptForBigDecimal("Enter amount to be sent: ");

        // Use index to get userId, send to server, and return new balance
        BigDecimal updatedBalance = transferService.sendMoney(recipientId, transferAmount);

//        if (updatedBalance == null) {
//            updatedBalance = accountService.getBalance();
//        }
        // Update in memory balance

        // Checks to see if balance has changed. If not, then the server did not fulfil the request.
        if(updatedBalance.equals(currentAccount.getBalance())) {
            System.out.println("Request failed.");
        }

        currentAccount.setBalance(updatedBalance);

        consoleService.printCurrentBalance(currentAccount);
    }

    private void requestBucks() {
        UserDto[] users = userService.getUsers();

        consoleService.printTable(
                new String[] {"ID","Name"},
                users,
                currentUser.getUser().getUsername()
        );

        int recipientId = consoleService.promptForInt("Enter ID of user you are requesting from (0 to cancel): ");
        BigDecimal transferAmount = consoleService.promptForBigDecimal("Enter amount to request: ");
        // Use index to get userId, send to server, and return new balance
        boolean wasSuccess = transferService.requestMoney(recipientId, transferAmount);

        if (!wasSuccess) {
            System.out.println("Request failed.");
        }
    }
}
