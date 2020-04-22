package com.example.jbt.filmlibrary;

//THIS IS THE FILM OBJECT WITH THE CONSTRUCTOR AND GETTER AND SETTER TO USE IT IN THE CUSTOMER ADAPTER

public class film {

    String name,details;
    int image;
    int id;
    String url;




    public film(String name,String url) {
        this.name = name;
        this.url=url;

    }
    public film(String name) {
        this.name = name;
    }

    public film(int id, String name, String details, String url) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.url = url;
    }

    public film(String name, String details, int image, int id, String url) {
        this.name = name;
        this.details = details;
        this.image = image;
        this.id = id;
        this.url = url;
    }

    public film(String name, String details, String url) {
        this.name = name;
        this.details = details;

        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public film(String name, String details, int image, int id) {
        this.name = name;
        this.details = details;
        this.image = image;
        this.id = id;
    }

    public film(int id, String name, String details) {
        this.id = id;
        this.name = name;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public film(String details, int image, String name) {
        this.details = details;
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
