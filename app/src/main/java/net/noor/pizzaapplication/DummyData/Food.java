package net.noor.pizzaapplication.DummyData;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nsamir on 7/2/2019.
 */
public class Food implements Parcelable {



    public enum FoodType {
        PIZZA, ICE_CREAM, BURGER, DRINKS;
    }

    private FoodType foodType;
    private String title;
    private List<Calories> calories;
    private int price;
    private int numOfItems;

    public Food(FoodType foodType, String title, List<Calories> calories, int price, int numOfItems) {
        this.foodType = foodType;
        this.title = title;
        this.calories = calories;
        this.price = price;
        this.numOfItems = numOfItems;
    }


    protected Food(Parcel in) {
        title = in.readString();
        calories = in.createTypedArrayList(Calories.CREATOR);
        price = in.readInt();
        numOfItems = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeTypedList(calories);
        dest.writeInt(price);
        dest.writeInt(numOfItems);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public FoodType getFoodType() {
        return foodType;
    }

    public List<Calories> getCalories() {
        return calories;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public int getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    //**************************************************************************

    public static Food getDummyPizza() {
        List<Calories> calories = new ArrayList<>();
        calories.add(new Calories("Total Fat", 150, 28));
        calories.add(new Calories("Total Carb", 275, 52));
        calories.add(new Calories("Protein", 105, 20));

        return new Food(FoodType.PIZZA, "Green Goddess pizza", calories, 60, 3);
    }

    public static Food getDummyBurger() {
        List<Calories> calories = new ArrayList<>();
        calories.add(new Calories("Total Fat", 150, 28));
        calories.add(new Calories("Total Carb", 275, 52));
        calories.add(new Calories("Protein", 105, 20));

        return new Food(FoodType.BURGER, "Mushroom Burger", calories, 150, 1);
    }

    public static Food getDummyDrink() {
        List<Calories> calories = new ArrayList<>();
        calories.add(new Calories("Total Fat", 150, 28));
        calories.add(new Calories("Total Carb", 275, 52));
        calories.add(new Calories("Protein", 105, 20));

        return new Food(FoodType.DRINKS, "Coffee", calories, 150, 1);
    }

    public static List<Food> getDummyFoodList() {
        List<Food> foods = new ArrayList<>();
        foods.add(getDummyPizza());
        foods.add(getDummyBurger());
        foods.add(getDummyDrink());
        return foods;
    }

}
