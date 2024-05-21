package in.aravinda_holla.fakestore1.repositories;

import in.aravinda_holla.fakestore1.models.Product;
import in.aravinda_holla.fakestore1.repositories.projections.ProductProjection;
import in.aravinda_holla.fakestore1.repositories.projections.ProductWithIdAndTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product save(Product product);
    List<Product> findAll();
    Product findByIdIs(Integer id);
    List<Product> findAllByCategoryTitle(String title);
    List<Product> findAllByCategory_Title(String title);
    List<Product> findAllByCategory_TitleContaining(String title);

    @Query("select p from Product p where p.category.title = :categoryName")
    List<Product> getProductWithCategoryName(String categoryName);

    @Query("select p.title from Product p where p.category.title = :categoryName")
    List<String> getProductTitleForCategoryTitle(String categoryName);

    @Query("select p.title as title, p.id as id from Product p where p.category.title = :categoryName")
    List<ProductWithIdAndTitle> getMultipleFields(String categoryName);

    @Query("select p.title as title, p.id as id from Product p where p.category.title = :categoryName")
    List<ProductProjection> getMultipleFields2(String categoryName);

    @Query(value="select * from product p where p.id = :id", nativeQuery = true)
    Product getProductById(Integer id);
}
