package com.cenah.efficentlearning.models;

public class UserSuccess {
    private float star;
    private float total;

    public UserSuccess() {
    }

    public UserSuccess(float star, float total) {
        this.star = star;
        this.total = total;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
