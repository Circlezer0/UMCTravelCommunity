package travel.travel_community.apiPayload.exception.handler;

import travel.travel_community.apiPayload.code.BaseErrorCode;
import travel.travel_community.apiPayload.exception.GeneralException;

public class UserHandler extends GeneralException {
    public UserHandler(BaseErrorCode code) {
        super(code);
    }
}
