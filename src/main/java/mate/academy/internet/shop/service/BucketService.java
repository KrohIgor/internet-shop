package mate.academy.internet.shop.service;

import mate.academy.internet.shop.model.Bucket;
import mate.academy.internet.shop.model.Item;

public interface BucketService {

    Bucket addItem(Long bucketId, Long itemId);
}
