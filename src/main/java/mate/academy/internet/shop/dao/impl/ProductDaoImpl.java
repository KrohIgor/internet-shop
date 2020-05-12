package mate.academy.internet.shop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import mate.academy.internet.shop.dao.ProductDao;
import mate.academy.internet.shop.db.Storage;
import mate.academy.internet.shop.model.Product;

public class ProductDaoImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        Storage.add(product);
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Storage.products
                .stream()
                .filter(p -> p.getProductId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAll() {
        return Storage.products;
    }

    @Override
    public Product update(Product product) {
        IntStream.range(0, Storage.products.size())
                .filter(x -> product.getProductId().equals(Storage.products.get(x).getProductId()))
                .forEach(p -> Storage.products.set(p, product));
        return product;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.products.removeIf(p -> p.getProductId().equals(id));
    }
}
