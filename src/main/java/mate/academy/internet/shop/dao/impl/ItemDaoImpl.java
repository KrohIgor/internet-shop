package mate.academy.internet.shop.dao.impl;

import mate.academy.internet.shop.dao.ItemDao;
import mate.academy.internet.shop.dao.Storage;
import mate.academy.internet.shop.model.Item;

import java.util.NoSuchElementException;

public class ItemDaoImpl implements ItemDao {

    @Override
    public Item create(Item item) {
        return null;
    }

    @Override
    public Item get(Long id) {
        return Storage.items
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find item with id " + id));
    }

    @Override
    public Item update(Item item) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void delete(Item item) {

    }
}
