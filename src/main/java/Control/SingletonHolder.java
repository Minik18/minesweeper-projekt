package Control;

import Option.DataOption.WindowOptions;
import Option.GeneralOptions;
import Option.ImportOptions;
import Window.WindowConfigure;
import lombok.Data;

@Data
public class SingletonHolder {

    private ButtonGenerator buttonGenerator;
    private GeneralOptions generalOptions;
    private WindowConfigure windowConfigure;
    private ImportOptions importOptions;
    private static SingletonHolder instance = new SingletonHolder();

    private SingletonHolder(){}

    public void setInstances()
    {
        importOptions = ImportOptions.getInstance();
        generalOptions = GeneralOptions.getInstance();
        buttonGenerator = ButtonGenerator.getInstance();
        windowConfigure = WindowConfigure.getInstance();
    }

    public static SingletonHolder getInstance()
    {
        return instance;
    }

}
