package puzzles.jam.ptui;

import puzzles.common.Observer;
import puzzles.jam.model.JamClientData;
import puzzles.jam.model.JamConfig;
import puzzles.jam.model.JamModel;
import puzzles.jam.solver.Jam;

import java.util.Scanner;


/**
 * Plain text UI for Jam
 *
 * @author ISHAN SHAH
 */
public class JamPTUI implements Observer<JamModel, JamClientData> {

    /**
     * The model for the view and controller.
     */
    private JamModel model;


    /**
     * Updates the config
     * @param model
     * @param jamClientData
     */
    @Override
    public void update(JamModel model, JamClientData jamClientData) {
        if (jamClientData != null) {
                System.out.println(jamClientData.getData());
            }
        JamConfig currentConfig = model.getCurrentConfig();
        System.out.println((currentConfig));
    }


    /**
     * Construct the PTUI
     */
    public JamPTUI(String filename) throws Exception {
        this.model = new JamModel(filename);
        String[] name = filename.split("/");
        System.out.println("Loaded: " + name[name.length - 1]);
        initializeView();
        displayHelp();
    }


    /**
     * Initialize the view
     */
    public void initializeView() {
        this.model.addObserver(this);
        update(this.model, null);
    }


    /**
     * Help displayed whenever wrong command is inputted
     */
    private void displayHelp() {
        System.out.println( "h(int)              -- hint next move" );
        System.out.println( "l(oad) filename     -- load new puzzle file" );
        System.out.println( "s(elect) r c        -- select cell at r, c" );
        System.out.println( "q(uit)              -- quit the game" );
        System.out.println( "r(eset)             -- reset the current game" );
    }

    /**
     * Read a command and execute loop.
     */
    private void run() throws Exception {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String line = in.nextLine();
            String[] words = line.split("\\s+");
            if (words.length > 0) {
                if (words[0].startsWith("q")) {
                    break;
                }
                else if (words[0].startsWith("h")) {
                    this.model.hint();
                }
                else if (words[0].startsWith("l")) {
                    this.model.load(words[1]);
                }
                else if (words[0].startsWith("s")) {
                    int r = Integer.parseInt(words[1]);
                    int c = Integer.parseInt(words[2]);
                    this.model.select(r, c);
                }
                else if (words[0].startsWith("r")) {
                    this.model.reset();
                }
                else {
                    displayHelp();
                }
            }
        }
    }


    /**
     * Main function to run the program
     * @param args command line args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java JamPTUI filename");
        }
        else {
            JamPTUI ptui = new JamPTUI(args[0]);
            ptui.run();
        }
    }

}
