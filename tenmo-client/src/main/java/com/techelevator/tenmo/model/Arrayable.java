/**
 * This interface must be implemented by any object that will use the ConsoleService#printTable
 * method, ensuring that it has a means to return a String[] array.
 */

package com.techelevator.tenmo.model;

public interface Arrayable {

    String[] toStringArray(String currentUser);

}
