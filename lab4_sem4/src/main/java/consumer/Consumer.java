package consumer;

import production.Product;

public interface Consumer {
    Product consume() throws InterruptedException;
}
