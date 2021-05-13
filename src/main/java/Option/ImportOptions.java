package Option;

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

    private String getStringFromFile() throws Exception  {

        String filePath = URLDecoder.decode(String.valueOf(getClass().getClassLoader().getResource(optionsFileName)),"UTF-8");

        if(filePath == "null")  //Run by IDE and the file does not exist
        {
            System.out.println("--LOG-- Run by IDE!");
            System.out.println("--LOG-- The " + optionsFileName + " does not exist!");
             String appLocation = URLDecoder.decode(String.valueOf(getClass().getProtectionDomain().getCodeSource().getLocation()),"UTF-8");
             appLocation = appLocation.replace("file:/","");
             copyFile(getClass().getClassLoader().getResourceAsStream(defaultOptionsFileName), appLocation + optionsFileName);
             filePath = appLocation + optionsFileName;
        }else if (filePath.startsWith("jar")) //Run by jar and the file may exist
            {
                System.out.println("--LOG-- Run by JAR!");
                filePath = filePath.replace("jar:","").replace("file:/","");
                filePath = filePath.substring(0,filePath.lastIndexOf("/"));
                filePath = filePath.substring(0,filePath.lastIndexOf("/"));
                filePath += "/";
                filePath += optionsFileName;
                System.out.println(filePath);
                File file = new File(filePath);
                if(!file.exists()) //Run by jar and the file does not exist
                {
                    System.out.println("--LOG-- The " + optionsFileName + " does not exist!");
                    String appLocation = URLDecoder.decode(String.valueOf(getClass().getProtectionDomain().getCodeSource().getLocation()),"UTF-8");
                    appLocation = appLocation.replace("file:/","").replace("jar:/","");
                    appLocation = appLocation.substring(0,appLocation.lastIndexOf("/"));
                    appLocation += "/";
                    copyFile(getClass().getClassLoader().getResourceAsStream(defaultOptionsFileName), appLocation + optionsFileName);
                    filePath = appLocation + optionsFileName;
                }else { //Run by jar and the file exist
                    System.out.println("--LOG-- The " + optionsFileName + " does exist!");
                    System.out.println(filePath);
                }
            }else { //Run by IDE and the file does exist
            System.out.println("--LOG-- Run by IDE!");
            System.out.println("--LOG-- The " + optionsFileName + " does exist!");
            filePath = filePath.replace("file:/", "");
        }
        try
        {
            InputStream in = Files.newInputStream(Path.of(filePath));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String result = "";
            String line;
            while((line = reader.readLine()) != null)
            {
                result += line;
            }
            reader.close();
            return result;

        }catch(Exception e)
        {
            //TODO:Handle error
            e.printStackTrace();
            throw e;
        }
    }

    private void copyFile(InputStream from, String toStr){

        try {
            FileOutputStream out = new FileOutputStream(toStr);
            out.write(from.readAllBytes());
            out.flush();
            out.close();
        }
        catch(Exception e) {
            //TODO:Handle error
            e.printStackTrace();
        }
    }
}
