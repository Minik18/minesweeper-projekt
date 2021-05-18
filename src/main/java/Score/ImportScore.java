package Score;

import Logging.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * This class has the responsibility to import the scores from the highscore jon file.
 */
public class ImportScore {

    private static final ImportScore instance = new ImportScore();
    private List<Score> list = new ArrayList<>();
    private static final String highscoreFileName = "highscore.json";

    private ImportScore(){}

    /**
     * Gets a {@link Option.ImportOptions} object.
     * @return An instantiated {@link Option.ImportOptions} object.
     */
    public static ImportScore getInstance()
    {
        return instance;
    }

    /**
     * Gets the imported score values.
     * @return A {@link List} containing {@link Score} objects.
     */
    public List<Score> getScores()
    {
        return list;
    }

    /**
     * This method imports the values from the a file. If the file does not exist, it creates it end sets an
     * empty json array object to it. Then processes the values and add them together into a {@link List}. This method
     * makes difference between whether the application is run by an IDE or a JAR file.
     * @param fileName The name of the file.
     */
    public void importScores(String fileName)
    {
        JSONObject jsonObject;
        list.clear();
        jsonObject = new JSONObject(getString(fileName));
        if(jsonObject.getJSONArray("scores").length() == 0)
        {
            list = new ArrayList<>();
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
        list.sort(Collections.reverseOrder());
        Integer index = 1;
        for (Score score : list) {
            score.setPlace(index);
            index++;
        }
        Log.log("info", getClass().getName() + " - Successfully imported scores.");
    }
    private String getString(String fileName)
     {
        StringBuilder result = new StringBuilder("""
                {
                "scores" :
                    [
                        
                    ]
                }
                """);

        String filePath;
         filePath = URLDecoder.decode(String.valueOf(getClass().getClassLoader().getResource(fileName)), StandardCharsets.UTF_8);
         if(filePath.equals("null"))  //Run by IDE and the file does not exist
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
             filePath = appLocation + fileName;
             createFile(filePath, result.toString());
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
             filePath += fileName;
             System.out.println(filePath);
             File file = new File(filePath);
             if(!file.exists()) //Run by jar and the file does not exist
             {
                 Log.log("warning",getClass().getName() + " - The " + fileName + " file does not exist!");
                 String appLocation ;
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
                 filePath = appLocation + fileName;
                 createFile(filePath, result.toString());
             }else { //Run by jar and the file exist
                 Log.log("info",getClass().getName() + " - The " + fileName + " file does exist!");
             }
         }else { //Run by IDE and the file does exist
             Log.log("info",getClass().getName() + " - The " + fileName + " file does exist!");
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
             result = new StringBuilder();
             String line;
             while((line = reader.readLine()) != null)
             {
                 result.append(line);
             }
             reader.close();

         }catch(Exception e)
         {
             Log.log("error",getClass().getName() + " - Error when reading file! " + e.getMessage());
         }
        return result.toString();
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

