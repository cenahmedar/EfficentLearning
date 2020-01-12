package com.cenah.efficentlearning.models;

public class SuccessModel {
    private float total;
    private float star;

    public SuccessModel() {
    }

    public SuccessModel(float total, float star) {
        this.total = total;
        this.star = star;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }
}
