package mate.academy.internet.shop.dao.impl;

import mate.academy.internet.shop.dao.BucketDao;
import mate.academy.internet.shop.dao.Storage;
import mate.academy.internet.shop.model.Bucket;

import java.util.NoSuchElementException;

public class BucketDaoImpl implements BucketDao {
    @Override
    public Bucket create(Bucket bucket) {
        return null;
    }

    @Override
    public Bucket get(Long bucketId) {
        return Storage.buckets
                .stream()
                .filter(b -> b.getId().equals(bucketId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find bucket whith id " + bucketId));
    }

    @Override
    public Bucket update(Bucket bucket) {
        return null;
    }
}
