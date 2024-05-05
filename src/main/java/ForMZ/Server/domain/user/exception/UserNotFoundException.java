package ForMZ.Server.domain.user.exception;


import static ForMZ.Server.domain.user.exception.UserExceptionList.USER_NOT_FOUND;

public class UserNotFoundException extends UserException {
    public UserNotFoundException() {
        super(USER_NOT_FOUND.getStatusCode(), USER_NOT_FOUND.getHttpStatus(), USER_NOT_FOUND.getMessage());
    }
}
