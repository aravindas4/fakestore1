package in.aravinda_holla.fakestore1.controllers;

import in.aravinda_holla.fakestore1.dtos.ErrorDto;
import in.aravinda_holla.fakestore1.dtos.ProductRequestDto;
import in.aravinda_holla.fakestore1.dtos.ProductResponseDto;
import in.aravinda_holla.fakestore1.exceptions.ProductNotFoundException;
import in.aravinda_holla.fakestore1.models.Product;
import in.aravinda_holla.fakestore1.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;
    private ModelMapper modelMapper;

    public ProductController(@Qualifier("selfProductService") ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getProductDetails(@PathVariable("id") int productId)
            throws ProductNotFoundException {
        Product product = productService.getSingleProduct(productId);
        return new ResponseEntity<>(convertToProductResponseDto(product), HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createNewProduct(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.addProduct(
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImage(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice()
        );
        return new ResponseEntity<>(convertToProductResponseDto(product), HttpStatus.CREATED);
    }

    private ProductResponseDto convertToProductResponseDto(Product product) {
        String categoryTitle = product.getCategory().getTitle();
        ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
        productResponseDto.setCategory(categoryTitle);
        return productResponseDto;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<Product> products = productService.getProducts();
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product obj: products) {
            productResponseDtos.add(convertToProductResponseDto(obj));
        }
        return new ResponseEntity<>(productResponseDtos, HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> replaceProduct(@PathVariable("id") int id, @RequestBody ProductRequestDto productRequestDto)
    throws ProductNotFoundException {
        Product product = productService.replaceProduct(
                id,
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImage(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice()
        );
        return new ResponseEntity<>(convertToProductResponseDto(product), HttpStatus.OK);
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") int id, @RequestBody ProductRequestDto productRequestDto)
            throws ProductNotFoundException {
        Product product = productService.updateProduct(
                id,
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImage(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice()
        );
        return new ResponseEntity<>(convertToProductResponseDto(product), HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable("id") int id)
    throws ProductNotFoundException{
        Product product = productService.deleteProduct(id);
        return new ResponseEntity<>(convertToProductResponseDto(product), HttpStatus.OK);
    }

//    @ExceptionHandler(ProductNotFoundException.class)
//    public ResponseEntity<ErrorDto> handleProductNotFoundException(ProductNotFoundException exception) {
//        ErrorDto errorDto = new ErrorDto();
//        errorDto.setMessage(exception.getMessage());
//        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
//    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = productService.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/categories/{title}")
    public ResponseEntity<List<ProductResponseDto>> getCategoryProducts(@PathVariable("title") String title) {
        List<ProductResponseDto> responses = new ArrayList<>();
        for (Product obj: productService.getCategoryProducts(title)) {
            responses.add(convertToProductResponseDto(obj));
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
