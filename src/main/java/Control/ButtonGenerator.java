package Control;

import Button.AbstractButton;
import Button.InGame.NumberButton;
import Button.State;
import Exception.UnknownButtonException;
import Option.DataOption.ButtonOptions;
import Option.DataOption.GameOptions;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import java.util.Random;

public class ButtonGenerator {
    private Dimension size;
    private ButtonFactory buttonFactory;
    private Integer numberOfBombs;

    private AbstractButton[][] buttonMatrix;
    private List<AbstractButton> buttons;
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

    public GridPane generateButtons(Dimension frameSize, GridPane pane) throws UnknownButtonException {
        List<Point> bombList = generateBombs(frameSize);
        buttonMatrix = new AbstractButton[frameSize.width / size.width][frameSize.height / size.height];
        Point temp;
        AbstractButton button;

        for(int i = 0;i < frameSize.width;i += size.width)
        {
            for(int j = 0;j < frameSize.height;j += size.height)
            {
                temp = new Point(i,j);
                if(bombList.contains(temp))
                {
                    button = buttonFactory.getButton(State.BOMB);
                }else
                {
                    button = getButton(temp,bombList);
                }
                button.setSize(size);
                AbstractButton finalButton = button;
                button.setOnAction(actionEvent -> {
                    finalButton.onClickEvent();
                });
                buttons.add(button);
                pane.add(button,i,j);
            }
        }
        return pane;
    }

    private AbstractButton getButton(Point point, List<Point> bombList) throws UnknownButtonException {
        Integer counter = 0;
        Point temp;
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
        if(counter == 0)
        {
            return buttonFactory.getButton(State.EMPTY);
        }else
        {
            NumberButton button = (NumberButton) buttonFactory.getButton(State.NUMBER);
            button.changeScore(counter);
            return button;
        }
    }

    private List<Point> generateBombs(Dimension range)
    {
        List<Point> result = new ArrayList<>();
        Integer counter = 0;
        Point temp;
        do{
            temp = new Point(getRandomNumberInRange(range.width),getRandomNumberInRange(range.height));
            Point finalTemp = temp;
            if(result.isEmpty() || !result.stream().anyMatch(a -> a.equals(finalTemp)));
            {
                result.add(temp);
                counter++;
            }
        }while(counter < numberOfBombs);
        return result;
    }
    private Integer getRandomNumberInRange(Integer range)
    {
        Integer number;
        Random rand = new Random();
        do {
            number = rand.nextInt(range);
        }while(number % size.height != 0);

        return number;
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


