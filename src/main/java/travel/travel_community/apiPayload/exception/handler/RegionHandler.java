package travel.travel_community.apiPayload.exception.handler;

import travel.travel_community.apiPayload.code.BaseErrorCode;
import travel.travel_community.apiPayload.exception.GeneralException;

public class RegionHandler extends GeneralException {
    public RegionHandler(BaseErrorCode code) {
        super(code);
    }
}
