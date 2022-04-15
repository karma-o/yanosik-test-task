package org.yanosik.test.task.server.dao;

import java.util.List;
import org.yanosik.test.task.common.model.InsuranceOffer;

/**
 * Would be used do communicate with database of the insurance offers.
 * We don't really need this class for our task, so I did not implement it completely.
 */
public interface InsuranceOfferDao {
    List<InsuranceOffer> getByVehicleId(Long id);

    //TODO: implement more methods
}
