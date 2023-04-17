package com.supriya.poshinda.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SoilTestFormEntity {

    @PrimaryKey(autoGenerate = true)
    int farmerId;

    String farmerName;
    String farmerAddress;
    String farmerRequest;
    String status;

    public int getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getFarmerAddress() {
        return farmerAddress;
    }

    public void setFarmerAddress(String farmerAddress) {
        this.farmerAddress = farmerAddress;
    }

    public String getFarmerRequest() {
        return farmerRequest;
    }

    public void setFarmerRequest(String farmerRequest) {
        this.farmerRequest = farmerRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
