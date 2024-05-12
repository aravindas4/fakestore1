package in.aravinda_holla.fakestore1.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product {
    private int id;
    private String title;
    private String description;
    private double price;
    private Category category;
    private String imageUrl;
}

