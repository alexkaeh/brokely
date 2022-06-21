package com.techelevator.tenmo.model;

public class UserDto implements Arrayable {

    private int id;
    private String username;


    // Creates a String[] array for use in the ConsoleService#printTable method
    @Override
    public String[] toStringArray(String currentUser) {
        // Since the current user should not be displayed in a list of users, this sets that user to null.
        // The ConsoleService#printTable method will then skip any null values it finds in the array.
        if (this.username.equals(currentUser)) {
            return null;
        }
        String[] arr = new String[2];
        arr[0] = "" + id;
        arr[1] = username;
        return arr;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof UserDto) {
            UserDto otherUser = (UserDto) other;
            return otherUser.getId() == id
                    && otherUser.getUsername().equals(username);
        } else {
            return false;
        }
    }
}
