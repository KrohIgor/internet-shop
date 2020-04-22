package mate.academy.internet.shop.service.impl;

import mate.academy.internet.shop.dao.ItemDao;
import mate.academy.internet.shop.model.Item;
import mate.academy.internet.shop.service.ItemService;

//@Service
public class ItemServiceImpl implements ItemService {

    //@Dao
    private ItemDao itemDao;

    @Override
    public Item create(Item item) {
        return itemDao.create(item);
    }

    @Override
    public Item get(Long id) {
        return itemDao.get(id);
    }

    @Override
    public Item update(Item item) {
        return itemDao.update(item);
    }

    @Override
    public void delete(Long id) {
        itemDao.delete(id);
    }

    @Override
    public void delete(Item item) {
        itemDao.delete(item);
    }
}
