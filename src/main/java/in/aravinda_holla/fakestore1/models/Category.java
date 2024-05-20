package in.aravinda_holla.fakestore1.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Category extends BaseModel {
    private String title;
    @OneToMany(mappedBy = "category")
    List<Product> products;
}
