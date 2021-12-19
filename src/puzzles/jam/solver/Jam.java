package puzzles.jam.solver;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.jam.model.JamConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;


/**
 * Main Jam class to call the Solver to solve the Jam puzzle
 *
 * @author ISHAN SHAH
 */
public class Jam {

    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            System.out.println("Usage: java Jam filename");
        }
        else{
            JamConfig jam = new JamConfig(args[0]);

            Solver solver = new Solver(jam);
            Optional<ArrayList<Configuration>> path = solver.solve();

            System.out.println("File: " + args[0]);
            System.out.println(jam);
            System.out.println("Total configs: " + solver.getTotalConfigs());
            System.out.println("Unique configs: " + solver.getUniqueConfigs());

            if (path.isPresent()) {
                int step = 0;
                for (Configuration c : path.get()) {
                    System.out.println("Step " + step++ + ":");
                    System.out.println(c);
                }
            }
            else {
                System.out.println("No Solution!");
            }
        }
    }
}

