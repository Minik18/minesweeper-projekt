package Option;

import Option.DataOption.Option;

import java.util.Map;

/**
 * This class contains the different kind of options.
 */
public class GeneralOptions {

    private final Map<String,Option> options = ImportOptions.getInstance().importOptions();
    private static final GeneralOptions instance = new GeneralOptions();

    private GeneralOptions(){}

    /**
     * Gets a {@link GeneralOptions} object.
     * @return An instantiated {@link GeneralOptions} object.
     */
    public static GeneralOptions getInstance()
    {
        return instance;
    }

    /**
     * Returns the map containing the set up {@link Option} objects.
     * @return A map which key is a string representing the name of the option and an attached value which is an
     * appropriate {@link Option} object.
     */
    public Map<String,Option> getOptions()
    {
        return options;
    }

}
