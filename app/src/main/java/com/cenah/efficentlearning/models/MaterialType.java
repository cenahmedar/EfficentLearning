package com.cenah.efficentlearning.models;

public class MaterialType {
    private int key;
    private String value;

    @Override
    public String toString() {
        return value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MaterialType() {
    }

    public MaterialType(int key, String value) {
        this.key = key;
        this.value = value;
    }
}
