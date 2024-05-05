package ForMZ.Server.domain.post.exception;

import static ForMZ.Server.domain.post.exception.PostExceptionList.INVALID_SORT_PARAM;

public class InvalidSortParamException extends PostException {
    public InvalidSortParamException() {
        super(INVALID_SORT_PARAM.getStatusCode(), INVALID_SORT_PARAM.getHttpStatus(), INVALID_SORT_PARAM.getMessage());
    }
}