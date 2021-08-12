//Created By   : Mudith Perera
//IIT Reg.No   : 2019845
//UOW          : W1761165
//Created Date : 4th of April 2020
//Last Modified: 13th of April 2020

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PassengerQueue {
    private static String[] trainQueue = new String[42];
    private static List<String> passenger = new ArrayList();


    public void flushArray(List array){
        for(;;){
            if (array.isEmpty()){
                break;
            }else
                array.remove(0);
        }
    }


    public int getQueueLength(){
        // create instance of Random class
        Random rand = new Random();
        // Generate random integers in range 1 to 6
        int queueLen = rand.nextInt(6)+1;
        return queueLen;
    }

    public void add(ArrayList array) {
        //Creating a object from TrainStation class
        TrainStation object = new TrainStation();

        int queueLen = getQueueLength();
        Stage stage = new Stage();
        //Setting a title for the stage
        stage.setTitle("Adding Passengers");

        int x = 0, y = 0;
        GridPane gridPane = new GridPane();
        //horizontal gap in pixels
        gridPane.setHgap(20);
        //vertical gap in pixels
        gridPane.setVgap(20);
        //margins around the whole grid
        gridPane.setPadding(new Insets(60, 20, 20, 50));
        gridPane.setStyle("-fx-background-image: url('https://i.pinimg.com/564x/70/07/cc/7007cc1888691422f64be2c7ef16406c.jpg');-fx-background-color:Black");

        if (array.size()<queueLen){
            queueLen=array.size();
        }
        //creating a button array
        Button[] btnarray = new Button[queueLen];


        for (int i = 0; i < queueLen; i++) {
            btnarray[i] = new Button("" + (i + 1));
            btnarray[i].setStyle("-fx-focus-color: transparent;-fx-faint-focus-color: transparent;");

            int finalX = i;
            int finalY = i;

                btnarray[i].setOnAction(event -> {
                    btnarray[finalX].setStyle("-fx-background-color: #CB4335;-fx-text-fill: White;-jfx-disable-visual-focus: true;");
                    btnarray[finalX].setDisable(true);
                 if (array.isEmpty()){
                     System.out.println("Waiting room is empty!");
                 }else {
                     //Adding passengers to the queue
                     passenger.add((String) array.get(0));
                     System.out.println(array.get(0) +" added to the Train queue position "+btnarray[finalX].getText());
                     array.remove(0);
                 }

                });

            btnarray[i].setText("" + (i + 1));
            //x is column index and y is row index
            gridPane.add(btnarray[i], (x+1), (y+1));
            x += 1;
            //When x = 1 the row changes
            if (x == 1) {
                y += 1;
                x = 0;
            }
        }
        //adding a button to send customers to the trainQueue
        Button btnConfirm = new Button();
        btnConfirm.setText("Confirm");
        int finalQueueLen = queueLen;
        btnConfirm.setOnAction(event -> {
            for (int z =0; z< (passenger.size());z++){
                if (passenger.get(z).equals(Passenger.getName(passenger.get(z)))){
                    object.getAllTripDetails()[findSeat((String) passenger.get(z))-1][4]= String.valueOf(finalQueueLen);
                }
                trainQueue[findSeat((String) passenger.get(z))-1]= passenger.get(z);
                System.out.println(passenger.get(z)+" Moved to the train seat ");
            }
        });

        //adding a button to quit from the window
        Button btnQuit = new Button();
        btnQuit.setText("Close");
        btnQuit.setOnAction(event -> {
            flushArray(passenger);
            stage.close();
        });
        Label lblLayout = new Label("      Queue Positions");
        lblLayout.setStyle("-fx-text-fill: White;-fx-font-weight: bold;");
        gridPane.add(lblLayout, 2, 0);
        gridPane.add(btnQuit, 6, 8);
        gridPane.add(btnConfirm,7,8);

        Scene scene = new Scene(gridPane, 500, 450);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void view() {
        Stage stage = new Stage();
        //Setting a title for the stage
        stage.setTitle("Passenger View");

        //Adding 42 button using a loop and changing the button layout
        int x = 0, y = 0;
        GridPane gridPane = new GridPane();
        //horizontal gap in pixels
        gridPane.setHgap(20);
        //vertical gap in pixels
        gridPane.setVgap(20);
        //margins around the whole grid
        gridPane.setPadding(new Insets(60, 20, 20, 50));

        gridPane.setStyle("-fx-background-image: url('https://i.pinimg.com/564x/70/07/cc/7007cc1888691422f64be2c7ef16406c.jpg');-fx-background-color:Black");
        //creating a button array
        Button[] btnarray = new Button[42];

        for (int i = 0; i < 42; i++) {
            btnarray[i] = new Button("" + (i + 1));
            btnarray[i].setStyle("-fx-focus-color: transparent;-fx-faint-focus-color: transparent;");

            int finalX = i;
            int finalY = i;

                //adding two buttons and two labels to visualize booked and available seats
                Button btnbook = new Button();
                Button btnfree = new Button();
                btnbook.setStyle("-fx-background-color: #CB4335;");
                btnfree.setStyle("-fx-background-color: #5DADE2;");

                Label lblbook = new Label("Added");
                Label lblfree = new Label("Empty");
                lblbook.setStyle("-fx-text-fill: White;");
                lblfree.setStyle("-fx-text-fill: White;");


                gridPane.add(btnbook, 6, 1);
                gridPane.add(btnfree, 6, 2);
                gridPane.add(lblbook, 7, 1);
                gridPane.add(lblfree, 7, 2);

                if (trainQueue[finalX]!=null) {
                    btnarray[finalX].setStyle("-fx-background-color: #CB4335;-fx-text-fill: White;");
                    btnarray[finalX].setText((finalX+1)+"-"+trainQueue[finalX]);
                } else {
                    btnarray[finalX].setStyle("-fx-background-color: #5DADE2;-fx-text-fill: Black;");
                    btnarray[finalX].setText((finalX+1)+"- Empty");
                }



            //x is column index and y is row index
            gridPane.add(btnarray[i], (x+1), (y+1));
            x += 1;
            //When x = 4 the row changes
            if (x == 4) {
                y += 1;
                x = 0;
            } else if (y == 10) {
                x = 3;
            }
        }
        //adding a button to quit from the window
        Button btnQuit = new Button();
        btnQuit.setText("Close");
        btnQuit.setOnAction(event -> {
            stage.close();
        });
        Label lblLayout = new Label("      Train Queue Positions");
        lblLayout.setStyle("-fx-text-fill: White;-fx-font-weight: bold;");
        gridPane.add(lblLayout, 2, 0);
        gridPane.add(btnQuit, 7, 10);

        Scene scene = new Scene(gridPane, 700, 800);
        stage.setScene(scene);
        stage.showAndWait();
    }
    public static int findSeat(String name) {
        TrainStation object = new TrainStation();
        int x =0;
        for (int i = 0; i < 42; i++) {
            if ((object.getAllTripDetails()[i][0] != null) && (object.getAllTripDetails()[i][0]).equalsIgnoreCase(name)) {
                 x = Integer.parseInt(object.getAllTripDetails()[i][2]);
            }
        }
        return x;
    }

    public static void deleteCustomer(String name) {
        int intSeat = (findSeat(name) - 1);
        if (trainQueue[intSeat] == null) {
            System.out.println("This seat is already empty! ");
        } else {
            System.out.println(trainQueue[intSeat] + " is removed from seat " + findSeat(trainQueue[intSeat]));
            trainQueue[intSeat] = null;

        }
    }

    public static void storeDetails(String trip, int date){
        //Initializing the connection with MongoDB
        try {
            //Hiding the mongodb logger details
            Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
            mongoLogger.setLevel(Level.SEVERE);

            //Initializing the client details
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            //Creating a new Database
            MongoDatabase database = mongoClient.getDatabase("TrainDetailsMud");

            //Creating a new collection for trips
            MongoCollection collection = database.getCollection("Date "+date+" Trip "+trip+" Train Queue");
            collection.drop();
            MongoCollection collectionNew = database.getCollection("Date "+date+" Trip "+trip+" Train Queue");
            for (int i = 0; i < 42; i++) {
                String name = trainQueue[i];
                Document namesAndSeats = new Document("name", name);
                collectionNew.insertOne(namesAndSeats);
            }
            System.out.println("Train Queue details Successfully Saved");
            System.out.println();


        }catch (Exception e){
            System.out.println("Data Saving unsuccessful");
            System.out.println();
        }
    }

    public static void getDetails(String trip, int date){
        try{
            //Connection details
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            //Selecting the database
            MongoDatabase database = mongoClient.getDatabase("TrainDetailsMud");

            //Creating the collection
            MongoCollection collection = database.getCollection("Date "+date+" Trip "+trip+" Train Queue");

            //Disabling the connection log messages
            Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
            Logger mongoCom = Logger.getLogger("com.mongodb");
            mongoCom.setLevel(Level.OFF);
            mongoLogger.setLevel(Level.OFF);

            //Creating a cursor to get document data from database
            MongoCursor<Document> cursor = collection.find().iterator();
            for (int i = 0; i < 42; i++) {
                //line by line will be retrieve to doc
                Document doc = cursor.next();
                trainQueue[i]= doc.getString("name");

            }
            System.out.println("Customer details Successfully Loaded");
            System.out.println();
        }catch (Exception e){
            System.out.println("Data loading failed");
            System.out.println();
        }
    }
}
