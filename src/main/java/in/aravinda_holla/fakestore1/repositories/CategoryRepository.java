package in.aravinda_holla.fakestore1.repositories;

import in.aravinda_holla.fakestore1.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category save(Category category);
    Category findByTitle(String name);
    List<Category> findAll();
}
