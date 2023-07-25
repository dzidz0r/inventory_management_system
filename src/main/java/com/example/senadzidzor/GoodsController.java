package com.example.senadzidzor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GoodsController {
    private List<Goods> goodsList;  // Collection to store goods data
    private Stack<Goods> goodsStack;  // Stack for adding and removing goods

    public GoodsController() {
        goodsList = new ArrayList<>();
        goodsStack = new Stack<>();
    }

    // Method to add a new goods item
    public void addGoods(String name, String category, int quantity, double price) {

        Goods goods = new Goods(name, category, quantity, price);
        goodsList.add(goods);
        goodsStack.push(goods);
    }

    // Method to remove a goods item
    public void removeGoods(Goods goods) {
        goodsList.remove(goods);
        goodsStack.remove(goods);
    }

    // Method to retrieve goods by category
    public List<Goods> getGoodsByCategory(String category) {
        List<Goods> categoryGoods = new ArrayList<>();
        for (Goods goods : goodsList) {
            if (goods.getCategory().equalsIgnoreCase(category)) {
                categoryGoods.add(goods);
            }
        }
        return categoryGoods;
    }

    // Method to update goods information
    public void updateGoods(Goods goods, String name, String category, int quantity, double price) {
        goods.setName(name);
        goods.setCategory(category);
        goods.setQuantity(quantity);
        goods.setPrice(price);
    }

    // Other methods and operations related to managing goods

    // Getter for goods list
    public List<Goods> getGoodsList() {
        return goodsList;
    }

    // Getter for goods stack
    public Stack<Goods> getGoodsStack() {
        return goodsStack;
    }
}
