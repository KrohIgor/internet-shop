package mate.academy.internet.shop.service.impl;

import mate.academy.internet.shop.dao.BucketDao;
import mate.academy.internet.shop.dao.ProductDao;
import mate.academy.internet.shop.lib.Inject;
import mate.academy.internet.shop.lib.Service;
import mate.academy.internet.shop.model.Bucket;
import mate.academy.internet.shop.model.Product;
import mate.academy.internet.shop.service.BucketService;
import java.util.NoSuchElementException;

@Service
public class BucketServiceImpl implements BucketService {

    @Inject
    private BucketDao bucketDao;
    @Inject
    private ProductDao productDao;

    @Override
    public Bucket addItem(Long bucketId, Long productId) {
        Bucket bucket = bucketDao.get(bucketId);
        Product product = productDao.get(productId)
                .orElseThrow(() -> new NoSuchElementException("Can't find item with id " + productId));
        bucket.getProducts().add(product);
        return bucketDao.update(bucket);
    }
}
