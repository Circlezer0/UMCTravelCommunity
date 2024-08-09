package travel.travel_community.apiPayload.exception.handler;

import travel.travel_community.apiPayload.code.BaseErrorCode;
import travel.travel_community.apiPayload.exception.GeneralException;

public class CommentHandler extends GeneralException {
    public CommentHandler(BaseErrorCode code) {
        super(code);
    }
}
