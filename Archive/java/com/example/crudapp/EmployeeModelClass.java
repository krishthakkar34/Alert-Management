package com.example.crudapp;

import java.sql.Blob;

public class EmployeeModelClass {
    String name;
    String email;
    String  image;
    Integer id;
    String mobileno;

    public EmployeeModelClass(String name, String email, String image) {
        this.name = name;
        this.email = email;
        this.image = image;
    }

    public EmployeeModelClass(String name, String email, Integer id,String image) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.id = id;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
