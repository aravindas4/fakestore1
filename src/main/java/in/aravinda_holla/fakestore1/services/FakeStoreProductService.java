package in.aravinda_holla.fakestore1.services;

import in.aravinda_holla.fakestore1.dtos.FakeStoreDto;
import in.aravinda_holla.fakestore1.dtos.ProductResponseDto;
import in.aravinda_holla.fakestore1.exceptions.ProductNotFoundException;
import in.aravinda_holla.fakestore1.models.Product;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("fakeStoreService")
public class FakeStoreProductService implements ProductService{

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(int productId) throws ProductNotFoundException{
        FakeStoreDto fakeStoreDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreDto.class
        );
        if (fakeStoreDto == null) {
            throwNotFound(productId);
        }
        return fakeStoreDto.toProduct();
    }

    public void throwNotFound(int productId) throws ProductNotFoundException {
        throw new ProductNotFoundException(
                "Product with Id:"+ productId + ". Try a product with id less than 21."
        );
    }

    @Override
    public Product addProduct(
            String title,
            String description,
            String imageUrl,
            String category,
            double price
    ) {
        FakeStoreDto fakeStoreDto = new FakeStoreDto();
        fakeStoreDto.setTitle(title);
        fakeStoreDto.setDescription(description);
        fakeStoreDto.setImage(imageUrl);
        fakeStoreDto.setCategory(category);
        fakeStoreDto.setPrice(price);
        FakeStoreDto response = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                fakeStoreDto,
                FakeStoreDto.class
        );

        return response.toProduct();
    }

    @Override
    public List<Product> getProducts() {
        ResponseEntity<FakeStoreDto[]> response = restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                FakeStoreDto[].class
        );
        List<Product> productDtos = new ArrayList<>();
        for(FakeStoreDto obj: response.getBody()) {
            productDtos.add(obj.toProduct());
        }
        return productDtos;
    }

    @Override
    public Product updateProduct(
            int id,
            String title,
            String description,
            String imageUrl,
            String category,
            double price
    ) throws ProductNotFoundException {
        FakeStoreDto fakeStoreDto = new FakeStoreDto();
        fakeStoreDto.setTitle(title);
        fakeStoreDto.setDescription(description);
        fakeStoreDto.setImage(imageUrl);
        fakeStoreDto.setCategory(category);
        fakeStoreDto.setPrice(price);
//        RequestCallback requestCallBack = restTemplate.httpEntityCallback(fakeStoreDto, FakeStoreDto.class);
//        ResponseExtractor<ResponseEntity<FakeStoreDto>> responseExtractor = restTemplate.responseEntityExtractor(
//                FakeStoreDto.class
//        );
//        ResponseEntity<FakeStoreDto> response = restTemplate.execute(
//                "https://fakestoreapi.com/products/{id}",
//                HttpMethod.PUT, requestCallBack, responseExtractor, id
//        );
//        FakeStoreDto fakeStoreDtoRes = response.getBody();
//        return fakeStoreDtoRes.toProduct();
        HttpEntity<FakeStoreDto> requestEntity = new HttpEntity<>(fakeStoreDto);
        FakeStoreDto response = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.PUT,
                requestEntity,
                FakeStoreDto.class
        ).getBody();

        if (response == null) {
            throwNotFound(id);
        }
        return response.toProduct();
    }

    @Override
    public Product replaceProduct(int id, String title, String description, String imageUrl, String category, double price) throws ProductNotFoundException {
        return null;
    }

    @Override
    public Product deleteProduct(int id) throws ProductNotFoundException {
//        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreDto.class);
//        ResponseExtractor<ResponseEntity<FakeStoreDto>> responseExtractor = restTemplate.responseEntityExtractor(
//                FakeStoreDto.class
//        );
//        ResponseEntity<FakeStoreDto> response = restTemplate.execute(
//                "https://fakestoreapi.com/products/{id}", HttpMethod.DELETE, requestCallback,
//                responseExtractor, id
//        );
//        FakeStoreDto fakeStoreDto = response.getBody();
//        return fakeStoreDto.toProduct();
        FakeStoreDto fakeStoreDto = restTemplate.exchange(
                "http://fakestoreapi.com/products/" + id,
                HttpMethod.DELETE,
                null,
                FakeStoreDto.class
        ).getBody();
        if (fakeStoreDto == null) {
            throwNotFound(id);
        }
        return fakeStoreDto.toProduct();
    }

    @Override
    public List<String> getCategories() {
        ResponseEntity<String[]> response = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/categories",
                String[].class
        );
        return Arrays.asList(response.getBody());
    }

    @Override
    public List<Product> getCategoryProducts(@PathVariable("title") String title) {
        ResponseEntity<FakeStoreDto[]> response = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/category/{title}",
                FakeStoreDto[].class, title
        );
        List<Product> productDtos = new ArrayList<>();
        for(FakeStoreDto obj: response.getBody()) {
            productDtos.add(obj.toProduct());
        }
        return productDtos;
    }


}
