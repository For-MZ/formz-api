package ForMZ.Server.Category;

import ForMZ.Server.Category.Entity.Category;
import ForMZ.Server.Category.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/create/Category")
    public void CreateCategory(){
        List<String> names = new ArrayList<>();
        names.add("policy");
        names.add("housing");
        names.add("job");
        names.add("foundation");
        names.add("free");
        names.add("tip");
        for (String name : names) {
            Category category = new Category(name);
            categoryService.save(category);
        }
    }
}
