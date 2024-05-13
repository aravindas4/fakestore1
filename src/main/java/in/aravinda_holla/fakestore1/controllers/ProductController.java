package in.aravinda_holla.fakestore1.controllers;

import in.aravinda_holla.fakestore1.dtos.ProductRequestDto;
import in.aravinda_holla.fakestore1.dtos.ProductResponseDto;
import in.aravinda_holla.fakestore1.models.Product;
import in.aravinda_holla.fakestore1.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductDetails(@PathVariable("id") int productId) {
        return productService.getSingleProduct(productId);
    }

    @PostMapping("/products")
    public ProductResponseDto createNewProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productService.addProduct(
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImage(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice()
        );
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts() {
        return productService.getProducts();
    }

    @PutMapping("/products/{id}")
    public ProductResponseDto updateProduct(@PathVariable("id") int id, @RequestBody ProductRequestDto productRequestDto) {
        return productService.updateProduct(
                id,
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImage(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice()
        );
    }

    @DeleteMapping("/products/{id}")
    public ProductResponseDto deleteProduct(@PathVariable("id") int id) {
        return productService.deleteProduct(id);
    }
}
