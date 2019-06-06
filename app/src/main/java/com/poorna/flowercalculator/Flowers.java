package com.poorna.flowercalculator;

public class Flowers {

    private int id;
    private String customername;
    private String mobileno;
    private String dateofpurchase;
    private Double costofflowers;
    private Double quantityofflowers;
    private Double totalamountofflowers;


    public Flowers() {
    }

    public Flowers(int id, String customername, String mobileno, String dateofpurchase, Double costofflowers, Double quantityofflowers, Double totalamountofflowers) {
        this.id = id;
        this.customername = customername;
        this.mobileno = mobileno;
        this.dateofpurchase = dateofpurchase;
        this.costofflowers = costofflowers;
        this.quantityofflowers = quantityofflowers;
        this.totalamountofflowers = totalamountofflowers;
    }

    public Flowers(String customername, String mobileno, String dateofpurchase, Double costofflowers, Double quantityofflowers, Double totalamountofflowers) {
        this.customername = customername;
        this.mobileno = mobileno;
        this.dateofpurchase = dateofpurchase;
        this.costofflowers = costofflowers;
        this.quantityofflowers = quantityofflowers;
        this.totalamountofflowers = totalamountofflowers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getDateofpurchase() {
        return dateofpurchase;
    }

    public void setDateofpurchase(String dateofpurchase) {
        this.dateofpurchase = dateofpurchase;
    }

    public Double getCostofflowers() {
        return costofflowers;
    }

    public void setCostofflowers(Double costofflowers) {
        this.costofflowers = costofflowers;
    }

    public Double getQuantityofflowers() {
        return quantityofflowers;
    }

    public void setQuantityofflowers(Double quantityofflowers) {
        this.quantityofflowers = quantityofflowers;
    }

    public Double getTotalamountofflowers() {
        return totalamountofflowers;
    }

    public void setTotalamountofflowers(Double totalamountofflowers) {
        this.totalamountofflowers = totalamountofflowers;
    }

}
