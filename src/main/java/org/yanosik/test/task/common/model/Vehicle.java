package org.yanosik.test.task.common.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Vehicle {
    private Long id;
    private String brand;
    private String model;
    private LocalDateTime insertTime;
    // I assumed we have only one vehicle per user
    // and OneToMany relationship with insurance offers based on the task description
    private List<InsuranceOffer> insuranceOffers;

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

    public List<InsuranceOffer> getInsuranceOffers() {
        return insuranceOffers;
    }

    public void setInsuranceOffers(List<InsuranceOffer> insuranceOffers) {
        this.insuranceOffers = insuranceOffers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id)
                && Objects.equals(brand, vehicle.brand)
                && Objects.equals(model, vehicle.model)
                && Objects.equals(insertTime, vehicle.insertTime)
                && Objects.equals(insuranceOffers, vehicle.insuranceOffers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, insertTime, insuranceOffers);
    }

    @Override
    public String toString() {
        return "Vehicle{"
                + "id=" + id
                + ", brand='" + brand + '\''
                + ", model='" + model + '\''
                + ", insertTime=" + insertTime
                + ", insuranceOffers=" + insuranceOffers
                + '}';
    }
}
