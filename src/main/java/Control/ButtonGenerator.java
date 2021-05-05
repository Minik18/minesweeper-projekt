package Control;

import Button.AbstractButton;
import Button.InGame.NumberButton;
import Button.State;
import Exception.UnknownButtonException;
import Option.DataOption.ButtonOptions;
import Option.DataOption.GameOptions;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class ButtonGenerator {
    private Dimension size;
    private ButtonFactory buttonFactory;
    private Integer numberOfBombs;

    private ArrayList<AbstractButton> buttons;
    private AbstractButton[][] buttonMatrix;
    private static ButtonGenerator instance = new ButtonGenerator();
    private ButtonOptions buttonOptions = (ButtonOptions) SingletonHolder.getInstance().getGeneralOptions().getOptions().get("ButtonOptions");
    private GameOptions gameOptions = (GameOptions) SingletonHolder.getInstance().getGeneralOptions().getOptions().get("GameOptions");

    private ButtonGenerator() {
        buttons = new ArrayList<>();
        buttonFactory = new ButtonFactory();
        size = buttonOptions.getSize();
        numberOfBombs = gameOptions.getDifficulty();
    }

    public static ButtonGenerator getInstance() {
        return instance;
    }

    public List<AbstractButton> generateButtons(Dimension frameSize) throws UnknownButtonException {
        buttonMatrix = new AbstractButton[frameSize.height / size.height][frameSize.width / size.width];
        ArrayList<Point> bombsList;
        AbstractButton button;
        bombsList = generateBombs(frameSize);


        for (int i = 0; i < frameSize.height; i += size.height) {
            for (int j = 0; j < frameSize.width; j += size.width) {
                Point temp = new Point(i, j);
                if (bombsList.stream().anyMatch(p -> p.equals(temp))) {
                    button = buttonFactory.getButton(State.BOMB);
                } else {
                    button = checkIfNumber(bombsList, temp);
                }
                button.setSize(size);

                buttons.add(button);
            }
        }
        return buttons;
    }
    private ArrayList<Point> generateBombs(Dimension frameSize)
    {
        ArrayList<Point> list = new ArrayList<>();
        Point temp;
        for(int i = 0;i<numberOfBombs;i++)
        {
            temp = new Point(getRandomNumberInRange(frameSize.height),getRandomNumberInRange(frameSize.width));
            if(list.isEmpty())
            {
                list.add(temp);
            }else
            {
                Point finalTemp = temp;
                if(list.stream().anyMatch(a -> a.equals(finalTemp)))
                {
                    i--;
                }else{
                    list.add(finalTemp);
                }
            }
        }

        return list;
    }

    private int getRandomNumberInRange(int range)
    {
        int number;
        Random rand = new Random();
        do {
            number = rand.nextInt(range);
        }while(number % size.height != 0);

        return number;
    }

    private AbstractButton checkIfNumber(ArrayList<Point> bombList, Point point) throws UnknownButtonException {
        Point temp;
        int counter = 0;

        for(int i=-1;i<2;i++)
        {
            for(int j = -1; j<2;j++)
            {
                temp = new Point(point.x+i*size.height, point.y+j*size.width);
                Point finalTemp = temp;

                if(bombList.stream().anyMatch(p -> p.equals(finalTemp)))
                {
                    counter++;
                }
            }
        }
        if(counter!=0)
        {
            NumberButton button = (NumberButton) buttonFactory.getButton(State.NUMBER);
            button.changeScore(counter);
            return button;
        }else
        {
            return buttonFactory.getButton(State.EMPTY);
        }
    }

    public AbstractButton[][] getButtonMatrix() {
        if (buttonMatrix[0][0] == null) {
            createButtonMatrix();
        }
        return buttonMatrix;
    }

    private void createButtonMatrix()
    {
        int counter = 0;
        for(int i=0;i< buttonMatrix.length;i++)
        {
            for(int j = 0;j<buttonMatrix[i].length;j++)
            {
                buttonMatrix[i][j] = buttons.get(counter);
                counter++;
            }
        }
    }
}
