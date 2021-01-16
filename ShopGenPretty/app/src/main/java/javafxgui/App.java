package javafxgui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/*
 * This is the main app menu. Idiot-proof.
 * Additional work needed: Could be made to look more attractive with the use of CSS and/or HTML.
 * Comments needed. Fix alignment of exit button, with use of 5 columns?
 */
public class App extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Shopping List Generator");

        Button btn1 = new Button();
        btn1.setText("Add Recipe");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Adding recipe...");
                addRecipe(); }
        });

        Button btn2 = new Button();
        btn2.setText("Make Shopping List");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) { System.out.println("Making new list...");
            try { makeShopList(); }
            catch (IOException e) { e.printStackTrace(); }
            }
        });

        Button btn3 = new Button();
        btn3.setText("Exit");
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){ primaryStage.close(); }
        });
        
        GridPane root = new GridPane();
        root.getColumnConstraints().add(new ColumnConstraints(50));
        root.getColumnConstraints().add(new ColumnConstraints(200));
        root.getColumnConstraints().add(new ColumnConstraints(200));
        root.getColumnConstraints().add(new ColumnConstraints(50));
        root.getRowConstraints().add(new RowConstraints(50));
        root.getRowConstraints().add(new RowConstraints(50));
        root.getRowConstraints().add(new RowConstraints(50));
        root.getRowConstraints().add(new RowConstraints(50));

        root.add(btn1,1,1);
        root.add(btn2,2,1);
        root.add(btn3,1,2,2,1);
        root.setHalignment(btn1, HPos.CENTER);
        root.setHalignment(btn2, HPos.CENTER);
        root.setHalignment(btn3, HPos.CENTER);
        primaryStage.setScene(new Scene(root, 500, 200));
        primaryStage.show();
    }


/*
 * This is the menu that allows the user to add in additional recipes to be saved by the app.
 * Additional work needed: Comments should be added to clarify code, and a different method of entering ingredients
 * could be found. Better method of entering the serving size of each recipe could be found, to prevent format errors.
 * Could be made to look more attractive with the use of CSS and/or HTML. NOT idiot-proof.
 */
    public void addRecipe(){
        Stage stage2 = new Stage();
        stage2.setTitle("Add Recipe");

        //take ui for recipe name
        TextField name = new TextField();
        name.setPromptText("Enter the recipe name");
        Label forName = new Label("Recipe Name:");


        //take ui for recipe servings num
        TextField serv = new TextField();
        serv.setPromptText("Enter the serving size");
        Label forServ = new Label("Serving Size:");


        //take ui for ingredients and quantities
        TextArea ing = new TextArea();
        ing.setPrefColumnCount(2);
        ing.setWrapText(false);
        ing.setPromptText("Enter the ingredients");
        Label forIng = new Label("Ingredients:\n"+"\nEnter the quantity, followed by a comma,"+
                " followed by the unit and ingredient. Example: 1, cups rice. "+
                "For oil and spices, enter no units and 0 as the quantity.");
        forIng.setWrapText(true);

        //save and exit button
        Button exit = new Button();
        exit.setText("Done");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){
                try {
                    FileWriter input = new FileWriter("recipes.txt",true);
                    input.write(name.getText()+"\n");
                    input.write(serv.getText()+"\n");
                    for(CharSequence line : ing.getParagraphs()){
                        input.write(line+"\n");
                    }
                    input.write("*\n");
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Recipe added!");
                stage2.close();}
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
        root.setValignment(forName, VPos.TOP);
        root.add(name,3,1);
        root.setValignment(name, VPos.TOP);
        root.add(forServ,1,2);
        root.setValignment(forServ, VPos.TOP);
        root.add(serv,3,2);
        root.setValignment(serv, VPos.TOP);
        root.add(forIng,1,3);
        root.setValignment(forIng, VPos.TOP);
        root.add(ing,3,3);
        root.add(exit,1,4,3,1);
        root.setHalignment(exit, HPos.CENTER);
        root.setValignment(exit, VPos.BOTTOM);

        stage2.setScene(new Scene(root,715,500));
        stage2.show();

    }

/*
 * This is the menu that allows a use to select their recipes and actually generate the shopping list.
 * Additional work needed: Better formatting for the buttons should be used. A way to display the meals currently
 * added to the meal plan should be included. Could also include a reset button. Comments should be added for
 * clarification. Could be made to look more attractive with CSS and/or HTML.
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

        ArrayList<Button> bs = new ArrayList();
        for(String s : rb.open()){
            Button b = new Button();
            b.setText(s+"("+rb.get(s).getServings()+")");
            b.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    mp.addRecipe(rb.get(s));
                //add to whatever display method is chosen
                }
            });
            bs.add(b);
        }

        //display somewhere the recipes and serving number on list
        Label display = new Label("This is a placeholder for a potential display panel");
        display.setWrapText(true);

        Button exit = new Button();
        exit.setText("Print & Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){
                try{
                    FileWriter output = new FileWriter("ShopList.txt");
                    output.write(mp.getName()+"\n");
                    output.write("Meals: ");
                    for(String s : mp.getMeals()){ output.write(s + ", "); }
                    output.write("\nServings: "+mp.getServings()+"\n");
                    for(String key : mp.getShopList().keySet()){
                        output.write(mp.getShopList().get(key)+key+"\n");}
                    output.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                stage3.close();
                System.out.println("Grocery list printed!");
            }
        });

        root.add(display,4,1,1,4);
        root.setValignment(display,VPos.TOP);

        for(Button btn : bs){
            root.add(btn,1+(bs.indexOf(btn)%3),1+(bs.indexOf(btn)/3));
            root.setHalignment(btn, HPos.CENTER);
        }

        root.add(exit,0,2+(bs.size()/3),5,1);
        root.setHalignment(exit,HPos.CENTER);

        stage3.setScene(new Scene(root,830,550));
        stage3.show();
    }
}
