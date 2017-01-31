package com.phyder.paalan.services;

import com.phyder.paalan.payload.request.individual.IndivitualReqRegistration;
import com.phyder.paalan.utils.OrgAddressInfo;

/**
 * Created by yashika on 18/1/17.
 */

public interface RegisterUserInterface {
    void navigatePrevious();
    void navigateNext();
    void registerUser();
    void setIndividualRegInfo(IndivitualReqRegistration individualInfo);
    void setAddressRegInfo(OrgAddressInfo orgAddressInfo);
}
