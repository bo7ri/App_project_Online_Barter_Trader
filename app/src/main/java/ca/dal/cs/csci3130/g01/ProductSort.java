package ca.dal.cs.csci3130.g01;

import java.util.Comparator;

public class ProductSort implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return p1.getTitle().toLowerCase().compareTo(p2.getTitle().toLowerCase());
    }

    @Override
    public Comparator<Product> reversed() {
        return Comparator.super.reversed();
    }
}
