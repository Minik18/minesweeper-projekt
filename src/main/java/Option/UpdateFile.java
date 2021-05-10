package Option;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UpdateFile {
    private static UpdateFile instance = new UpdateFile();
    private final String PATH_TO_FILE = "src/main/resources/options.json";
    private JSONObject jsonObject;

    private UpdateFile(){}

    public static UpdateFile getInstance()
    {
        return instance;
    }

    public void updateNickname(String name)
    {
        jsonObject = new JSONObject(getString());

        String oldName = jsonObject.getJSONObject("options").getJSONObject("gameOptions").getString("nickName");
        if(!oldName.equals(name))
        {
            jsonObject.getJSONObject("options").getJSONObject("gameOptions").put("nickName",name);
            try {
                FileWriter fw = new FileWriter(PATH_TO_FILE);
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

    }
    public void updateDifficulty(Integer newNumber)
    {
        jsonObject = new JSONObject(getString());

        Integer oldNumber = jsonObject.getJSONObject("options").getJSONObject("gameOptions").getInt("difficulty");
        if(!oldNumber.equals(newNumber))
        {
            jsonObject.getJSONObject("options").getJSONObject("gameOptions").put("difficulty",newNumber);
            try {
                FileWriter fw = new FileWriter(PATH_TO_FILE);
                fw.write(jsonObject.toString());
                fw.flush();
                fw.close();
            } catch (IOException e) {
                //TODO: Log error
            }

        }
    }

    private String getString() {
        String result = "";
        Scanner scanner;
        try {
             scanner = new Scanner(new File(PATH_TO_FILE));
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
