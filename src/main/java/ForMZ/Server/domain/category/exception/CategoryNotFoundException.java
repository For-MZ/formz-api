package ForMZ.Server.domain.category.exception;

import static ForMZ.Server.domain.category.exception.CategoryExceptionList.CATEGORY_NOT_FOUND;

public class CategoryNotFoundException extends CategoryException {
    public CategoryNotFoundException() {
        super(CATEGORY_NOT_FOUND.getStatusCode(), CATEGORY_NOT_FOUND.getHttpStatus(), CATEGORY_NOT_FOUND.getMessage());
    }
}
