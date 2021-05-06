package Option;

import Option.DataOption.Option;

import java.util.Map;

public class GeneralOptions {

    private Map<String,Option> options = ImportOptions.getInstance().importOptions();
    private static GeneralOptions instance = new GeneralOptions();

    private GeneralOptions(){}

    public static GeneralOptions getInstance()
    {
        return instance;
    }

    public Map<String,Option> getOptions()
    {
        return options;
    }

}
