package in.aravinda_holla.fakestore1.services;

import in.aravinda_holla.fakestore1.dtos.FakeStoreDto;
import in.aravinda_holla.fakestore1.dtos.ProductResponseDto;
import in.aravinda_holla.fakestore1.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@Service("fakeStoreService")
//public class FakeStoreCategoryService implements CategoryService{
//    private RestTemplate restTemplate;
//
//    public FakeStoreCategoryService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//
//}
