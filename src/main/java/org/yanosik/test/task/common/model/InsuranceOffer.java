package org.yanosik.test.task.common.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class InsuranceOffer {
    private Long id;
    private Long vehicleId;
    private String insurer;
    private BigDecimal price;
    private LocalDateTime insertTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getInsurer() {
        return insurer;
    }

    public void setInsurer(String insurer) {
        this.insurer = insurer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(LocalDateTime insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InsuranceOffer that = (InsuranceOffer) o;
        return Objects.equals(id, that.id)
                && Objects.equals(vehicleId, that.vehicleId)
                && Objects.equals(insurer, that.insurer)
                && Objects.equals(price, that.price)
                && Objects.equals(insertTime, that.insertTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vehicleId, insurer, price, insertTime);
    }

    @Override
    public String toString() {
        return "InsuranceOffer{"
                + "id=" + id
                + ", vehicleId=" + vehicleId
                + ", insurer='" + insurer + '\''
                + ", price=" + price
                + ", insertTime=" + insertTime
                + '}';
    }
}
