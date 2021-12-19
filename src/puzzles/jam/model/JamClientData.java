package puzzles.jam.model;


/**
 * Class to deliver update status from the model to the view
 *
 * @author ISHAN SHAH
 */
public class JamClientData {
    /**
     * Text for status
     */
    private final String data;

    /**
     * Constructor to initialise and update data
     * @param data
     */
    public JamClientData(String data) {
        this.data = data;
    }


    /**
     * Gets the updated data
     *
     * @return data
     */
    public String getData() {
        return data;
    }

}
