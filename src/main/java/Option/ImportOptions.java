package Option;

import Logging.Log;
import Option.DataOption.ButtonOptions;
import Option.DataOption.GameOptions;
import Option.DataOption.Option;
import Option.DataOption.WindowOptions;
import javafx.scene.image.Image;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * This class is responsible to import the options file, process it, and load the values into different objects.
 */
public class ImportOptions {

    private final String defaultOptionsFileName = "defaultOptions.json";
    private static final ImportOptions instance = new ImportOptions();

    private ImportOptions() {}

    /**
     * Gets a {@link ImportOptions} object.
     * @return An instantiated {@link ImportOptions} object.
     */
    public static ImportOptions getInstance()
    {
        return instance;
    }

    /**
     * Import values from the options file. If that not exists, then creates it copies the content in the defaultOptions
     * file to the newly created options file and import the values. This method makes difference between weather the application
     * is run by IDE or by a JAR file. The imported values will be sorted into the appropriate objects.
     * @param fileName The name of the imported file.
     * @return A map with a key as the type of the option and a value attached to it as appropriate {@link Option} object.
     */
    public Map<String,Option> importOptions(String fileName) {
        String json = "";
        try {
             json = getStringFromFile(fileName);
        }catch(Exception e)
        {
            e.printStackTrace();
            //TODO:Print error message
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
        wo.setGamePanelSize(new Dimension(windowOptions.getJSONObject("gamePanelSize").getInt("width"),
                windowOptions.getJSONObject("gamePanelSize").getInt("height")));
        wo.setInfoPanelSize(new Dimension(windowOptions.getJSONObject("infoPanelSize").getInt("width"),
                windowOptions.getJSONObject("infoPanelSize").getInt("height")));
        wo.setMenuPanelSize(new Dimension(windowOptions.getJSONObject("menuPanelSize").getInt("width"),
                windowOptions.getJSONObject("menuPanelSize").getInt("height")));
        wo.setConsolePanelSize(new Dimension(windowOptions.getJSONObject("consolePanelSize").getInt("width"),
                windowOptions.getJSONObject("consolePanelSize").getInt("height")));
        wo.setResizeable(windowOptions.getBoolean("resizeable"));
        wo.setTitle(windowOptions.getString("title"));
        try {
            String filePath = URLDecoder.decode(String.valueOf(getClass().getClassLoader().getResource(windowOptions.getString("iconLocation"))), StandardCharsets.UTF_8);
            wo.setImage(new Image(filePath));
        }catch (Exception e)
        {
            e.printStackTrace();
            //TODO: Handle error
        }

        map.put("WindowOptions",wo);

        GameOptions go = new GameOptions();
        go.setDifficulty(gameOptions.getInt("difficulty"));
        go.setNickName(gameOptions.getString("nickName"));

        map.put("GameOptions",go);

        return map;
    }

    private String getStringFromFile(String fileName) {

        String filePath;
        filePath = URLDecoder.decode(String.valueOf(getClass().getClassLoader().getResource(fileName)), StandardCharsets.UTF_8);

        if(filePath.equals("null"))  //Run by IDE and the file does not exist
        {
             Log.log("info",getClass().getName() + " - Run by IDE!");
             Log.log("warning",getClass().getName() + " - The " + fileName + " file does not exist!");

            String appLocation;
            appLocation = URLDecoder.decode(String.valueOf(getClass().getProtectionDomain().getCodeSource().getLocation()), StandardCharsets.UTF_8);
            //Making sure to work on Linux and Windows based systems as well
            if(System.getProperty("os.name").startsWith("Win")) {
                appLocation = appLocation.replace("file:/", "");
            }else
            {
                appLocation = appLocation.replace("file:", "");
            }
             copyFile(getClass().getClassLoader().getResourceAsStream(defaultOptionsFileName), appLocation + fileName, fileName);
             filePath = appLocation + fileName;
        }else if (filePath.startsWith("jar")) //Run by jar and the file may exist
            {
                Log.log("info",getClass().getName() + " - Run by JAR!");
                if(System.getProperty("os.name").startsWith("Win")) {
                    filePath = filePath.replace("jar:","").replace("file:/","");
                }else
                {
                    filePath = filePath.replace("jar:","").replace("file:","");
                }
                filePath = filePath.substring(0,filePath.lastIndexOf("/"));
                filePath = filePath.substring(0,filePath.lastIndexOf("/"));
                filePath += "/";
                filePath += fileName;
                File file = new File(filePath);
                if(!file.exists()) //Run by jar and the file does not exist
                {
                    Log.log("warning",getClass().getName() + " - The " + fileName + " file does not exist!");

                    String appLocation;
                    appLocation = URLDecoder.decode(String.valueOf(getClass().getProtectionDomain().getCodeSource().getLocation()), StandardCharsets.UTF_8);
                    //Making sure to work on Linux and Windows based systems as well
                    if(System.getProperty("os.name").startsWith("Win")) {
                        appLocation = appLocation.replace("file:/", "").replace("jar:/","");
                    }else
                    {
                        appLocation = appLocation.replace("file:", "").replace("jar:/","");
                    }
                    appLocation = appLocation.substring(0,appLocation.lastIndexOf("/"));
                    appLocation += "/";
                    copyFile(getClass().getClassLoader().getResourceAsStream(defaultOptionsFileName), appLocation + fileName,fileName);
                    filePath = appLocation + fileName;
                }else { //Run by jar and the file exist
                    Log.log("info",getClass().getName() + " - The " + fileName + " file does exist!");
                }
            }else { //Run by IDE and the file does exist
            Log.log("info",getClass().getName() + " - Run by IDE!");
            Log.log("info",getClass().getName() + " - The " + fileName + " file does exist!");
            if(System.getProperty("os.name").startsWith("Win")) {
                filePath = filePath.replace("file:/","");
            }else
            {
                filePath = filePath.replace("file:","");
            }
        }
        StringBuilder result = new StringBuilder();
        try
        {
            InputStream in = Files.newInputStream(Path.of(filePath));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine()) != null)
            {
                result.append(line);
            }
            reader.close();
        }catch(IOException e)
        {
            Log.log("error",getClass().getName() + " When reading file! " + e.getMessage());
        }
        return result.toString();
    }

    private void copyFile(InputStream from, String toStr, String fileName){
        Log.log("debug",getClass().getName() + " - Creating " + fileName + " file!");
        try {
            FileOutputStream out = new FileOutputStream(toStr);
            out.write(from.readAllBytes());
            out.flush();
            out.close();
            Log.log("debug",getClass().getName() + " - Successfully created " + fileName + " file!");
        }
        catch(Exception e) {
            Log.log("error",getClass().getName() + " - Error creating " + fileName + " file! Cause: " + e.getMessage());
        }

    }
}
