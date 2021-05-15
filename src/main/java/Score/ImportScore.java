package Score;

import Logging.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ImportScore {

    private static ImportScore instance = new ImportScore();
    private JSONObject jsonObject;
    private List<Score> list = new ArrayList<>();
    private static final String highscoreFileName = "highscore.json";

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
        Log.log("info", getClass().getName() + " - Successfully imported scores.");
        return list;
    }
    private String getString()
     {
        String result = """
                {
                "scores" :
                    [
                        
                    ]
                }
                """;

        String filePath = null;
        try {
            filePath = URLDecoder.decode(String.valueOf(getClass().getClassLoader().getResource(highscoreFileName)),"UTF-8");
        } catch (UnsupportedEncodingException ignored) {
            //Ignored because the encoding will always be UTF_8 which is a valid encoding format
            Log.log("error", getClass().getName() + " - Error when opening file. " + ignored.getCause().getMessage());
        }
         if(filePath == "null")  //Run by IDE and the file does not exist
         {
             Log.log("warning",getClass().getName() + " - The " + highscoreFileName + " file does not exist!");
             String appLocation = null;
             try {
                 appLocation = URLDecoder.decode(String.valueOf(getClass().getProtectionDomain().getCodeSource().getLocation()),"UTF-8");
             } catch (UnsupportedEncodingException ignored) {
                 //Ignored because the encoding will always be UTF_8 which is a valid encoding format
                 Log.log("error", getClass().getName() + " - Error when opening file. " + ignored.getCause().getMessage());
             }
             appLocation = appLocation.replace("file:/","");
             filePath = appLocation + highscoreFileName;
             createFile(filePath,result);
         }else if (filePath.startsWith("jar")) //Run by jar and the file may exist
         {
             filePath = filePath.replace("jar:","").replace("file:/","");
             filePath = filePath.substring(0,filePath.lastIndexOf("/"));
             filePath = filePath.substring(0,filePath.lastIndexOf("/"));
             filePath += "/";
             filePath += highscoreFileName;
             System.out.println(filePath);
             File file = new File(filePath);
             if(!file.exists()) //Run by jar and the file does not exist
             {
                 Log.log("warning",getClass().getName() + " - The " + highscoreFileName + " file does not exist!");
                 String appLocation = null;
                 try {
                     appLocation = URLDecoder.decode(String.valueOf(getClass().getProtectionDomain().getCodeSource().getLocation()),"UTF-8");
                 } catch (UnsupportedEncodingException ignored) {
                     Log.log("error", getClass().getName() + " - Error when opening file. " + ignored.getCause().getMessage());
                 }
                 appLocation = appLocation.replace("file:/","").replace("jar:/","");
                 appLocation = appLocation.substring(0,appLocation.lastIndexOf("/"));
                 appLocation += "/";
                 filePath = appLocation + highscoreFileName;
                 createFile(filePath,result);
             }else { //Run by jar and the file exist
                 Log.log("info",getClass().getName() + " - The " + highscoreFileName + " file does exist!");
             }
         }else { //Run by IDE and the file does exist
             Log.log("info",getClass().getName() + " - The " + highscoreFileName + " file does exist!");
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

         }catch(Exception e)
         {
             Log.log("error",getClass().getName() + " - Error when reading file! " + e.getCause().getMessage());
         }
        return result;
    }

    private void createFile(String path, String str)
    {
        Log.log("debug","Creating " + path + " file!");
        try {
            FileOutputStream fo = new FileOutputStream(path);
            fo.write(str.getBytes());
            fo.flush();
            fo.close();
            Log.log("debug","Successfully created the " + path + " file!");
        } catch (FileNotFoundException e) {
            Log.log("error",getClass().getName() + " - The " + path + " file does not exist!");
        } catch (IOException e) {
            Log.log("error",getClass().getName() + " - Error when creating file! " + e.getCause().getMessage());
        }
    }

}

