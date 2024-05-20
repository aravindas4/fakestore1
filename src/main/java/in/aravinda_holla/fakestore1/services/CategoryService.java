package in.aravinda_holla.fakestore1.services;

import in.aravinda_holla.fakestore1.dtos.ProductResponseDto;
import in.aravinda_holla.fakestore1.models.Product;

import java.util.List;

public interface CategoryService {
    public List<String> getCategories();
    public List<Product> getCategoryProducts(String title);
}
