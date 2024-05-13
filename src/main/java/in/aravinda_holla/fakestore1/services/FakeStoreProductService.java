package in.aravinda_holla.fakestore1.services;

import in.aravinda_holla.fakestore1.dtos.FakeStoreDto;
import in.aravinda_holla.fakestore1.dtos.ProductResponseDto;
import in.aravinda_holla.fakestore1.models.Product;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ProductResponseDto getSingleProduct(int productId) {
        FakeStoreDto fakeStoreDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreDto.class
        );
        return fakeStoreDto.toProductResponseDto();
    }

    @Override
    public ProductResponseDto addProduct(
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

        return response.toProductResponseDto();
    }

    @Override
    public List<ProductResponseDto> getProducts() {
        ResponseEntity<FakeStoreDto[]> response = restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                FakeStoreDto[].class
        );
        List<ProductResponseDto> productDtos = new ArrayList<>();
        for(FakeStoreDto obj: response.getBody()) {
            productDtos.add(obj.toProductResponseDto());
        }
        return productDtos;
    }

    @Override
    public ProductResponseDto updateProduct(
            int id,
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
        RequestCallback requestCallBack = restTemplate.httpEntityCallback(fakeStoreDto, FakeStoreDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreDto>> responseExtractor = restTemplate.responseEntityExtractor(
                FakeStoreDto.class
        );
        ResponseEntity<FakeStoreDto> response = restTemplate.execute(
                "https://fakestoreapi.com/products/{id}",
                HttpMethod.PUT, requestCallBack, responseExtractor, id
        );
        FakeStoreDto fakeStoreDtoRes = response.getBody();
        return fakeStoreDtoRes.toProductResponseDto();
    }

    @Override
    public ProductResponseDto deleteProduct(int id) {
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreDto>> responseExtractor = restTemplate.responseEntityExtractor(
                FakeStoreDto.class
        );
        ResponseEntity<FakeStoreDto> response = restTemplate.execute(
                "https://fakestoreapi.com/products/{id}", HttpMethod.DELETE, requestCallback,
                responseExtractor, id
        );
        FakeStoreDto fakeStoreDto = response.getBody();
        return fakeStoreDto.toProductResponseDto();
    }
}
