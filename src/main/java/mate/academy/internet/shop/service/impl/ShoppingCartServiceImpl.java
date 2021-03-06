package mate.academy.internet.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.internet.shop.dao.ShoppingCartDao;
import mate.academy.internet.shop.lib.Inject;
import mate.academy.internet.shop.lib.Service;
import mate.academy.internet.shop.model.Product;
import mate.academy.internet.shop.model.ShoppingCart;
import mate.academy.internet.shop.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        if (shoppingCart.getShoppingCartId() == null) {
            shoppingCartDao.create(shoppingCart);
        }
        if (shoppingCart.getProducts() == null) {
            shoppingCart.setProducts(new ArrayList<>());
        }
        shoppingCart.getProducts().add(product);
        shoppingCartDao.update(shoppingCart);
        return shoppingCart;
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        boolean deletedProduct = shoppingCart.getProducts()
                .removeIf(p -> p.getProductId().equals(product.getProductId()));
        shoppingCartDao.update(shoppingCart);
        return deletedProduct;
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        if (shoppingCart.getProducts() != null) {
            shoppingCart.getProducts().clear();
        }
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return shoppingCartDao.getAll()
                .stream()
                .filter(s -> s.getUserId().equals(userId))
                .findFirst().get();
    }

    @Override
    public ShoppingCart create(ShoppingCart element) {
        return shoppingCartDao.create(element);
    }

    @Override
    public ShoppingCart get(Long elementId) {
        return shoppingCartDao.get(elementId).get();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return shoppingCartDao.getAll();
    }

    @Override
    public ShoppingCart update(ShoppingCart element) {
        return shoppingCartDao.update(element);
    }

    @Override
    public boolean delete(Long elementId) {
        return shoppingCartDao.delete(elementId);
    }
}
