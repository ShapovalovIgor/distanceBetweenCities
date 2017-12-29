package ru.shapovalov.Class.Model;

/**
 * Created by igor on 28.07.15.
 */
public class Distance {
    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    private String fromCity;

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    private String toCity;

    public int getDistace() {
        return distace;
    }

    public void setDistace(int distace) {
        this.distace = distace;
    }

    private int distace;
    public Distance(){}
}
