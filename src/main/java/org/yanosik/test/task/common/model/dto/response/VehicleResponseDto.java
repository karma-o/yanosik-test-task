package org.yanosik.test.task.common.model.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class VehicleResponseDto {
    private Long id;
    private String brand;
    private String model;
    private LocalDateTime insertTime;
    private List<InsuranceOfferDto> insuranceOffers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDateTime getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(LocalDateTime insertTime) {
        this.insertTime = insertTime;
    }

    public List<InsuranceOfferDto> getInsuranceOffers() {
        return insuranceOffers;
    }

    public void setInsuranceOffers(List<InsuranceOfferDto> insuranceOffers) {
        this.insuranceOffers = insuranceOffers;
    }
}
