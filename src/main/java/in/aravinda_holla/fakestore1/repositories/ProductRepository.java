package in.aravinda_holla.fakestore1.repositories;

import in.aravinda_holla.fakestore1.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product save(Product product);
    List<Product> findAll();
    Product findByIdIs(Integer id);
    List<Product> findAllByCategoryTitle(String title);
}
