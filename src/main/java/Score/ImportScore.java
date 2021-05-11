package Score;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ImportScore {

    private static ImportScore instance = new ImportScore();
    private JSONObject jsonObject;
    private List<Score> list = new ArrayList<>();
    private static final String PATH_TO_FILE = "src/main/resources/highscore.json";

    private ImportScore(){}

    public static ImportScore getInstance()
    {
        return instance;
    }

    public List<Score> getScores()
    {
        list.clear();
        jsonObject = new JSONObject(getString());
        if(jsonObject.getJSONArray("scores").length() == 0)
        {
            return new ArrayList<>();
        }else
        {
            JSONArray jsonArray = jsonObject.getJSONArray("scores");
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Score score = new Score();
                score.setName(obj.getString("name"));
                score.setTime(obj.getLong("time"));
                score.setScore(obj.getDouble("score"));
                score.setBombNumber(obj.getInt("bombNumber"));
                list.add(score);
            }
        }
        Collections.sort(list,Collections.reverseOrder());
        Integer index = 1;
        for (Score score : list) {
            score.setPlace(index);
            index++;
        }
        return list;
    }
    private String getString()
    {
        File file;
        Scanner scanner;
        String result = """
                {
                "scores" :
                    [
                        
                    ]
                }
                """;
        try
        {
            file = new File(PATH_TO_FILE);
            if(!file.exists())
            {
                file.createNewFile();
                FileOutputStream fo = new FileOutputStream(file);
                fo.write(result.getBytes());
                fo.close();
            }else {
                scanner = new Scanner(file);
                result = "";
                while (scanner.hasNextLine()) {
                    result += scanner.nextLine();
                }
                scanner.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
