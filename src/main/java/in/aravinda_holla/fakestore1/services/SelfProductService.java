package in.aravinda_holla.fakestore1.services;

import in.aravinda_holla.fakestore1.exceptions.ProductNotFoundException;
import in.aravinda_holla.fakestore1.models.Category;
import in.aravinda_holla.fakestore1.models.Product;
import in.aravinda_holla.fakestore1.repositories.CategoryRepository;
import in.aravinda_holla.fakestore1.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("selfProductService")
public class SelfProductService implements ProductService {


    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public SelfProductService(CategoryRepository categoryRepository,
                              ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Product getSingleProduct(int productId) throws ProductNotFoundException {
        Product product = productRepository.findByIdIs(productId);

        if (product == null) {
            throw new ProductNotFoundException("Product with id " + productId + " not found.");
        }
        return product;
    }

    @Override
    public Product addProduct(String title, String description, String imageUrl, String category, double price) {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setImageUrl(imageUrl);
        product.setPrice(price);
        Category categoryDb = categoryRepository.findByTitle(category);

        if (categoryDb == null) {
            Category categoryObj = new Category();
            categoryObj.setTitle(category);
//            categoryRepository.save(categoryObj);
            categoryDb = categoryObj;
        }
        product.setCategory(categoryDb);
        Product productDb = productRepository.save(product);
        return productDb;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(int id, String title, String description, String imageUrl, String category, double price) throws ProductNotFoundException {
        Product productDb = productRepository.findByIdIs(id);

        if (productDb == null) {
            throw new ProductNotFoundException("Product not found.");
        }

        if (title != null) {
            productDb.setTitle(title);
        }

        if (description != null) {
            productDb.setDescription(description);
        }

        if (imageUrl != null) {
            productDb.setImageUrl(imageUrl);
        }

        if (price > 0) {
            productDb.setPrice(price);
        }

        if (category != null) {
            Category categoryDb = categoryRepository.findByTitle(category);
            if (categoryDb == null) {
                Category categoryObj = new Category();
                categoryObj.setTitle(category);
                categoryDb = categoryObj;
            }
            productDb.setCategory(categoryDb);
        }

    return productRepository.save(productDb);

    }

    @Override
    public Product replaceProduct(int id, String title, String description, String imageUrl, String category, double price) throws ProductNotFoundException {
        Product productDb = productRepository.findByIdIs(id);
        if (productDb == null) {
            return addProduct(title,description,imageUrl,category,price);
        }

        productDb.setTitle(title);
        productDb.setPrice(price);
        productDb.setDescription(description);
        productDb.setImageUrl(imageUrl);
        Category categoryDb = categoryRepository.findByTitle(category);

        if (categoryDb == null) {
            Category categoryObj = new Category();
            categoryObj.setTitle(category);
//            categoryRepository.save(categoryObj);
            categoryDb = categoryObj;
        }
        productDb.setCategory(categoryDb);
        return productRepository.save(productDb);
    }

    @Override
    public Product deleteProduct(int id) throws ProductNotFoundException {
        Product product = productRepository.findByIdIs(id);

        if (product == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found.");
        }
        productRepository.delete(product);
        return product;
    }

    @Override
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        List<Category> categoriesDB = categoryRepository.findAll();
        for(Category obj: categoriesDB) {
            categories.add(obj.getTitle());
        }
        return categories;
    }

    @Override
    public List<Product> getCategoryProducts(String title) {
        return productRepository.findAllByCategoryTitle(title);
    }
}
