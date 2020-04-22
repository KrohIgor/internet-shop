package mate.academy.internet.shop.service;

import mate.academy.internet.shop.model.Product;
import java.util.List;

public interface ProductService {

    Product create(Product product);

    Product get(Long productId);

    List<Product> getAll();

    Product update(Product product);

    boolean delete(Long productId);
}
