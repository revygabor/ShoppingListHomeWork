package com.example.gbor.shoppinglisthomework.data;

public class ShoppingItem {
    public String name;
    public String description;
    public int price;
    public boolean bought;

    public enum Category {
        FOOD, ELECTRICAL, CLOTHES, BOOK, OTHER;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    };

    public Category category;
}
