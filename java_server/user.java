package com;

import java.util.Date;

public class user {
    private String id;
    private String password;
    private String name;
    private Date birth;
    private String mail;


    public user(String id,String password,String name){
        this.id=id;
        this.password=password;
        this.name=name;
    }

    public user(String id,String name,Date birth,String mail){
        this.id=id;
        this.name=name;
        this.birth=birth;
        this.mail=mail;
    }

    public void setId(String id){
        this.id=id;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setBirth(Date birth){
        this.birth=birth;
    }

    public void setMail(String mail){
        this.mail=mail;
    }

    public String getId(){
        return id;
    }

    public String getPassword(){
        return password;
    }

    public String getName(){
        return name;
    }

    public Date getBirth(){
        return birth;
    }

    public String getMail(){
        return mail;
    }
}
