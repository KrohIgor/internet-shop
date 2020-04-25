package mate.academy.internet.shop.service;

import java.util.List;

public interface GenericService<T, K> {

    T create(T element);

    T get(K elementId);

    List<T> getAll();

    T update(T element);

    boolean delete(K elementId);
}
