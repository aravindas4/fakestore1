package in.aravinda_holla.fakestore1.repositories.projections;

import in.aravinda_holla.fakestore1.models.Category;

import java.math.BigDecimal;

public interface ProductProjection {
    Integer getId();
    String getTitle();
    String getDescription();
    BigDecimal getPrice();
    Category getCategory();
    String getImageUrl();
}
