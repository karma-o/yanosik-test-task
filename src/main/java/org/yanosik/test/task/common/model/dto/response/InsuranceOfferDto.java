package org.yanosik.test.task.common.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InsuranceOfferDto {
    private Long id;
    private String insurer;
    private BigDecimal price;
    private LocalDateTime insertTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
