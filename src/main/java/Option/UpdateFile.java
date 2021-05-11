package Option;

import Score.Score;
import Score.ImportScore;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.Scanner;

public class UpdateFile {
    private static UpdateFile instance = new UpdateFile();
    private final String PATH_TO_OPTIONS = "src/main/resources/options.json";
    private final String PATH_TO_SCORE = "src/main/resources/highscore.json";
    private JSONObject jsonObject;

    private UpdateFile(){}

    public static UpdateFile getInstance()
    {
        return instance;
    }

    public void updateNickname(String name)
    {
        jsonObject = new JSONObject(getString(PATH_TO_OPTIONS));

        String oldName = jsonObject.getJSONObject("options").getJSONObject("gameOptions").getString("nickName");
        if(!oldName.equals(name))
        {
            jsonObject.getJSONObject("options").getJSONObject("gameOptions").put("nickName",name);
            try {
                FileWriter fw = new FileWriter(PATH_TO_OPTIONS);
                fw.write(jsonObject.toString());
                fw.flush();
                fw.close();
            } catch (IOException e) {
                //TODO: Log error
            }

        }

    }

    public void updateHighScore(String name, Long time, Integer bombNumber, Double score)
    {
        Score scoreObj = new Score();
        scoreObj.setScore(score);
        scoreObj.setName(name);
        scoreObj.setTime(time);
        scoreObj.setBombNumber(bombNumber);

        ImportScore.getInstance().getScores();

        String oldScores = getString(PATH_TO_SCORE);

        JSONObject root = new JSONObject(oldScores);
        JSONArray scores = root.getJSONArray("scores");
        String str = new Gson().toJson(scoreObj);
        str = str.replace("\\", "");
        scores.put(new JSONObject(str));

        root.put("scores", scores);
        try {
            FileWriter fo = new FileWriter(PATH_TO_SCORE);
            fo.write(root.toString());
            fo.flush();
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDifficulty(Integer newNumber)
    {
        jsonObject = new JSONObject(getString(PATH_TO_OPTIONS));

        Integer oldNumber = jsonObject.getJSONObject("options").getJSONObject("gameOptions").getInt("difficulty");
        if(!oldNumber.equals(newNumber))
        {
            jsonObject.getJSONObject("options").getJSONObject("gameOptions").put("difficulty",newNumber);
            try {
                FileWriter fw = new FileWriter(PATH_TO_OPTIONS);
                fw.write(jsonObject.toString());
                fw.flush();
                fw.close();
            } catch (IOException e) {
                //TODO: Log error
            }

        }
    }

    private String getString(String path) {
        String result = "";
        Scanner scanner;
        try {
             scanner = new Scanner(new File(path));
             while(scanner.hasNextLine())
             {
                 result += scanner.nextLine();
             }
             scanner.close();
        } catch (FileNotFoundException e) {
            //It cannot happen that the file does not exist because at the start of the program, the file is created if its not exist
            //TODO:Log out the error
        }

        return result;
    }


}
