package in.aravinda_holla.fakestore1.controllers;

import in.aravinda_holla.fakestore1.dtos.ProductResponseDto;
import in.aravinda_holla.fakestore1.services.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<String> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/categories/{title}")
    public List<ProductResponseDto> getCategoryProducts(@PathVariable("title") String title) {
        return categoryService.getCategoryProducts(title);
    }
}
