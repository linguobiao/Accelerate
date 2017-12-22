package com.lgb.accelerate.bean;

/**
 * Created by linguobiao on 16/8/25.
 */
public class Track {
    private String create_date;
    private int id;
    private int uid;
    private int steps;
    private double stride;
    private double weight;

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public double getStride() {
        return stride;
    }

    public void setStride(double stride) {
        this.stride = stride;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Track{" +
                "create_date='" + create_date + '\'' +
                ", id=" + id +
                ", uid=" + uid +
                ", steps=" + steps +
                ", stride=" + stride +
                ", weight=" + weight +
                '}';
    }
}
