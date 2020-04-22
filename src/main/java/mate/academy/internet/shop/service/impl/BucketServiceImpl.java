package mate.academy.internet.shop.service.impl;

import mate.academy.internet.shop.dao.BucketDao;
import mate.academy.internet.shop.dao.ItemDao;
import mate.academy.internet.shop.model.Bucket;
import mate.academy.internet.shop.model.Item;
import mate.academy.internet.shop.service.BucketService;

//@Service
public class BucketServiceImpl implements BucketService {

    //@Dao
    private BucketDao bucketDao;
    //Dao
    private ItemDao itemDao;

    @Override
    public Bucket addItem(Long bucketId, Long itemId) {
        Bucket bucket = bucketDao.get(bucketId);
        Item item = itemDao.get(itemId);
        bucket.getItems().add(item);
        return bucketDao.update(bucket);
    }
}
