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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ImportOptions {

    private final String defaultOptionsFileName = "defaultOptions.json";
    private final String optionsFileName = "options.json";
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
            String filePath = URLDecoder.decode(String.valueOf(getClass().getClassLoader().getResource(windowOptions.getString("iconLocation"))),"UTF-8");
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

    private String getStringFromFile() {

        String filePath = null;
        try {
            filePath = URLDecoder.decode(String.valueOf(getClass().getClassLoader().getResource(optionsFileName)),"UTF-8");
        } catch (UnsupportedEncodingException ignored) {
            //It cannot happen because the encoding will always be UTF-8 which is a valid encoding format.
            Log.log("error",getClass().getName() + " " + ignored.getCause().getMessage());
        }

        if(filePath == "null")  //Run by IDE and the file does not exist
        {
             Log.log("info",getClass().getName() + " - Run by IDE!");
             Log.log("warning",getClass().getName() + " - The " + optionsFileName + " file does not exist!");

            String appLocation = null;
            try {
                appLocation = URLDecoder.decode(String.valueOf(getClass().getProtectionDomain().getCodeSource().getLocation()),"UTF-8");
            } catch (UnsupportedEncodingException ignored) {
                //It cannot happen because the encoding will always be UTF-8 which is a valid encoding format.
                Log.log("error",getClass().getName() + " " + ignored.getCause().getMessage());
            }

            appLocation = appLocation.replace("file:/","");
             copyFile(getClass().getClassLoader().getResourceAsStream(defaultOptionsFileName), appLocation + optionsFileName);
             filePath = appLocation + optionsFileName;
        }else if (filePath.startsWith("jar")) //Run by jar and the file may exist
            {
                Log.log("info",getClass().getName() + " - Run by JAR!");
                filePath = filePath.replace("jar:","").replace("file:/","");
                filePath = filePath.substring(0,filePath.lastIndexOf("/"));
                filePath = filePath.substring(0,filePath.lastIndexOf("/"));
                filePath += "/";
                filePath += optionsFileName;
                File file = new File(filePath);
                if(!file.exists()) //Run by jar and the file does not exist
                {
                    Log.log("warning",getClass().getName() + " - The " + optionsFileName + " file does not exist!");

                    String appLocation = null;
                    try {
                        appLocation = URLDecoder.decode(String.valueOf(getClass().getProtectionDomain().getCodeSource().getLocation()),"UTF-8");
                    } catch (UnsupportedEncodingException ignored) {
                        //It cannot happen because the encoding will always be UTF-8 which is a valid encoding format.
                        Log.log("error",getClass().getName() + " " + ignored.getCause().getMessage());
                    }

                    appLocation = appLocation.replace("file:/","").replace("jar:/","");
                    appLocation = appLocation.substring(0,appLocation.lastIndexOf("/"));
                    appLocation += "/";
                    copyFile(getClass().getClassLoader().getResourceAsStream(defaultOptionsFileName), appLocation + optionsFileName);
                    filePath = appLocation + optionsFileName;
                }else { //Run by jar and the file exist
                    Log.log("info",getClass().getName() + " - The " + optionsFileName + " file does exist!");
                }
            }else { //Run by IDE and the file does exist
            Log.log("info",getClass().getName() + " - Run by IDE!");
            Log.log("info",getClass().getName() + " - The " + optionsFileName + " file does exist!");
            filePath = filePath.replace("file:/", "");
        }
        String result = "";
        try
        {
            InputStream in = Files.newInputStream(Path.of(filePath));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine()) != null)
            {
                result += line;
            }
            reader.close();
        }catch(IOException e)
        {
            Log.log("error",getClass().getName() + " When reading file! " + e.getCause().getMessage());
        }
        return result;
    }

    private void copyFile(InputStream from, String toStr){
        Log.log("debug",getClass().getName() + " - Creating " + optionsFileName + " file!");
        try {
            FileOutputStream out = new FileOutputStream(toStr);
            out.write(from.readAllBytes());
            out.flush();
            out.close();
        }
        catch(Exception e) {
            Log.log("error",getClass().getName() + " - Error creating " + optionsFileName + " file! Cause: " + e.getCause().getMessage());
        }
        Log.log("debug",getClass().getName() + " - Successfully created " + optionsFileName + " file!");
    }
}
