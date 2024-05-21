package in.aravinda_holla.fakestore1;

import in.aravinda_holla.fakestore1.models.Product;
import in.aravinda_holla.fakestore1.repositories.CategoryRepository;
import in.aravinda_holla.fakestore1.repositories.ProductRepository;
import in.aravinda_holla.fakestore1.repositories.projections.ProductProjection;
import in.aravinda_holla.fakestore1.repositories.projections.ProductWithIdAndTitle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Fakestore1ApplicationTests {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Test
    void contextLoads() {
    }

    @Test
    void testJPADeclaredJoin() {
        List<Product> products = productRepo.findAllByCategory_Title("electronics");
        for(Product obj: products) {
            System.out.println(obj.getTitle());
        }

        List<Product> products1 = productRepo.findAllByCategory_TitleContaining("ele");
        for(Product obj: products1) {
            System.out.println(obj.getTitle());
        }
    }

    @Test
    void testHQL() {
        List<Product> products = productRepo.getProductWithCategoryName("electronics");
        for(Product obj: products) {
            System.out.println(obj.getPrice());
        }
    }

    @Test
    void testSpecificField() {
        List<String> titles = productRepo.getProductTitleForCategoryTitle("electronics");
        for(String title: titles) {
            System.out.println(title);
        }
    }

    @Test
    void testProjections() {
        List<ProductWithIdAndTitle> products = productRepo.getMultipleFields("electronics");
        for(ProductWithIdAndTitle obj: products) {
            System.out.println(obj.getTitle());
            System.out.println(obj.getId());
        }
    }

    @Test
    void testProjections2() {
        List<ProductProjection> products = productRepo.getMultipleFields2("electronics");
        for(ProductProjection obj: products) {
            System.out.println(obj.getTitle());
            System.out.println(obj.getId());
        }
    }

    @Test
    void testNativeQuery() {
        Product product = productRepo.getProductById(9);
        System.out.println(product.getTitle());
    }

}
