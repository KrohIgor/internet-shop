package mate.academy.internet.shop.service;

import java.util.List;
import mate.academy.internet.shop.model.Product;

public interface ProductService {

    Product create(Product product);

    Product get(Long productId);

    List<Product> getAll();

    Product update(Product product);

    boolean delete(Long productId);
}
