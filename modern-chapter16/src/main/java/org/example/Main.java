package org.example;

import java.util.Arrays;
import java.util.List;

import static org.example.Shop.test;

public class Main {
    public static void main(String[] args) {
      List<Shop> shops = Arrays.asList(new Shop("BestPrice"), new Shop("LetsSaveBig"), new Shop("MyFavoriteShop"), new Shop("BuyItAll"));
      test(shops);
    }
}
