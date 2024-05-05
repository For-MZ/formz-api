package ForMZ.Server.domain.comment.exception;

import org.springframework.http.HttpStatus;

import static ForMZ.Server.domain.comment.exception.CommentExceptionList.COMMENT_NOT_FOUND;

public class CommentNotFoundException extends CommentException{
    public CommentNotFoundException() {
        super(COMMENT_NOT_FOUND.getStatusCode(), COMMENT_NOT_FOUND.getHttpStatus(), COMMENT_NOT_FOUND.getMessage());
    }
}
