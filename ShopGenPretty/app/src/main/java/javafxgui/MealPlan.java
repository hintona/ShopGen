package javafxgui;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/*
The MealPlan class stores the recipes that the user has selected and
puts all the corresponding ingredients into one shopping list for the user.
 */
public class MealPlan {
    protected int servings;
    protected String name;
    protected ArrayList<String> meals;
    protected HashMap<String,Integer> shopList;
    protected Merge mg;

    public MealPlan(String nm) {
        this.name = nm;
        this.servings = 0;
        this.meals = new ArrayList<String>();
        this.shopList = new HashMap<String,Integer>();
        this.mg = new Merge();
    }

    public int getServings() {
        return servings;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getMeals() {
        return meals;
    }

    public HashMap<String,Integer> getShopList() {
        return shopList;
    }

    public void addRecipe(Recipe r) {
        if(r != null){
            servings += r.getServings();
            meals.add(r.getName());
            shopList = mg.mergeMaps(shopList, r.getIngr());
        }
    }
}

class Merge{
    public HashMap mergeMaps(HashMap<String,Integer> m1, HashMap<String,Integer> m2){
        String[] items = m2.keySet().toArray(new String[m2.size()]);
        HashMap<String,Integer> m = new HashMap<String,Integer>();
        for(int i = 0;i < items.length-1;i++){
            if(m1.containsKey(items[i])){
                int temp = m1.remove(items[i]) + m2.get(items[i]);
                m.put(items[i],temp);
            }
            else{
                m.put(items[i],m2.get(items[i]));
            }
        }
        m.putAll(m1);
        return m;
    }
}
