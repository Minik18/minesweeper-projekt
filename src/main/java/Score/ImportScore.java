package Score;

import Logging.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * This class has the responsibility to import the scores from the highscore jon file.
 */
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

    /**
     * This method imports the values from the highscore file. If the file does not exist, it creates it end sets an
     * empty json array object to it. Then processes the values and add them together into a {@link List}. This method
     * makes difference between whether the application is run by an IDE or a JAR file.
     * @returns A list, containing {@link Score} objects.
     */
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
            Log.log("error", getClass().getName() + " - Error when opening file. " + ignored.getMessage());
        }
         if(filePath == "null")  //Run by IDE and the file does not exist
         {
             Log.log("warning",getClass().getName() + " - The " + highscoreFileName + " file does not exist!");
             String appLocation = null;
             try {
                 appLocation = URLDecoder.decode(String.valueOf(getClass().getProtectionDomain().getCodeSource().getLocation()),"UTF-8");
             } catch (UnsupportedEncodingException ignored) {
                 //Ignored because the encoding will always be UTF_8 which is a valid encoding format
                 Log.log("error", getClass().getName() + " - Error when opening file. " + ignored.getMessage());
             }
             //Making sure to work on Linux and Windows based systems as well
             if(System.getProperty("os.name").startsWith("Win")) {
                 appLocation = appLocation.replace("file:/", "").replace("jar:/","");
             }else
             {
                 appLocation = appLocation.replace("file:", "").replace("jar:/","");
             }
             filePath = appLocation + highscoreFileName;
             createFile(filePath,result);
         }else if (filePath.startsWith("jar")) //Run by jar and the file may exist
         {
             //Making sure to work on both Linus and Windows base systems
             if(System.getProperty("os.name").startsWith("Win"))
             {
                 filePath = filePath.replace("jar:","").replace("file:/","");
             }else
             {
                 filePath = filePath.replace("jar:","").replace("file:","");
             }
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
                     Log.log("error", getClass().getName() + " - Error when opening file. " + ignored.getMessage());
                 }
                 //Making sure to work on Linux and Windows based systems as well
                 if(System.getProperty("os.name").startsWith("Win")) {
                     appLocation = appLocation.replace("file:/", "").replace("jar:/","");
                 }else
                 {
                     appLocation = appLocation.replace("file:", "").replace("jar:/","");
                 }
                 appLocation = appLocation.substring(0,appLocation.lastIndexOf("/"));
                 appLocation += "/";
                 filePath = appLocation + highscoreFileName;
                 createFile(filePath,result);
             }else { //Run by jar and the file exist
                 Log.log("info",getClass().getName() + " - The " + highscoreFileName + " file does exist!");
             }
         }else { //Run by IDE and the file does exist
             Log.log("info",getClass().getName() + " - The " + highscoreFileName + " file does exist!");
             if(System.getProperty("os.name").startsWith("Win")) {
                 filePath = filePath.replace("file:/", "").replace("jar:/","");
             }else
             {
                 filePath = filePath.replace("file:", "").replace("jar:/","");
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

         }catch(Exception e)
         {
             Log.log("error",getClass().getName() + " - Error when reading file! " + e.getMessage());
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
            Log.log("error",getClass().getName() + " - Error when creating file! " + e.getMessage());
        }
    }

}

