package com.example.gbor.shoppinglisthomework.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;

@Entity(tableName = "shoppingitem")
public class ShoppingItem {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name="price")
    public int price;

    @ColumnInfo(name = "bought")
    public boolean bought;

    public enum Category {
        FOOD, ELECTRICAL, CLOTHES, BOOK, OTHER;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }

        @TypeConverter
        public static Category getByOrdinal(int ordinal) {
            Category ret = null;
            for (Category cat : Category.values()) {
                if (cat.ordinal() == ordinal) {
                    ret = cat;
                    break;
                }
            }
            return ret;
        }

        @TypeConverter
        public static int toInt(Category category) {
            return category.ordinal();
        }
    };

    @ColumnInfo(name = "category")
    public Category category;
}
