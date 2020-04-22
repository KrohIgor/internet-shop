package mate.academy.internet.shop;

import mate.academy.internet.shop.lib.Injector;
import mate.academy.internet.shop.model.Product;
import mate.academy.internet.shop.service.ProductService;

public class Application {

    private static Injector injector = Injector.getInstance("mate.academy.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        Product product1 = new Product("Apple", 5.5);
        Product product2 = new Product("Tomato", 10.5);
        Product product3 = new Product("Cucumber", 7.5);
        productService.create(product1);
        productService.create(product2);
        productService.create(product3);

        for (Product product : productService.getAll()) {
            System.out.println(product);
        }
        System.out.println();

        product2.setName("Watermelon");
        product2.setPrice(15.0);
        productService.update(product2);
        for (Product product : productService.getAll()) {
            System.out.println(product);
        }
        System.out.println();

        productService.delete(product2.getId());
        for (Product product : productService.getAll()) {
            System.out.println(product);
        }
    }
}
