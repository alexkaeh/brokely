package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.model.UserDto;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
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
                System.out.println("Please enter a decimal number.");
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
    public void displayUsers(UserDto[] users){
        System.out.println("\n------------------------------------\nUsers\nID\t\tUsername\n------------------------------------");
        for (UserDto user : users){
            System.out.println(user.getId()+"\t"+user.getUsername());
        }
        System.out.println("------------------------------------");
    }

    public int getChoiceFromOptions(Object[] options) {
        int choice = -1;
        while (choice == -1) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    private int getChoiceFromUserInput(Object[] options) {
        System.out.print(System.lineSeparator() + "Please choose an option: ");
        String userInput = scanner.nextLine();
        try {
            int selectedOption = Integer.parseInt(userInput);
            if (selectedOption >= 0 && selectedOption <= options.length) {
                return selectedOption;
            }
        } catch (NumberFormatException e) {
            System.out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
        }
        return -1;
    }

    public void displayMenuOptions(Object[] options) {
        System.out.println();
        for (int i = 1; i < options.length; i++) {
            System.out.println(i + ": " + options[i]);
        }
        if(options[0] != null){
            System.out.println("0: " + options[0]);
        }
    }

}
