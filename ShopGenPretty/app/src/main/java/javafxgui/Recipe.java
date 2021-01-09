package javafxgui;
import java.util.HashMap;

/*
The Recipe class stores the actual details of each recipe, including the name,
the list of ingredients needed, and how many servings it makes.
 */
public class Recipe {
    protected String name;
    protected int servings;
    protected HashMap ingr;

    public Recipe(String nm,int sv,HashMap ig){
        this.name = nm;
        this.servings = sv;
        this.ingr = ig;
    }

    public String getName() {
        return name;
    }

    public int getServings(){
        return servings;
    }

    public HashMap getIngr(){
        return ingr;
    }
}

