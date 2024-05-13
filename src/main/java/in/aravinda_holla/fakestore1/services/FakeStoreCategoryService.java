package in.aravinda_holla.fakestore1.services;

import in.aravinda_holla.fakestore1.dtos.FakeStoreDto;
import in.aravinda_holla.fakestore1.dtos.ProductResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FakeStoreCategoryService implements CategoryService{
    private RestTemplate restTemplate;

    public FakeStoreCategoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<String> getCategories() {
        ResponseEntity<String[]> response = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/categories",
                String[].class
        );
        return Arrays.asList(response.getBody());
    }

    public List<ProductResponseDto> getCategoryProducts(@PathVariable("title") String title) {
        ResponseEntity<FakeStoreDto[]> response = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/category/{title}",
                FakeStoreDto[].class, title
        );
        List<ProductResponseDto> productDtos = new ArrayList<>();
        for(FakeStoreDto obj: response.getBody()) {
            productDtos.add(obj.toProductResponseDto());
        }
        return productDtos;
    }
}
