package Option;

import Option.DataOption.ButtonOptions;
import Option.DataOption.GameOptions;
import Option.DataOption.Option;
import Option.DataOption.WindowOptions;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ImportOptions {

    private final String PATH_TO_FILE = "src/main/resources/defaultOptions.json";
    private static ImportOptions instance = new ImportOptions();

    private ImportOptions() {}

    public static ImportOptions getInstance()
    {
        return instance;
    }

    public Map<String,Option> importOptions() {
        String json = "";
        try {
             json = getStringFromFile();
        }catch(IOException e)
        {
            //Print error message
        }
        JSONObject jsonObject = new JSONObject(json);
        JSONObject buttonOptions = jsonObject.getJSONObject("options").getJSONObject("buttonOptions");
        JSONObject gameOptions = jsonObject.getJSONObject("options").getJSONObject("gameOptions");
        JSONObject windowOptions = jsonObject.getJSONObject("options").getJSONObject("windowOptions");

        Map<String,Option> map = new HashMap<>();

        ButtonOptions bo = new ButtonOptions();
        bo.setSize(new Dimension(buttonOptions.getJSONObject("size").getInt("width"),
                buttonOptions.getJSONObject("size").getInt("height")));

        map.put("ButtonOptions",bo);

        WindowOptions wo = new WindowOptions();
        wo.setSize(new Dimension(windowOptions.getJSONObject("size").getInt("width"),
                windowOptions.getJSONObject("size").getInt("height")));
        wo.setResizeable(windowOptions.getJSONObject("size").getBoolean("resizeable"));
        wo.setTitle(windowOptions.getString("title"));

        map.put("WindowOptions",wo);

        GameOptions go = new GameOptions();
        go.setDifficulty(gameOptions.getInt("difficulty"));
        go.setNickName(gameOptions.getString("nickName"));

        map.put("GameOptions",go);

        return map;
    }

    private String getStringFromFile() throws FileNotFoundException {
        try
        {
            Scanner scanner = new Scanner(new File(PATH_TO_FILE));
            String result = "";

            while(scanner.hasNextLine())
            {
                result += scanner.nextLine();
            }
            scanner.close();
            return result;

        }catch(Exception e)
        {
            throw e;
        }
    }
}
