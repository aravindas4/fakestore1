package in.aravinda_holla.fakestore1.services;

import in.aravinda_holla.fakestore1.dtos.FakeStoreDto;
import in.aravinda_holla.fakestore1.dtos.ProductResponseDto;
import in.aravinda_holla.fakestore1.exceptions.ProductNotFoundException;
import in.aravinda_holla.fakestore1.models.Product;

import java.util.List;

public interface ProductService {
    public Product getSingleProduct(int productId) throws ProductNotFoundException;
    public Product addProduct(
            String title,
            String description,
            String imageUrl,
            String category,
            double price
    );
    public List<Product> getProducts();
    public Product updateProduct(
            int id,
            String title,
            String description,
            String imageUrl,
            String category,
            double price
    ) throws ProductNotFoundException;
    public Product replaceProduct(
            int id,
            String title,
            String description,
            String imageUrl,
            String category,
            double price
    ) throws ProductNotFoundException;
    public Product deleteProduct(int id) throws ProductNotFoundException;
    public List<String> getCategories();
    public List<Product> getCategoryProducts(String title);
}

