package travel.travel_community.apiPayload.exception.handler;

import travel.travel_community.apiPayload.code.BaseErrorCode;
import travel.travel_community.apiPayload.exception.GeneralException;

public class CategoryHandler extends GeneralException {
    public CategoryHandler(BaseErrorCode code) {
        super(code);
    }
}
