/**
 * This class is used to read and write to the console. It is used for all interactions with the user.
 */
package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);
    public static final int CELL_WIDTH = 16;

    public void printTable(String[] headers, Arrayable[] objects, String currentUser) {
        if (headers == null || objects == null || objects.length == 0) {
            return;
        }

        // Set the number of cells in each row based on the length of headers[]
        final int ROW_LENGTH = headers.length;

        for (String cell : headers) {
            System.out.printf("%-" + CELL_WIDTH + "s", cell);
        }

        // Print new line followed up repeating dash
        System.out.println("\n" + "-".repeat((CELL_WIDTH) * ROW_LENGTH));

        for (Arrayable obj : objects) {
            if (obj == null) {
                continue;
            }
            String[] currentRow = obj.toStringArray(currentUser);

            // The UserDto class will set the current user to null in objects array, so we skip it
            if (currentRow == null) {
                continue;
            }
            for (String cell : currentRow) {
                System.out.printf("%-" + CELL_WIDTH + "s", cell);
            }
            System.out.println();
        }
    }

    public void printCurrentBalance(Account currentAccount) {
        System.out.println("Current balance: $" + currentAccount.getBalance());
    }

    public void printGreeting() {
        System.out.println("************************");
        System.out.println("* Welcome to broke.ly! *");
        System.out.println("************************");
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    // helper method
    public void displayUsers(UserDto[] users) {
        System.out.println("\n------------------------------------\nUsers\nID\t\tUsername\n------------------------------------");
        for (UserDto user : users) {
            System.out.println(user.getId() + "\t" + user.getUsername());
        }
        System.out.println("------------------------------------");
    }

    public void displayTransferDetails(TransferDto transferDto) {
        System.out.println("\n------------------------------------\nTransfer Details\n------------------------------------");
        System.out.println(transferDto);
    }

    public int getChoiceFromOptions(Object[] options) {
        int choice = -2;
        while (choice == -2) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
            //-1 == exit || -2 == error
        }
        return choice;
    }

    private int getChoiceFromUserInput(Object[] options) {
        System.out.print(System.lineSeparator() + "Please choose an option: ");
        String userInput = scanner.nextLine();
        try {
            int selectedOption = Integer.parseInt(userInput);
            // 0 always exits
            if (selectedOption == 0) {
                return -1;
            }
            // Option must be in valid bounds
            if (selectedOption > 0 && selectedOption <= options.length) {
                return selectedOption - 1;
            }
        } catch (NumberFormatException e) {
            System.out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
        }
        // Exit if user enters invalid input
        return -2;
    }

    public void displayMenuOptions(Object[] options) {
        System.out.println();
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ": " + options[i]);
        }
        System.out.println("0: Exit");
    }

}
