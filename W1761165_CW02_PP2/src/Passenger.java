
//Created By   : Mudith Perera
//IIT Reg.No   : 2019845
//UOW          : W1761165
//Created Date : 4th of April 2020
//Last Modified: 13th of April 2020

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Passenger {

    public static String getName(String name) {
        TrainStation object = new TrainStation();
        String fName = null;
        for (int i = 0; i < 42; i++) {
            if ((object.getAllTripDetails()[i][0] != null) && (object.getAllTripDetails()[i][0]).equalsIgnoreCase(name)) {
                fName = object.getAllTripDetails()[i][0];
            }
        }
        return fName;
    }

    public static void setMinTime(String[][] array){
        int max = 0;
        int min = 100;
        int avg = 0;
        int total =0;
        for(int i =0; i < 42; i++){
            if (array[i][0]!= null && Integer.parseInt(array[i][3]) > max){
                max = Integer.parseInt(array[i][3]);
            }else if (array[i][0]!= null && Integer.parseInt(array[i][3]) < min){
                min = Integer.parseInt(array[i][3]);
            }
        }
        for (int x =0; x < 42; x++){
            if (array[x][0] == null){
                continue;
            }else{
                total+=Integer.parseInt(array[x][3]);
            }
        }
        avg = total/42;
        for (int y = 0; y < 42; y++){
            array[y][5] = Integer.toString(min);
            array[y][6] = Integer.toString(max);
            array[y][7] = Integer.toString(avg);

        }
    }

    public static void setName(String name,String sName,String[][] array,String seat){
        array[Integer.parseInt(seat)][0]=name;
        array[Integer.parseInt(seat)][1]=sName;
    }

    public static void setSecondsInQueue(String[][] array){
        for (int i =0; i<42; i++){
            Random rand = new Random();
            int one = rand.nextInt(6)+1;
            int two = rand.nextInt(6)+1;
            int three = rand.nextInt(6)+1;
            if (array[i][0]!=null){
                array[i][3]= Integer.toString(one+two+three);
            }
        }
        setMinTime(array);

    }
    public static void display(String[][] array) throws IOException {
        printReport(array);
        Stage stage = new Stage();
        //Setting a title for the stage
        stage.setTitle("Report");

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
        Label lblName = new Label("Customer Name     : ");
        Label lblSName = new Label("Customer Surname  : ");
        Label lblQueueTime = new Label("Time in the queue : ");
        Label lblQueueLength = new Label("Maximum Queue Length : ");
        Label lblMinTime = new Label("Maximum Time : ");
        Label lblMaxTime = new Label("Minimum Time : ");
        Label lblAvgTime = new Label("Average Time : ");
        Label lblSetName = new Label("");
        Label lblSetSName = new Label("");
        Label lblSetQueueTime = new Label("");
        Label lblSetQueueLength = new Label("");
        Label lblSetMinTime = new Label("");
        Label lblSetMaxTime = new Label("");
        Label lblSetAvgTime = new Label("");


        for (int i = 0; i < 42; i++) {
            btnarray[i] = new Button("" + (i + 1));
            btnarray[i].setStyle("-fx-focus-color: transparent;-fx-faint-focus-color: transparent;");

            int finalX = i;


            btnarray[i].setOnAction(event -> {
                //btnarray[finalX].setStyle("-fx-background-color: #CB4335;-fx-text-fill: White;-jfx-disable-visual-focus: true;");
                lblSetName.setText("");
                lblSetSName.setText("");
                lblSetQueueTime.setText("");
                lblSetQueueLength.setText("");
                lblSetMaxTime.setText("");
                lblSetAvgTime.setText("");
                lblSetMinTime.setText("");
                lblSetName.setText(array[finalX][0]);
                lblSetSName.setText(array[finalX][1]);
                lblSetQueueTime.setText(array[finalX][3] + "s");
                lblSetQueueLength.setText(array[finalX][4]);
                lblSetMinTime.setText(array[finalX][5] + "s");
                lblSetMaxTime.setText(array[finalX][6] + "s");
                lblSetAvgTime.setText(array[finalX][7] + "s");


            });
            if (array[finalX][0]!=null) {
                btnarray[finalX].setText((finalX+1)+"-"+array[finalX][0]);
            } else {
                btnarray[finalX].setText((finalX+1)+"- Empty");
                btnarray[finalX].setStyle("-fx-background-color: #5DADE2;-fx-text-fill: Black;");
                btnarray[finalX].setDisable(true);
            }

            //x is column index and y is row index
            gridPane.add(btnarray[i], (x + 1), (y + 1));
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
        Button btnClear = new Button();
        btnClear.setText("Clear");
        btnClear.setOnAction(event -> {
            lblSetName.setText("");
            lblSetSName.setText("");
            lblSetQueueTime.setText("");
        });
        Button btnQuit = new Button();
        btnQuit.setText("Close");
        btnQuit.setOnAction(event -> {
            stage.close();
        });
        Label lblLayout = new Label("      Select a number");
        lblLayout.setStyle("-fx-text-fill: White;-fx-font-weight: bold;");
        gridPane.add(lblLayout, 2, 0);
        gridPane.add(btnQuit, 11, 10);

        lblName.setStyle("-fx-text-fill: White;");
        lblSName.setStyle("-fx-text-fill: White;");
        lblQueueTime.setStyle("-fx-text-fill: White;");
        lblQueueLength.setStyle("-fx-text-fill: White;");
        lblMinTime.setStyle("-fx-text-fill: White;");
        lblMaxTime.setStyle("-fx-text-fill: White;");
        lblAvgTime.setStyle("-fx-text-fill: White;");

        lblSetName.setStyle("-fx-text-fill: White;");
        lblSetSName.setStyle("-fx-text-fill: White;");
        lblSetQueueTime.setStyle("-fx-text-fill: White;");
        lblSetQueueLength.setStyle("-fx-text-fill: White;");
        lblSetMinTime.setStyle("-fx-text-fill: White;");
        lblSetMaxTime.setStyle("-fx-text-fill: White;");
        lblSetAvgTime.setStyle("-fx-text-fill: White;");

        gridPane.add(lblName, 11, 1);
        gridPane.add(lblSName, 11, 2);
        gridPane.add(lblQueueTime, 11, 3);
        gridPane.add(lblQueueLength, 11, 4);
        gridPane.add(lblMinTime, 11, 5);
        gridPane.add(lblMaxTime, 11, 6);
        gridPane.add(lblAvgTime, 11, 7);
        gridPane.add(lblSetName, 12, 1);
        gridPane.add(lblSetSName, 12, 2);
        gridPane.add(lblSetQueueTime, 12, 3);
        gridPane.add(lblSetQueueLength, 12, 4);
        gridPane.add(lblSetMinTime, 12, 5);
        gridPane.add(lblSetMaxTime, 12, 6);
        gridPane.add(lblSetAvgTime, 12, 7);



        Scene scene = new Scene(gridPane, 1000, 700);
        stage.setScene(scene);
        stage.showAndWait();
    }
    public static void printReport(String[][] array) throws IOException {
        FileWriter writer = new FileWriter("TrainReport.txt");
        for (int i = 0; i < 42; i++) {
            if (array[i][0]==null){
                continue;
            }else
                //writer.write("Name     Surname     Time in Queue     Queue Length"+"\n");
                writer.write(("Name: "+array[i][0]+"    Surname: "+array[i][1]+"     Time in Queue: "+array[i][3]+"     Queue Length: "+array[i][4]+"     Minimum Time: "+array[i][5]+"     Maximum Time: "+array[i][6]+"     Average Time: "+array[i][7])+ "\n");
        }
        writer.close();
    }
}
