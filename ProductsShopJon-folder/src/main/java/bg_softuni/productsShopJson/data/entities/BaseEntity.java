package bg_softuni.productsShopJson.data;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

    private Integer id;

    protected BaseEntity() {

    }

    public Integer getId() {
        return id;
    }
}
