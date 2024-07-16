package travel.travel_community.apiPayload.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import travel.travel_community.apiPayload.code.BaseErrorCode;
import travel.travel_community.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}