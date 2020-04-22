package mate.academy.internet.shop.service;

import mate.academy.internet.shop.model.Item;

public interface ItemService {

    Item create(Item item);

    Item get(Long id);

    Item update(Item item);

    void delete(Long id);

    void delete(Item item);
}
