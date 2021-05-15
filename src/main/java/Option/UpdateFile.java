package Option;

import Logging.Log;
import Score.Score;
import Score.ImportScore;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;

public class UpdateFile {
    private static UpdateFile instance = new UpdateFile();
    private final String optionsFileName = "options.json";
    private final String scoreFileName = "highscore.json";
    private JSONObject jsonObject;

    private UpdateFile() {
    }

    public static UpdateFile getInstance() {
        return instance;
    }

    public void updateNickname(String name) {
        jsonObject = new JSONObject(getString(optionsFileName));

        String oldName = jsonObject.getJSONObject("options").getJSONObject("gameOptions").getString("nickName");
        if (!oldName.equals(name)) {
            jsonObject.getJSONObject("options").getJSONObject("gameOptions").put("nickName", name);
            try {
                String appLocation = URLDecoder.decode(getClass().getProtectionDomain().getCodeSource().getLocation().toString(),"UTF-8");
                appLocation = appLocation.replace("jar:/", "").replace("file:/","");
                appLocation = appLocation.substring(0, appLocation.lastIndexOf("/"));
                appLocation += "/";
                appLocation += optionsFileName;
                FileOutputStream out = new FileOutputStream(appLocation);
                out.write(jsonObject.toString().getBytes());
                out.flush();
                out.close();
                Log.log("info", getClass().getName() + " - Successfully updated the nickname!");
            } catch (Exception e) {
                Log.log("error", getClass().getName() + " - Error when opening file. " + e.getMessage());
            }

        }

    }

    public void updateHighScore(String name, Long time, Integer bombNumber, Double score) {
        Score scoreObj = new Score();
        scoreObj.setScore(score);
        scoreObj.setName(name);
        scoreObj.setTime(time);
        scoreObj.setBombNumber(bombNumber);

        ImportScore.getInstance().getScores(); //Make sure that the highscore.json file exists

        String oldScores = getString(scoreFileName);

        JSONObject root = new JSONObject(oldScores);
        JSONArray scores = root.getJSONArray("scores");
        String str = new Gson().toJson(scoreObj);
        scores.put(new JSONObject(str));

        root.put("scores", scores);
        try {
            String appLocation = URLDecoder.decode(String.valueOf(getClass().getProtectionDomain().getCodeSource().getLocation()),"UTF-8");
            appLocation = appLocation.replace("jar:/", "").replace("file:/","");
            appLocation = appLocation.substring(0, appLocation.lastIndexOf("/"));
            appLocation += "/";
            appLocation += scoreFileName;
            FileOutputStream out = new FileOutputStream(appLocation);
            out.write(root.toString().getBytes());
            out.flush();
            out.close();
            Log.log("info", getClass().getName() + " - Successfully updated the score!");
        } catch (Exception e) {
            Log.log("error", getClass().getName() + " - Error when opening file. " + e.getCause().getMessage());
        }
    }

    public void updateDifficulty(Integer newNumber) {
        jsonObject = new JSONObject(getString(optionsFileName));

        Integer oldNumber = jsonObject.getJSONObject("options").getJSONObject("gameOptions").getInt("difficulty");
        if (!oldNumber.equals(newNumber)) {

            jsonObject.getJSONObject("options").getJSONObject("gameOptions").put("difficulty", newNumber);
            try {
                String appLocation = URLDecoder.decode(String.valueOf(getClass().getProtectionDomain().getCodeSource().getLocation()),"UTF-8");
                appLocation = appLocation.replace("jar:/", "").replace("file:/","");
                appLocation = appLocation.substring(0, appLocation.lastIndexOf("/"));
                appLocation += "/";
                appLocation += optionsFileName;
                FileOutputStream out = new FileOutputStream(appLocation);
                out.write(jsonObject.toString().getBytes());
                out.flush();
                out.close();
                Log.log("info", getClass().getName() + " - Successfully updated the difficulty!");
            } catch (IOException e) {
                Log.log("error", getClass().getName() + " - Error when opening file. " + e.getCause().getMessage());
            }

        }
    }

    private String getString(String path) {
        String filePath = null;
        String result = "";
        try {
            filePath = URLDecoder.decode(String.valueOf(getClass().getClassLoader().getResource(path)),"UTF-8");
        } catch (UnsupportedEncodingException ignored) {
            //Ignored because the encoding will always be UTF_8 which is a valid encoding format
            Log.log("error", getClass().getName() + " - Error when opening file. " + ignored.getCause().getMessage());
        }
        if(filePath == "null")  //Run by IDE and the file does not exist
        {
            Log.log("warning", getClass().getName() + " - The " + path + " file does not exits!");
            String appLocation = null;
            try {
                appLocation = URLDecoder.decode(String.valueOf(getClass().getProtectionDomain().getCodeSource().getLocation()),"UTF-8");
            } catch (UnsupportedEncodingException ignored) {
                //Ignored because the encoding will always be UTF_8 which is a valid encoding format
                Log.log("error", getClass().getName() + " - Error when opening file. " + ignored.getCause().getMessage());
            }
            appLocation = appLocation.replace("file:/","");
            filePath = appLocation + path;
        }else if (filePath.startsWith("jar")) //Run by jar and the file may exist
        {
            filePath = filePath.replace("jar:","").replace("file:/","");
            filePath = filePath.substring(0,filePath.lastIndexOf("/"));
            filePath = filePath.substring(0,filePath.lastIndexOf("/"));
            filePath += "/";
            filePath += path;
            System.out.println(filePath);
            File file = new File(filePath);
            if(!file.exists()) //Run by jar and the file does not exist
            {
                Log.log("warning", getClass().getName() + " - The " + path + " file does not exits!");
                String appLocation = null;
                try {
                    appLocation = URLDecoder.decode(String.valueOf(getClass().getProtectionDomain().getCodeSource().getLocation()),"UTF-8");
                } catch (UnsupportedEncodingException ignored) {
                    //Ignored because the encoding will always be UTF_8 which is a valid encoding format
                    Log.log("error", getClass().getName() + " - Error when opening file. " + ignored.getCause().getMessage());
                }
                appLocation = appLocation.replace("file:/","").replace("jar:/","");
                appLocation = appLocation.substring(0,appLocation.lastIndexOf("/"));
                appLocation += "/";
                filePath = appLocation + path;
            }else { //Run by jar and the file exist
                Log.log("info", getClass().getName() + " - The " + path + " file does exits!");
            }
        }else { //Run by IDE and the file does exist
            Log.log("info", getClass().getName() + " - The " + path + " file does exits!");
            filePath = filePath.replace("file:/", "");
        }
        try
        {
            InputStream in = Files.newInputStream(Path.of(filePath));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = "";
            String line;
            while((line = reader.readLine()) != null)
            {
                result += line;
            }
            reader.close();
            return result;

        }catch(Exception e)
        {
            Log.log("error", getClass().getName() + " - Error when reading file! "  + e.getCause().getMessage());
        }
        return result;
    }

}
