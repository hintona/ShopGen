package javafxgui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import static javafx.scene.text.Font.getFontNames;

/*
 * This is the main app menu.
 * Additional work needed: Comments needed. Fix alignment of exit button, with use of 5 columns?
 */
public class App extends Application {
    public static void main(String[] args) { launch(args); }

    public Font APPFONT;  //variable to set font
    public Color BACKGROUNDCOLOR; //variable to set background colour
    public Color FONTCOLOR; //variable to set font color

    @Override
    public void start(Stage primaryStage) throws IOException {

        //here set up the initial colour and fonts by reading from the settings file
        FileReader fr = new FileReader("settings.txt");
        int i;
        while((i = fr.read()) != -1){
            //read into font
        }
        while((i = fr.read()) != -1){
            //read into backgroundcolor
        }
        while((i = fr.read()) != -1){
            //read into fontcolor
        }

        primaryStage.setTitle("Shopping List Generator"); //font?
        primaryStage.setResizable(false);

        Button rBtn = new Button();
        //rBtn.setFont(APPFONT);
        //rBtn.setTextFill(FONTCOLOR);
        rBtn.setText("Add Recipe");
        rBtn.setOnAction(event -> {
            System.out.println("Adding recipe...");
            addRecipe();
        });

        Button lBtn = new Button();
        //lBtn.setFont(APPFONT);
        //lBtn.setTextFill(FONTCOLOR);
        lBtn.setText("Make Shopping List");
        lBtn.setOnAction(event -> {
            System.out.println("Making new list...");
            try {
                makeShopList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button sBtn = new Button();
        //sBtn.setFont(APPFONT);
        //sBtn.setTextFill(FONTCOLOR);
        sBtn.setText("Settings");
        sBtn.setOnAction(event -> {
            try {
                openSettings();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button eBtn = new Button();
        //eBtn.setFont(APPFONT);
        //eBtn.setTextFill(FONTCOLOR);
        eBtn.setText("Exit");
        eBtn.setOnAction(event -> primaryStage.close());
        
        GridPane root = new GridPane();
        root.getColumnConstraints().add(new ColumnConstraints(50));
        root.getColumnConstraints().add(new ColumnConstraints(200));
        root.getColumnConstraints().add(new ColumnConstraints(200));
        root.getColumnConstraints().add(new ColumnConstraints(50));
        root.getRowConstraints().add(new RowConstraints(50));
        root.getRowConstraints().add(new RowConstraints(50));
        root.getRowConstraints().add(new RowConstraints(50));
        root.getRowConstraints().add(new RowConstraints(50));

        root.add(rBtn,1,1);
        root.add(lBtn,2,1);
        root.add(sBtn,1,2);
        root.add(eBtn,2,2);

        GridPane.setHalignment(rBtn, HPos.CENTER);
        GridPane.setHalignment(lBtn, HPos.CENTER);
        GridPane.setHalignment(sBtn, HPos.CENTER);
        GridPane.setHalignment(eBtn, HPos.CENTER);
        primaryStage.setScene(new Scene(root, 500, 200));
        primaryStage.show();
    }


/*
 * This is the menu that allows the user to add in additional recipes to be saved by the app.
 * Additional work needed: Comments should be added to clarify code, and a different method of entering ingredients
 * could be found. Better method of entering the serving size of each recipe could be found, to prevent format errors.
 */
    public void addRecipe(){
        Stage stage2 = new Stage();
        stage2.setTitle("Add Recipe");

        //take ui for recipe name
        TextField name = new TextField();
        //name.setFont(APPFONT);
        name.setPromptText("Enter the recipe name");
        Label forName = new Label("Recipe Name:");


        //take ui for recipe servings num
        TextField serv = new TextField();
        //serv.setFont(APPFONT);
        serv.setPromptText("Enter the serving size");
        Label forServ = new Label("Serving Size:");


        //take ui for ingredients and quantities
        TextArea ing = new TextArea();
        //ing.setFont(APPFONT);
        ing.setPrefColumnCount(2);
        ing.setWrapText(false);
        ing.setPromptText("Enter the ingredients");
        Label forIng = new Label("Ingredients:\n"+"\nEnter the quantity, followed by a comma,"+
                " followed by the unit and ingredient. Example: 1, cups rice. "+
                "For oil and spices, enter no units and 0 as the quantity.");
        forIng.setWrapText(true);

        //save and exit button
        Button exit = new Button();
        //exit.setFont(APPFONT);
        //exit.setTextFill(FONTCOLOR);
        exit.setText("Done");
        exit.setOnAction(event -> {
            try {
                FileWriter input = new FileWriter("recipes.txt", true);
                input.write(name.getText() + "\n");
                input.write(serv.getText() + "\n");
                for (CharSequence line : ing.getParagraphs()) {
                    input.write(line + "\n");
                }
                input.write("*\n");
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Recipe added!");
            stage2.close();
        });

        GridPane root = new GridPane();

        root.getColumnConstraints().add(new ColumnConstraints(50)); //col 0, left buffer
        root.getColumnConstraints().add(new ColumnConstraints(300)); //col 1, label column
        root.getColumnConstraints().add(new ColumnConstraints(15)); //col 2, middle buffer
        root.getColumnConstraints().add(new ColumnConstraints(300));// col 3, text column
        root.getColumnConstraints().add(new ColumnConstraints(50)); // col 4, right buffer

        root.getRowConstraints().add(new RowConstraints(50)); //row 0, top buffer
        root.getRowConstraints().add(new RowConstraints(50)); //row 1, name row
        root.getRowConstraints().add(new RowConstraints(50)); //row 2, serving row
        root.getRowConstraints().add(new RowConstraints(250)); //row 3, ingredients row
        root.getRowConstraints().add(new RowConstraints(50));  //row 4, exit row
        root.getRowConstraints().add(new RowConstraints(50)); //row 5, bottom buffer

        root.add(forName,1,1);
        GridPane.setValignment(forName, VPos.TOP);
        root.add(name,3,1);
        GridPane.setValignment(name, VPos.TOP);
        root.add(forServ,1,2);
        GridPane.setValignment(forServ, VPos.TOP);
        root.add(serv,3,2);
        GridPane.setValignment(serv, VPos.TOP);
        root.add(forIng,1,3);
        GridPane.setValignment(forIng, VPos.TOP);
        root.add(ing,3,3);
        root.add(exit,1,4,3,1);
        GridPane.setHalignment(exit, HPos.CENTER);
        GridPane.setValignment(exit, VPos.BOTTOM);

        stage2.setScene(new Scene(root,715,500));
        stage2.show();

    }

/*
 * This is the menu that allows a use to select their recipes and actually generate the shopping list.
 * Additional work needed: Better formatting for the buttons should be used. A way to display the meals currently
 * added to the meal plan should be included. Could also include a reset button. Comments should be added for
 * clarification.
 */
    public void makeShopList() throws IOException {
        Stage stage3 = new Stage();
        stage3.setTitle("Make Shopping List");
        RecipeBook rb = RecipeBook.createBook();
        MealPlan mp = new MealPlan("mp");

        GridPane root = new GridPane();
        root.setVgap(15);
        root.getRowConstraints().add(new RowConstraints(30)); //row 0, top buffer
        root.getColumnConstraints().add(new ColumnConstraints(30));//col 0, left buffer
        root.getColumnConstraints().add(new ColumnConstraints(200));//col 1, button display
        root.getColumnConstraints().add(new ColumnConstraints(200));//col 2, button display
        root.getColumnConstraints().add(new ColumnConstraints(200));//col 3, button display
        root.getColumnConstraints().add(new ColumnConstraints(200));//col 4, dialogue box slash right buffer

        ArrayList<Button> bs = new ArrayList<>();
        for(String s : rb.open()){
            Button b = new Button();
            //b.setFont(APPFONT);
            //b.setTextFill(FONTCOLOR);
            b.setText(s+"("+rb.get(s).getServings()+")");
            b.setOnAction(event -> {
                mp.addRecipe(rb.get(s));
                //add to whatever display method is chosen
            });
            bs.add(b);
        }

        //display somewhere the recipes and serving number on list
        Label display = new Label("This is a placeholder for a potential display panel");
        //display.setFont(APPFONT);
        //display.setTextFill(FONTCOLOR);
        display.setWrapText(true);

        Button exit = new Button();
        //exit.setFont(APPFONT);
        //exit.setTextFill(FONTCOLOR);
        exit.setText("Print & Exit");
        exit.setOnAction(event -> {
            try {
                FileWriter output = new FileWriter("ShopList.txt");
                output.write(mp.getName() + "\n");
                output.write("Meals: ");
                for (String s : mp.getMeals()) {
                    output.write(s + ", ");
                }
                output.write("\nServings: " + mp.getServings() + "\n");
                for (String key : mp.getShopList().keySet()) {
                    output.write(mp.getShopList().get(key) + key + "\n");
                }
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage3.close();
            System.out.println("Grocery list printed!");
        });

        root.add(display,4,1,1,4);
        GridPane.setValignment(display,VPos.TOP);

        for(Button btn : bs){
            root.add(btn,1+(bs.indexOf(btn)%3),1+(bs.indexOf(btn)/3));
            GridPane.setHalignment(btn, HPos.CENTER);
        }

        root.add(exit,0,2+(bs.size()/3),5,1);
        GridPane.setHalignment(exit,HPos.CENTER);

        stage3.setScene(new Scene(root,830,550));
        stage3.show();
    }

    /*
     * This menu allows the user to change the appearance of the app.
     * Additional work needed: Comments needed. Menu currently does nothing.
     */
    public void openSettings() throws IOException {
        Stage stage4 = new Stage();
        stage4.setTitle("Settings (Under Construction)");
        GridPane root = new GridPane();
        FileWriter fw = new FileWriter("settings.txt");

        Label inst = new Label("Select the colors and font that you want.\n"+
                "Changes will not take effect until program restarts.\n"+
                "Must save changes using the Exit button");
        //inst.setFont(APPFONT);
        //inst.setTextFill(FONTCOLOR);
        Label backgroundL = new Label("Background Color:");
        //backgroundL.setFont(APPFONT);
        //backgroundL.setTextFill(FONTCOLOR);
        Label fontL = new Label("Font Color:");
        //fontL.setFont(APPFONT);
        //fontL.setTextFill(FONTCOLOR);
        Label fontTL = new Label("Font:");
        //fontTL.setFont(APPFONT);
        //fontTL.setTextFill(FONTCOLOR);

        ColorPicker backgroundC = new ColorPicker();
        backgroundC.setOnAction(e -> BACKGROUNDCOLOR = backgroundC.getValue());

        ColorPicker fontC = new ColorPicker();
        fontC.setOnAction(e -> FONTCOLOR = fontC.getValue());


        ArrayList<MenuItem> options = new ArrayList<>();
        ArrayList<String> fonts = new ArrayList<>(getFontNames());
        for(String s: fonts){
            MenuItem m = new MenuItem(s);
            m.setText(s);
            m.setOnAction(event -> {
                //APPFONT = FONT.font();
            });
            options.add(m);
        }

        Menu menu = new Menu();
        menu.getItems().addAll(options);
        MenuBar fontT = new MenuBar(menu);

        Button exit = new Button("Exit");
        exit.setOnAction(e -> {
           //save current values to the file, overwriting it
           stage4.close();
        });
        //exit.setFont(APPFONT);
        //exit.setTextFill(FONTCOLOR);

        root.add(inst,1,1,2,1);
        GridPane.setHalignment(inst,HPos.CENTER);
        root.add(backgroundL,1,2);
        root.add(backgroundC,1,3);
        root.add(fontL,2,2);
        root.add(fontC,2,3);
        root.add(fontTL,3,2);
        root.add(fontT,3,3);
        root.add(exit,1,4,3,1);
        GridPane.setHalignment(exit,HPos.CENTER);

        root.setHgap(30);
        root.setVgap(30);

        stage4.setScene(new Scene(root,550,250));
        stage4.show();
    }
}