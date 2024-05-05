package ForMZ.Server.domain.category.service;

import ForMZ.Server.domain.category.entity.Category;
import ForMZ.Server.domain.category.entity.CategoryName;
import ForMZ.Server.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public Category getCategory(String categoryName){
        if(categoryName.isBlank())
            return null;
        return categoryRepository.findByCategoryName(CategoryName.parsing(categoryName));
    }
}