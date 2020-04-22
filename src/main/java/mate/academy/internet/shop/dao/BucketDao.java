package mate.academy.internet.shop.dao;

import mate.academy.internet.shop.model.Bucket;

public interface BucketDao {

    Bucket create(Bucket bucket);

    Bucket get(Long bucketId);

    Bucket update(Bucket bucket);

    boolean delete(Long id);
}
