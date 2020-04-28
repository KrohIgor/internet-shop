package mate.academy.internet.shop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import mate.academy.internet.shop.dao.ShoppingCartDao;
import mate.academy.internet.shop.db.Storage;
import mate.academy.internet.shop.lib.Dao;
import mate.academy.internet.shop.model.ShoppingCart;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.add(shoppingCart);
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        return Storage.shoppingCarts
                .stream().filter(s -> s.getShoppingCartId().equals(id))
                .findFirst();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return Storage.shoppingCarts;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        IntStream.range(0, Storage.shoppingCarts.size())
                .filter(x -> shoppingCart.getShoppingCartId()
                        .equals(Storage.shoppingCarts.get(x).getShoppingCartId()))
                .forEach(s -> Storage.shoppingCarts.set(s, shoppingCart));
        return shoppingCart;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.shoppingCarts.removeIf(s -> s.getShoppingCartId().equals(id));
    }
}
