package mate.academy.internet.shop.dao;

import mate.academy.internet.shop.model.Bucket;

public interface BucketDao {
    //CRUD
    Bucket create(Bucket bucket);

    Bucket get(Long bucketId);

    Bucket update(Bucket bucket);
}
