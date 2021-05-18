package Option;

import Logging.Log;
import Score.Score;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class is responsible to update the correct options or highscore file.
 */
public class UpdateFile {
    private static final UpdateFile instance = new UpdateFile();
    private JSONObject jsonObject;

    private UpdateFile() {
    }

    /**
     * Gets a {@link GeneralOptions} object.
     * @return An instantiated {@link GeneralOptions} object.
     */
    public static UpdateFile getInstance() {
        return instance;
    }

    /**
     * Updates the nickname element in the options json file. This method makes a difference between whether the application
     * is run by an IDE or from a JAR file. If the file could not be updated, the reason will be logged.
     * @param name A new name for a player.
     * @param optionsFileName The file name to be updated.
     */
    public void updateNickname(String name,String optionsFileName) {
        jsonObject = new JSONObject(getString(optionsFileName));

        String oldName = jsonObject.getJSONObject("options").getJSONObject("gameOptions").getString("nickName");
        if (!oldName.equals(name)) {
            jsonObject.getJSONObject("options").getJSONObject("gameOptions").put("nickName", name);
            try {
                String appLocation = URLDecoder.decode(getClass().getProtectionDomain().getCodeSource().getLocation().toString(), StandardCharsets.UTF_8);
                if(System.getProperty("os.name").startsWith("Win")) {
                    appLocation = appLocation.replace("jar:/", "").replace("file:/", "");
                }else
                {
                    appLocation = appLocation.replace("jar:/", "").replace("file:", "");
                }
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

    /**
     * Adds a {@link Score} object to the highscore json file. It first creates the object from the given parameters,
     * then appends to the existing list. The score is calculated by the {@code (numberOfBombs/deltaTime) * numberOfBombs}
     * formula.
     * @param name The name of the player.
     * @param time The time between the start and the end of the game.
     * @param bombNumber The number of bombs in the current game.
     * @param scoreFileName The file name to be updated.
     */
    public void updateHighScore(String name, Long time, Integer bombNumber,String scoreFileName) {
        Score scoreObj = new Score();
        scoreObj.setScore( (double) ( (bombNumber / time) * bombNumber));
        scoreObj.setName(name);
        scoreObj.setTime(time);
        scoreObj.setBombNumber(bombNumber);

        String oldScores = getString(scoreFileName);

        JSONObject root = new JSONObject(oldScores);
        JSONArray scores = root.getJSONArray("scores");
        String str = new Gson().toJson(scoreObj);
        scores.put(new JSONObject(str));

        root.put("scores", scores);
        try {
            String appLocation = URLDecoder.decode(String.valueOf(getClass().getProtectionDomain().getCodeSource().getLocation()), StandardCharsets.UTF_8);
            if(System.getProperty("os.name").startsWith("Win")) {
                appLocation = appLocation.replace("jar:/", "").replace("file:/", "");
            }else
            {
                appLocation = appLocation.replace("jar:/", "").replace("file:", "");
            }            appLocation = appLocation.substring(0, appLocation.lastIndexOf("/"));
            appLocation += "/";
            appLocation += scoreFileName;
            FileOutputStream out = new FileOutputStream(appLocation);
            out.write(root.toString().getBytes());
            out.flush();
            out.close();
            Log.log("info", getClass().getName() + " - Successfully updated the score!");
        } catch (Exception e) {
            Log.log("error", getClass().getName() + " - Error when opening file. " + e.getMessage());
        }
    }

    /**
     * Updates the difficulty element in the options json file. This method makes a difference between whether the application
     * is run by an IDE or from a JAR file. If the file could not be updated, the reason will be logged.
     * @param newNumber A new number of bombs.
     * @param optionsFileName The file name to be updated.
     */
    public void updateDifficulty(Integer newNumber,String optionsFileName) {
        jsonObject = new JSONObject(getString(optionsFileName));

        Integer oldNumber = jsonObject.getJSONObject("options").getJSONObject("gameOptions").getInt("difficulty");
        if (!oldNumber.equals(newNumber)) {

            jsonObject.getJSONObject("options").getJSONObject("gameOptions").put("difficulty", newNumber);
            try {
                String appLocation = URLDecoder.decode(String.valueOf(getClass().getProtectionDomain().getCodeSource().getLocation()), StandardCharsets.UTF_8);
                if(System.getProperty("os.name").startsWith("Win")) {
                    appLocation = appLocation.replace("jar:/", "").replace("file:/", "");
                }else
                {
                    appLocation = appLocation.replace("jar:/", "").replace("file:", "");
                }                appLocation = appLocation.substring(0, appLocation.lastIndexOf("/"));
                appLocation += "/";
                appLocation += optionsFileName;
                FileOutputStream out = new FileOutputStream(appLocation);
                out.write(jsonObject.toString().getBytes());
                out.flush();
                out.close();
                Log.log("info", getClass().getName() + " - Successfully updated the difficulty!");
            } catch (IOException e) {
                Log.log("error", getClass().getName() + " - Error when opening file. " + e.getMessage());
            }

        }
    }

    private String getString(String path) {
        String filePath;
        String result = "";
        filePath = URLDecoder.decode(String.valueOf(getClass().getClassLoader().getResource(path)), StandardCharsets.UTF_8);
        if (filePath.startsWith("jar")) // Run by JAR
        {
            //Making sure to work both on Windows and Linux based systems
            if(System.getProperty("os.name").startsWith("Win")) {
                filePath = filePath.replace("jar:","").replace("file:/","");
            }else
            {
                filePath = filePath.replace("jar:","").replace("file:","");
            }
            filePath = filePath.substring(0,filePath.lastIndexOf("/"));
            filePath = filePath.substring(0,filePath.lastIndexOf("/"));
            filePath += "/";
            filePath += path;
        }else //Run by IDE
        {
            Log.log("info", getClass().getName() + " - The " + path + " file does exits!");
            if(System.getProperty("os.name").startsWith("Win")) {
                filePath = filePath.replace("file:/","");
            }else
            {
                filePath = filePath.replace("file:","");
            }
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
            Log.log("error", getClass().getName() + " - Error when reading file! "  + e.getMessage());
        }
        return result;
    }

}
