package com.techelevator.tenmo.model;

public class UserDto {

    private int id;
    private String username;

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
