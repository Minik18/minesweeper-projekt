package Option;

import Control.SingletonHolder;
import Option.DataOption.Option;

import java.util.Map;

public class GeneralOptions {

    private Map<String,Option> options = SingletonHolder.getInstance().getImportOptions().importOptions();
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
