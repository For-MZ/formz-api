package ForMZ.Server.domain.post.exception;

import static ForMZ.Server.domain.post.exception.PostExceptionList.POST_NOT_FOUND;

public class PostNotFoundException extends PostException {
    public PostNotFoundException() {
        super(POST_NOT_FOUND.getStatusCode(), POST_NOT_FOUND.getHttpStatus(), POST_NOT_FOUND.getMessage());
    }
}
