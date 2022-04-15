package org.yanosik.test.task.server.service;

import java.util.List;
import org.yanosik.test.task.common.model.InsuranceOffer;
import org.yanosik.test.task.server.dao.InsuranceOfferDao;

/**
 * Would contain {@link InsuranceOfferDao} and business logic of the insurance offers.
 * We don't really need this class for our task, so I did not implement it completely.
 *
 */
public interface InsuranceOfferService {
    List<InsuranceOffer> getByVehicleId(Long vehicleId);

    //TODO: implement more methods
}
