package puzzles.jam.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import puzzles.common.Observer;
import puzzles.jam.model.JamClientData;
import puzzles.jam.model.JamConfig;
import puzzles.jam.model.JamModel;

import java.io.File;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * GUI representation of Jam puzzle
 *
 * @author ISHAN SHAH
 */

public class JamGUI extends Application  implements Observer<JamModel, JamClientData>  {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    /**
     * Color code defined
     */
    private final static String X_CAR_COLOR = "#DF0101";
    private final static String A_CAR_COLOR = "#651e1e";
    private final static String B_CAR_COLOR = "#00E2EF";
    private final static String C_CAR_COLOR = "#0A36AC";
    private final static String D_CAR_COLOR = "#31FE07";
    private final static String E_CAR_COLOR = "#2B701D";
    private final static String F_CAR_COLOR = "#FF00FE";
    private final static String G_CAR_COLOR = "#560B56";
    private final static String H_CAR_COLOR = "#FFE809";
    private final static String I_CAR_COLOR = "#E6DD83";
    private final static String J_CAR_COLOR = "#FF7F00";
    private final static String K_CAR_COLOR = "#FF7F00";
    private final static String L_CAR_COLOR = "#978C81";
    private final static String O_CAR_COLOR = "#E36B6B";
    private final static String P_CAR_COLOR = "#47687B";
    private final static String Q_CAR_COLOR = "#AB8BC1";
    private final static String R_CAR_COLOR = "#636363";
    private final static String S_CAR_COLOR = "#212F3C";


    /**
     * Image for icon
     */
    private final Image icon = new Image(getClass().getResourceAsStream(RESOURCES_DIR + "X-hori.png"));

    /**
     * size of button
     */
    private final static int BUTTON_FONT_SIZE = 20;
    /**
     * icon size
     */
    private final static int ICON_SIZE = 75;

    /**
     * Label to update game status
     */
    private Label text;

    /**
     * List of buttons to update the text
     */
    private final List<Button> buttonList = new LinkedList<>();


    /**
     * Data directory where files are stored
     */
    private final String DATA_DIR = "data/jam/";

    /**
     * The model for controller to communicate with and for view to update
     */
    private JamModel model;


    /**
     * Parses the data file to create the model and adds itself to observers
     * @throws Exception for any possible exception
     */
    public void init() throws Exception {
        String filename = getParameters().getRaw().get(0);
        this.model = new JamModel(filename);
        this.model.addObserver(this);
    }


    /**
     * Start method to generate GUI elements and create main window
     * @param stage the stage
     * @throws Exception for any possible exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane border=new BorderPane();

        HBox centerTextHBox = new HBox();
        centerTextHBox.setAlignment(Pos.CENTER);

        text=new Label();

        String[] name = model.getFile().split("/");
        text.setText("Loaded: " + name[name.length - 1]);


        centerTextHBox.getChildren().add(text);
        border.setTop(centerTextHBox);

        Button load=new Button("Load");

        Button reset = new Button("Reset");
        reset.setOnAction(event-> {
            try {
                model.reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button hint=new Button("Hint");
        hint.setOnAction(event-> {
            try {
                model.hint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        FlowPane bottom =new FlowPane();
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().addAll(load,reset,hint);
        border.setBottom(bottom);
        GridPane center=makeGridPane();
        border.setCenter(center);
        setLoadEvent(stage, load, bottom, centerTextHBox);
        Scene scene=new Scene(border);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Jam Puzzle");
        stage.getIcons().add(icon);
        stage.sizeToScene();
        stage.show();
    }


    /**
     * Sets the graphics for buttons
     * @param board the board
     * @param r row cursor
     * @param c column cursor
     * @param button button whose graphics need to be set
     */
    private void setButtonGraphic(String[][] board,int r, int c,Button button) {
        if(board[r][c].equals("A")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + A_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }
        else if(board[r][c].equals("B")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + B_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }
        else if(board[r][c].equals("C")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + C_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }
        else if(board[r][c].equals("D")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + D_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }
        else if(board[r][c].equals("E")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + E_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }else if(board[r][c].equals("F")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + F_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }else if(board[r][c].equals("G")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + G_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }
        else if(board[r][c].equals("H")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + H_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }else if(board[r][c].equals("I")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + I_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }else if(board[r][c].equals("J")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + J_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }else if(board[r][c].equals("K")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + K_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }else if(board[r][c].equals("L")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + L_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }else if(board[r][c].equals("O")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + O_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }else if(board[r][c].equals("P")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + P_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }else if(board[r][c].equals("Q")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + Q_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }else if(board[r][c].equals("R")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + R_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }else if(board[r][c].equals("S")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + S_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }
        else if(board[r][c].equals("X")){
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: " + X_CAR_COLOR + ";" +
                    "-fx-font-weight: bold;");
            button.setText(board[r][c]);
        }
        else if(board[r][c].equals(JamConfig.EMPTY_SPACE)){
            button.setText("");
            button.setStyle("-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-background-color: transparent;" +
                    "-fx-font-weight: bold;");
        }
        else{
            text.setText("Car not found");
        }
        button.setTextFill(Color.WHITE);
    }


    /**
     * Used to change file of the puzzle
     * @param stage the stage
     * @param load button
     * @param bottom flowpane containing 3 buttons
     * @param centerTextHBox Hbox holding label to update textbox
     */
    private void setLoadEvent(Stage stage, Button load, FlowPane bottom, HBox centerTextHBox) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Puzzle");
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString() +
                File.separator + "data" + File.separator + "jam";
        fc.setInitialDirectory(new File(currentPath));

        load.setOnAction(e -> {
            File file = fc.showOpenDialog(stage);
            if (file != null) {
                try {
                    model.load(DATA_DIR + file.getName());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            buttonList.clear();
            GridPane grid = makeGridPane();
            BorderPane b = new BorderPane();
            b.setTop(centerTextHBox);
            b.setCenter(grid);
            b.setBottom(bottom);
            stage.setScene(new Scene(b));
            stage.sizeToScene();
        });
    }


    /**
     * Creates the gridPane for the board
     * @return the gridPane
     */
    public GridPane makeGridPane(){
        GridPane gridPane=new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        int row = model.getCurrentConfig().getRow();
        int col = model.getCurrentConfig().getCol();
        gridPane.setGridLinesVisible(true);
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                Button button = new Button();

                button.setMinSize(ICON_SIZE, ICON_SIZE);
                button.setMaxSize(ICON_SIZE, ICON_SIZE);
                setButtonGraphic(model.getCurrentConfig().getBoard(),r, c,button);

                int finalR = r;
                int finalC = c;

                button.setOnAction(e -> model.select(finalR, finalC));
                buttonList.add(button);

                gridPane.add(button, c, r);

            }
        }
        return gridPane;
    }


    /**
     * Updates the board and text label
     * @param jamModel current model
     * @param jamClientData data from the server to the observer
     */
    @Override
    public void update(JamModel jamModel, JamClientData jamClientData) {
        if (jamClientData != null) {
                text.setText(jamClientData.getData());
        }
        int row = model.getCurrentConfig().getRow();
        int col = model.getCurrentConfig().getCol();
        int i=0;

        // Prevents load from updating the board with different dimensions
        // Happens because load alerts observers before a new grid is made
        if(buttonList.size()==row*col){
            for (int r = 0; r < row; ++r) {
                for (int c = 0; c < col; ++c) {
                    setButtonGraphic(model.getCurrentConfig().getBoard(),r,c,buttonList.get(i));
                    i++;
                }
            }
        }
    }


    /**
     * main function to run the gui
     * @param args command line args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
