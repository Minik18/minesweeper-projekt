package Option;

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
                String appLocation = getClass().getProtectionDomain().getCodeSource().getLocation().toString();
                appLocation = appLocation.replace("jar:/", "").replace("file:/","");
                appLocation = appLocation.substring(0, appLocation.lastIndexOf("/"));
                appLocation += "/";
                appLocation += optionsFileName;
                FileOutputStream out = new FileOutputStream(appLocation);
                out.write(jsonObject.toString().getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                //TODO: Log error
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
        } catch (Exception e) {
            //TODO: Log error
            e.printStackTrace();
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
            } catch (IOException e) {
                //TODO: Log error
                e.printStackTrace();
            }

        }
    }

    private String getString(String path) {
        String filePath = null;
        String result = "";
        try {
            filePath = URLDecoder.decode(String.valueOf(getClass().getClassLoader().getResource(path)),"UTF-8");
        } catch (UnsupportedEncodingException ignored) {
            //Ignored exception because encoding will always be UTF-8 which is a valid encoding
        }
        if(filePath == "null")  //Run by IDE and the file does not exist
        {
            System.out.println("--LOG-- Run by IDE!");
            System.out.println("--LOG-- The " + path + " does not exist!");
            String appLocation = null;
            try {
                appLocation = URLDecoder.decode(String.valueOf(getClass().getProtectionDomain().getCodeSource().getLocation()),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            appLocation = appLocation.replace("file:/","");
            filePath = appLocation + path;
        }else if (filePath.startsWith("jar")) //Run by jar and the file may exist
        {
            System.out.println("--LOG-- Run by JAR!");
            filePath = filePath.replace("jar:","").replace("file:/","");
            filePath = filePath.substring(0,filePath.lastIndexOf("/"));
            filePath = filePath.substring(0,filePath.lastIndexOf("/"));
            filePath += "/";
            filePath += path;
            System.out.println(filePath);
            File file = new File(filePath);
            if(!file.exists()) //Run by jar and the file does not exist
            {
                System.out.println("--LOG-- The " + path + " does not exist!");
                String appLocation = null;
                try {
                    appLocation = URLDecoder.decode(String.valueOf(getClass().getProtectionDomain().getCodeSource().getLocation()),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                appLocation = appLocation.replace("file:/","").replace("jar:/","");
                appLocation = appLocation.substring(0,appLocation.lastIndexOf("/"));
                appLocation += "/";
                filePath = appLocation + path;
            }else { //Run by jar and the file exist
                System.out.println("--LOG-- The " + path + " does exist!");
                System.out.println(filePath);
            }
        }else { //Run by IDE and the file does exist
            System.out.println("--LOG-- Run by IDE!");
            System.out.println("--LOG-- The " + path + " does exist!");
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
            //TODO:Handle error
            e.printStackTrace();
        }
        return result;
    }

}
