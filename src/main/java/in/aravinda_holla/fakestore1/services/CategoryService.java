package in.aravinda_holla.fakestore1.services;

import in.aravinda_holla.fakestore1.dtos.ProductResponseDto;

import java.util.List;

public interface CategoryService {
    public List<String> getCategories();
    public List<ProductResponseDto> getCategoryProducts(String title);
}
