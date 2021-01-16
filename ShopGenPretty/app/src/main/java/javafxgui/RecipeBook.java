package javafxgui;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/*
The RecipeBook class stores all the recipes that the program knows,
and allows the user to select them by name. Since only one recipe book
will be needed for each time the program runs, I have opted to make use of
the singleton pattern here.
 */
public class RecipeBook {
    private static RecipeBook book;
    private HashMap<String,Recipe> pages;

    private RecipeBook(){
        this.pages = new HashMap<>();
    }

    public static RecipeBook createBook() throws IOException {
        book = new RecipeBook();
        book.fillBook();
        return book;
    }

    public void add(Recipe r){
        pages.put(r.getName(),r);
    }

    public Recipe get(String n){
        if(pages.containsKey(n)){
            return pages.get(n);
        }
        else{
            System.out.println("That recipe is not in this book, sorry.");
            return null;
        }
    }

    //method modified to suit new gui
    public String[] open(){
        String[] ns = pages.keySet().toArray(new String[pages.size()]);
        return ns;
    }

    private void fillBook() throws IOException {
        FileReader fr = new FileReader("recipes.txt");
        int i;
        String tempN = ""; //temp variable to hold the recipe name
        String tempS = ""; //temp variable to hold recipe serving size
        HashMap<String,Integer> tempL = new HashMap<>();
        boolean name = false;
        boolean serv = false;

        String tempK = ""; //temp variable to hold the ingredient name
        String tempV = ""; //temp variable to hold ingredient quantity
        boolean val = false;

        while((i = fr.read()) != -1){
            char c = (char)i;

            if(!name){
                if(c == '\n'){
                    if(tempN != "") {
                        name = true;
                    }
                }
                else{
                    tempN += c;
                }
            }

            else if(!serv){
                if(c == '\n'){
                    serv = true;
                }
                else{
                    tempS += c;
                }
            }

            else if(c != '*'){
                if (c == ',') {
                    val = true;
                } else if (!val) {
                    tempV += c;
                } else if (c != '\n') {
                    tempK += c;
                } else {
                    tempL.put(tempK, Integer.parseInt(tempV));
                    val = false;
                    tempV = "";
                    tempK = "";
                }
            }

            else {
                book.add(new Recipe(tempN,Integer.parseInt(tempS),tempL));
                tempN = "";
                name = false;
                tempS = "";
                serv = false;
                tempL = new HashMap<>();
            }
        }
        fr.close();
    }
}
