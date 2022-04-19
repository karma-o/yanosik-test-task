package org.yanosik.test.task.server.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.yanosik.test.task.common.model.InsuranceOffer;
import org.yanosik.test.task.server.dao.InsuranceOfferDao;
import org.yanosik.test.task.server.service.InsuranceOfferService;

@Service
public class InsuranceOfferServiceImpl implements InsuranceOfferService {
    private final InsuranceOfferDao insuranceOfferDao;

    public InsuranceOfferServiceImpl(InsuranceOfferDao insuranceOfferDao) {
        this.insuranceOfferDao = insuranceOfferDao;
    }

    @Override
    public List<InsuranceOffer> getByVehicleId(Long vehicleId) {
        return insuranceOfferDao.getByVehicleId(vehicleId);
    }
}
