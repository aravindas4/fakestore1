package in.aravinda_holla.fakestore1.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}
