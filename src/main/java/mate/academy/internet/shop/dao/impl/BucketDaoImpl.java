package mate.academy.internet.shop.dao.impl;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import mate.academy.internet.shop.dao.BucketDao;
import mate.academy.internet.shop.db.Storage;
import mate.academy.internet.shop.lib.Dao;
import mate.academy.internet.shop.model.Bucket;

@Dao
public class BucketDaoImpl implements BucketDao {

    @Override
    public Bucket create(Bucket bucket) {
        Storage.add(bucket);
        return bucket;
    }

    @Override
    public Bucket get(Long bucketId) {
        return Storage.buckets
                .stream().filter(b -> b.getId().equals(bucketId))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Can't find bucket whith id " + bucketId));
    }

    @Override
    public Bucket update(Bucket bucket) {
        IntStream.range(0, Storage.buckets.size())
                .filter(x -> bucket.getId().equals(Storage.buckets.get(x).getId()))
                .forEach(b -> Storage.buckets.set(b, bucket));
        return bucket;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.buckets.removeIf(b -> b.getId().equals(id));
    }
}
