package com.techelevator.tenmo.model;

public class UserDto implements Arrayable {

    private int id;
    private String username;

    @Override
    public String[] toStringArray(String currentUser) {
        return toStringArray();
    }

    @Override
    public String[] toStringArray() {
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
