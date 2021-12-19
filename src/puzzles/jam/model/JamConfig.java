package puzzles.jam.model;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import puzzles.common.solver.Configuration;
import puzzles.jam.solver.Jam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.*;


/**
 * Configuration for  MVC of Jam puzzle
 *
 * @author ISHAN SHAH
 */
public class JamConfig implements Configuration{

    /**
     * marks the row of the X
     */
    private int exit;
    /**
     * Total number of cars
     */
    private int num_cars;
    /**
     * row and column dimension
     */
    private int row,col;
    /**
     * Represents empty space
     */
    public static String EMPTY_SPACE = "_";
    /**
     * Represesnts the ice_cream truck
     */
    public static String ICE_CREAM_TRUCK = "X";
    /**
     * the board
     */
    private String[][]board;
    /**
     * hashset of successors
     */
    private Collection<Configuration> successor = new HashSet<>();
    /**
     * change in r
     */
    private int delta_r=0;
    /**
     * change in c
     */
    private int delta_c=0;
    /**
     * current row cursor
     */
    private int r;
    /**
     * current col cursor
     */
    private int c;

    /**
     * Constructor that builds the board and initializes values
     * with the contents of the provided file
     * @param filename name of the file
     * @throws Exception
     */
    public JamConfig(String filename) throws Exception{

        BufferedReader in = new BufferedReader(new FileReader(filename));
        String[] dim = in.readLine().split(" ");
        row = Integer.parseInt(dim[0]);
        col = Integer.parseInt(dim[1]);
        board=new String[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                board[i][j]=EMPTY_SPACE;
            }
        }
        num_cars= Integer.parseInt(in.readLine());
        String car;
        int start_row,start_col,end_row,end_col;
        for(int i=0;i<num_cars;i++){
            String[] read = in.readLine().split(" ");
            car=read[0];
            start_row=Integer.parseInt(read[1]);
            start_col=Integer.parseInt(read[2]);
            end_row=Integer.parseInt(read[3]);
            end_col=Integer.parseInt(read[4]);
            if(start_col==end_col){
                for(int j=start_row;j<=end_row;j++){
                    board[j][start_col]=car;
                }
            }
            if(start_row==end_row){
                for(int j=start_col;j<=end_col;j++){
                    board[start_row][j]=car;
                }
            }
            if(i==num_cars-1){
                ICE_CREAM_TRUCK=car;
                exit=start_row;
            }
        }
    }

    /**
     * Copy constructor
     * @param other
     */
    public JamConfig(JamConfig other) {
        this.exit=other.exit;
        this.ICE_CREAM_TRUCK=other.ICE_CREAM_TRUCK;
        this.row=other.row;
        this.col=other.col;
        this.board=new String[row][col];
        for(int i = 0; i < row; i++){
            this.board[i] = other.board[i].clone();
        }
    }

    /**
     * Helper function to print the board
     */
    public void print_board(){
        System.out.println("  0 1 2 3 4 5");
        for(int i=0;i<row;i++){
            System.out.print(i+" ");
            for(int j=0;j<col;j++){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }


    /**
     * Checks if selected block is vcertical
     * @param r row coordinate
     * @param c column coordinate
     * @param board board
     * @return true if vertical, else false
     */
    public boolean isVertical(int r,int c,String[][] board){
        if(row==1){
            return false;
        }
        if(r!=0){
            if(board[r-1][c].equals(board[r][c])){
                return true;
            }
        }
        if(r!=row-1){
            if(board[r+1][c].equals(board[r][c])){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if current config is equal to the given config
     * @param other config
     * @return
     */
    @Override
    public boolean equals(Object other){
        boolean result = false;
        if (other instanceof JamConfig) {
            JamConfig o = (JamConfig) other;
            result = Arrays.deepEquals(this.board, o.board);
        }
        return result;
    }

    /**
     * Hash code customised for 2d array
     * @return
     */
    @Override
    public int hashCode() {
        int hash = -2147483648;
        for (String[] r : board) {
            hash += Arrays.hashCode(r);
        }
        return hash;
    }


    /**
     * Checks if current selected coordinate is valid or not
     * @param r row coordinate
     * @param c col coordinate
     * @return true if valid
     */
    public boolean checkSelection(int r, int c){
        if(r>=0 && r<row && c>=0 &&c<col && board[r][c]!=EMPTY_SPACE){
            return true;
        }
        return false;
    }

    /**
     * Checks if current move is valid or not
     * @param ur row coordinate
     * @param uc col coordinate
     * @return true if valid move, else false
     */
    public JamConfig checkMove(String check, int ur, int uc, int nr, int nc){

        if(this.getBoard()[nr][nc].equals(check)){
            return null;
        }
        delta_r = 0;
        delta_c = 0;
        if (isVertical(ur, uc, this.getBoard())) {
            delta_r = 1;
        } else {
            delta_c = 1;
        }
        int min_r = ur;
        int max_r = ur;
        int min_c = uc;
        int max_c = uc;
        while (min_r - delta_r >= 0 && min_c - delta_c >= 0 && this.getBoard()[min_r - delta_r][min_c - delta_c] == this.getBoard()[ur][uc]) {
            min_r -= delta_r;
            min_c -= delta_c;
        }
        while (max_r + delta_r < row && max_c + delta_c < col && this.getBoard()[max_r + delta_r][max_c + delta_c] == this.getBoard()[ur][uc]) {
            max_r += delta_r;
            max_c += delta_c;
        }
        JamConfig config1 = new JamConfig(this);
        JamConfig config2 = new JamConfig(this);

        if (min_r - delta_r >= 0 && min_c - delta_c >= 0 && this.getBoard()[min_r - delta_r][min_c - delta_c] == EMPTY_SPACE) {
            config1.board[min_r - delta_r][min_c - delta_c] = this.getBoard()[ur][uc];
            config1.board[max_r][max_c] = EMPTY_SPACE;
        }
        if(max_r + delta_r < row && max_c + delta_c < col && this.getBoard()[max_r + delta_r][max_c + delta_c] == EMPTY_SPACE) {
            config2.board[min_r][min_c] = EMPTY_SPACE;
            config2.board[max_r + delta_r][max_c + delta_c] = this.getBoard()[ur][uc];
        }
        Collection<Configuration> next=this.getSuccessors();
        for(Configuration s:next){
            JamConfig suc=(JamConfig) s;
            int config1_count=0;
            int config2_count=0;
            for(int i=0;i<row;i++){
                for(int j=0;j<col;j++){
                    if(config1.getBoard()[nr][nc].equals(check)){
                        if(suc.getBoard()[i][j].equals(config1.getBoard()[i][j])){
                            config1_count++;
                            if(config1_count==row*col){
                                return config1;
                            }
                        }
                    }
                    else if(config2.getBoard()[nr][nc].equals(check)){
                        if(suc.getBoard()[i][j].equals(config2.getBoard()[i][j])){
                            config2_count++;
                            if(config2_count==row*col){
                                return config2;
                            }
                        }
                    }

                    else{
                        break;
                    }
                }
            }
        }
        return null;
    }


    /**
     * Gets successors of current config
     * @return collection of successors
     */
    @Override
    public Collection<Configuration> getSuccessors() {
        HashSet<String> processed_char_sets=new HashSet<>();
        processed_char_sets.add(EMPTY_SPACE);
        for(int r=0;r<row;r++){
            for(int c=0;c<col;c++){
                if(!processed_char_sets.contains(board[r][c])){
                    processed_char_sets.add(board[r][c]);
                    delta_r=0;
                    delta_c=0;
                    if(isVertical(r,c,board)){
                        delta_r=1;
                    }
                    else{
                        delta_c=1;
                    }
                    int min_r=r;
                    int max_r=r;
                    int min_c=c;
                    int max_c=c;
                    while(min_r - delta_r >= 0 && min_c - delta_c >= 0 && board[min_r - delta_r][min_c - delta_c] == board[r][c]){
                        min_r -= delta_r;
                        min_c -= delta_c;
                    }
                    while(max_r + delta_r < row && max_c + delta_c < col && board[max_r + delta_r][max_c + delta_c] == board[r][c]){
                        max_r += delta_r;
                        max_c += delta_c;
                    }
                    if(min_r - delta_r >= 0 && min_c - delta_c >= 0 && board[min_r - delta_r][min_c - delta_c] == EMPTY_SPACE){
                        JamConfig config1=new JamConfig(this);
                        config1.board[min_r - delta_r][min_c - delta_c] = board[r][c];
                        config1.board[max_r][max_c] = EMPTY_SPACE;
                        successor.add(config1);
                    }
                    if(max_r + delta_r < row && max_c + delta_c < col && board[max_r + delta_r][max_c + delta_c] == EMPTY_SPACE){
                        JamConfig config1=new JamConfig(this);
                        config1.board[min_r][min_c] = EMPTY_SPACE;
                        config1.board[max_r + delta_r][max_c + delta_c] = board[r][c];
                        successor.add(config1);
                    }
                }
            }
        }
        return successor;
    }


    /**
     * gets row dimension
     * @return row
     */
    public int getRow(){
        return row;
    }


    /**
     * gets col dimension
     * @return col
     */
    public int getCol(){
        return col;
    }

    /**
     * checks if goal is reached or not
     * @return true if reached, else false
     */
    @Override
    public boolean isGoal() {
        if(board[exit][col-1].equals(ICE_CREAM_TRUCK)){
            return true;
        }
        return false;
    }


    /**
     * returns current board
     * @return
     */
    public String[][] getBoard() {
        return this.board;
    }


    /**
     * String representation of board with row col no
     */
    @Override
    public String toString(){
        String out="  ";
        for(int i=0;i<col;i++){
                out+=" "+i;
        }
        out+="\n";
        out+="  ";
        for(int i=0;i<col;i++){
            out+="--";
        }
        out+="\n";
        for(int i=0;i<row;i++){
            out+=i+"| ";
            for(int j=0;j<col;j++){
                out+=board[i][j]+" ";
            }
            out+="\n";
        }
        out+="\n";
        return out;
    }


}
