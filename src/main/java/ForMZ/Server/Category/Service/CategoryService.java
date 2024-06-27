package ForMZ.Server.Category.Service;


import ForMZ.Server.Category.Entity.Category;
import ForMZ.Server.Category.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public void save(Category category){
        categoryRepository.save(category);
    }
}
