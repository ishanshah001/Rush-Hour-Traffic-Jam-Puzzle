package puzzles.jam.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.IOException;
import java.util.*;


/**
 * Model for Jam Puzzle
 *
 * @author ISHAN SHAH
 */
public class JamModel {
    /** the collection of observers of this model */
    private final List<Observer<JamModel, JamClientData>> observers = new LinkedList<>();

    /** the current configuration */
    private JamConfig currentConfig;

    /**
     * stores file name
     */
    private String file;
    /**
     * Stores how many times has s been selected
     */
    private int selectCount;
    /**
     * holds row and col previously selected
     */
    private final Queue<Integer> moves;


    /**
     * path for bfs
     */
    private ArrayList<Configuration> path = new ArrayList<>();

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<JamModel, JamClientData> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(JamClientData data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }

    /**
     * Construct a JamMode
     * @param filename name of file
     * @throws Exception
     */
    public JamModel(String filename) throws Exception {
        this.file = filename;
        this.selectCount = 0;
        this.moves = new LinkedList<>();
        load(file);
    }


    /**
     * loads the file
     * @param filename name of file
     * @throws Exception
     */
    public void load(String filename) throws Exception {
        try {
            this.currentConfig = new JamConfig(filename);
            this.file=filename;
            String[] name = filename.split("/");
            alertObservers(new JamClientData("Loaded: " + name[name.length - 1]));
        } catch (IOException ioe) {
            alertObservers(new JamClientData("Failed to load: " + filename));
        }
    }


    /**
     * resets the board
     * @throws Exception
     */
    public void reset() throws Exception {
        load(file);
        alertObservers(new JamClientData("Puzzle reset"));
    }


    /**
     * Gives the next move
     */
    public void hint(){
        if (selectCount != 0) {
            selectCount--;
            moves.remove();
            moves.remove();
        }

        Solver solver = new Solver(currentConfig);
        Optional<ArrayList<Configuration>> bfs = solver.solve();
        if(bfs.isPresent()){
            path= bfs.get();
        }
        if(currentConfig.isGoal()){
            alertObservers(new JamClientData("Already solved!"));
        }
        else if (path.contains(currentConfig)) {
                currentConfig = new JamConfig((JamConfig) path.get(path.indexOf(currentConfig) + 1));
                alertObservers(new JamClientData("Next step!"));
        }
        else {
            alertObservers(new JamClientData("No solution"));;
        }
    }


    /**
     * @return current config
     */
    public JamConfig getCurrentConfig() {
        return currentConfig;
    }

    /**
     * Selects the row and column of start and end block
     * @param r row coordinate
     * @param c column coordinate
     */
    public void select(int r, int c){
        switch (selectCount) {
            case 0 -> {
                if (currentConfig.checkSelection(r, c)) {
                    moves.add(r);
                    moves.add(c);
                    alertObservers(new JamClientData("Selected (" + r + ", " + c + ")"));
                    selectCount++;
                } else {
                    alertObservers(new JamClientData("No car at (" + r + ", " + c + ")"));
                }
            }
            case 1 -> {
                int pRow = moves.remove();
                int pCol = moves.remove();

                JamConfig s= currentConfig.checkMove(currentConfig.getBoard()[pRow][pCol], pRow, pCol, r, c);

                if (s!=null) {
                    for(int i=0;i< currentConfig.getRow();i++){
                        for(int j=0;j<currentConfig.getCol();j++){
                            currentConfig.getBoard()[i][j]=s.getBoard()[i][j];
                            currentConfig=new JamConfig(currentConfig);
                        }
                    }
                    alertObservers(new JamClientData("Moved from (" + pRow + ", " + pCol + ") to " +
                            "(" + r + ", " + c + ")"));
                }
                else {
                    alertObservers(new JamClientData("Can't move from (" + pRow + ", " + pCol + ") to " +
                            "(" + r + ", " + c + ")"));
                }
                selectCount--;
            }
        }
    }


    /**
     * returns file name
     * @return name of the file
     */
    public String getFile(){
        return file;
    }


}
