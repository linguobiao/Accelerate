package com.lgb.accelerate.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by linguobiao on 16/8/24.
 */
public class ResultLogin implements Parcelable {

    private boolean result;
    private String session_id;
    private int total_steps;
    private double total_distances;
    private double total_calories;
    private String date;
    private int id;
    private String email;
    private String name;
    private int gender;
    private double height;
    private double weight;
    private double stride;
    private int units;
    private String create_date;
    private String update_date;
    private int code;
    private String reason;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getStride() {
        return stride;
    }

    public void setStride(double stride) {
        this.stride = stride;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getTotal_steps() {
        return total_steps;
    }

    public void setTotal_steps(int total_steps) {
        this.total_steps = total_steps;
    }

    public double getTotal_distances() {
        return total_distances;
    }

    public void setTotal_distances(double total_distances) {
        this.total_distances = total_distances;
    }

    public double getTotal_calories() {
        return total_calories;
    }

    public void setTotal_calories(double total_calories) {
        this.total_calories = total_calories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte)(result ?1:0));//if myBoolean == true, byte == 1
        dest.writeString(session_id);
        dest.writeInt(total_steps);
        dest.writeDouble(total_distances);
        dest.writeDouble(total_calories);
        dest.writeString(date);
        dest.writeInt(id);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeInt(gender);
        dest.writeDouble(height);
        dest.writeDouble(weight);
        dest.writeDouble(stride);
        dest.writeInt(units);
        dest.writeString(create_date);
        dest.writeString(update_date);
        dest.writeInt(code);
        dest.writeString(reason);

    }

    public static final Parcelable.Creator<ResultLogin> CREATOR = new Creator<ResultLogin>() {
        public ResultLogin createFromParcel(Parcel source) {
            ResultLogin object = new ResultLogin();

            object.result =source.readByte()!=0;//myBoolean == true if byte != 0
            object.session_id = source.readString();
            object.total_steps = source.readInt();
            object.total_distances = source.readDouble();
            object.total_calories = source.readDouble();
            object.date = source.readString();
            object.id = source.readInt();
            object.email = source.readString();
            object.name = source.readString();
            object.gender = source.readInt();
            object.height = source.readDouble();
            object.weight = source.readDouble();
            object.stride = source.readDouble();
            object.units = source.readInt();
            object.create_date = source.readString();
            object.update_date = source.readString();
            object.code = source.readInt();
            object.reason = source.readString();

            return object;
        }

        public ResultLogin[] newArray(int size) {
            return new ResultLogin[size];
        }
    };

    @Override
    public String toString() {
        return "ResultLogin{" +
                "result=" + result +
                ", session_id='" + session_id + '\'' +
                ", steps=" + total_steps +
                ", distances=" + total_distances +
                ", calories=" + total_calories +
                ", date='" + date + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", height=" + height +
                ", weight=" + weight +
                ", stride=" + stride +
                ", units=" + units +
                ", create_date='" + create_date + '\'' +
                ", update_date='" + update_date + '\'' +
                ", code=" + code +
                ", reason='" + reason + '\'' +
                '}';
    }
}
