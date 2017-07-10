package dev.anhnt.kimdung.models;

public class Chapter {
    private int id;
    private String name;

    public Chapter(int id, String name) {
        setId(id);
        setName(name);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
