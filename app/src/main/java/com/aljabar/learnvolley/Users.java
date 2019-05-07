package com.aljabar.learnvolley;

public class Users {
    private String Id;
    private String name;
    private String username;
    private String email;
    private String address;
    private String geo;

    public Users(String id, String name, String email, String username, String address, String geo) {
        this.Id = id;
        this.name= name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.geo = geo;
    }

    public String getId() {
        return Id;
    }
    public void setId(String id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getGeo() {
        return geo;
    }
    public void setGeo(String geo) {
        this.geo = geo;
    }

}