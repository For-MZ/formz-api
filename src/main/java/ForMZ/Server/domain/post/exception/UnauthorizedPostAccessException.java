package ForMZ.Server.domain.post.exception;

import static ForMZ.Server.domain.post.exception.PostExceptionList.UNAUTHORIZED_POST_ACCESS;

public class UnauthorizedPostAccessException extends PostException{
    public UnauthorizedPostAccessException(){
        super(UNAUTHORIZED_POST_ACCESS.getStatusCode(), UNAUTHORIZED_POST_ACCESS.getHttpStatus(), UNAUTHORIZED_POST_ACCESS.getMessage());
    }
}
