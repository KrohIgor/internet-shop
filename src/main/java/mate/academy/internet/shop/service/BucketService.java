package mate.academy.internet.shop.service;

import mate.academy.internet.shop.model.Bucket;

public interface BucketService {

    Bucket addItem(Long bucketId, Long productId);
}
