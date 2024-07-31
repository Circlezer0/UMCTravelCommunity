package travel.travel_community.apiPayload.exception.handler;

import travel.travel_community.apiPayload.code.BaseErrorCode;
import travel.travel_community.apiPayload.exception.GeneralException;

public class PostHandler extends GeneralException {
    public PostHandler(BaseErrorCode code) {
        super(code);
    }
}
