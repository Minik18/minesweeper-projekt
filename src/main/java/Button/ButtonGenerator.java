package Button;

import Button.InGame.EmptyButton;
import Button.InGame.NumberButton;
import Control.RevealButton;
import Exception.UnknownButtonException;
import Logging.Log;
import Option.DataOption.ButtonOptions;
import Option.DataOption.GameOptions;
import Option.GeneralOptions;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import java.util.Random;

/**
 * A class that generates buttons according to the sizes of the given objects.
 */
public class ButtonGenerator {
    private Dimension size;
    private ButtonFactory buttonFactory;
    private Integer numberOfBombs;

    private static AbstractButton[][] buttonMatrix;
    private ButtonOptions buttonOptions = (ButtonOptions) GeneralOptions.getInstance().getOptions().get("ButtonOptions");
    private GameOptions gameOptions = (GameOptions) GeneralOptions.getInstance().getOptions().get("GameOptions");

    /**
     * Instantiates a {@link ButtonFactory} class and gets the given sizes for the buttons and gets to set number of bombs.
     */
    public ButtonGenerator() {
        buttonFactory = new ButtonFactory();
        size = buttonOptions.getSize();
        numberOfBombs = gameOptions.getDifficulty();
    }

    /**
     *  Generates buttons according to the game panel size and the size of the buttons. Generates as many bomb buttons
     *  as have been set in the options file. It creates an {@link AbstractButton} matrix for later use. Sets the
     *  number of buttons and the number of tiles in {@link RevealButton} class.
     * @param frameSize The size of the game panel.
     * @param pane The empty panel.
     * @return A set up panel which contains the generated number in each cell.
     */
    public GridPane generateButtons(Dimension frameSize, GridPane pane) {
        List<Point> bombList = generateBombs(frameSize);
        RevealButton.setBombNumber(bombList.size());
        buttonMatrix = new AbstractButton[frameSize.width / size.width + 1 ][frameSize.height / size.height + 1];
        Point temp;
        new EmptyButton();
        AbstractButton button = new EmptyButton();
        Integer bombNumber = 0;
        Integer buttonNumber = 0;
        for(int i = 0;i < frameSize.width;i += size.width)
        {
            for(int j = 0;j < frameSize.height;j += size.height)
            {
                temp = new Point(i,j);
                if(bombList.contains(temp))
                {
                    try {
                        button = buttonFactory.getButton(State.BOMB);
                    } catch (UnknownButtonException e) {
                        Log.log("error", getClass().getName() + " - Unknown button configuration! " + e.getMessage());
                    }
                    bombNumber ++;
                }else
                {
                    try {
                        button = getButton(temp,bombList);
                    } catch (UnknownButtonException e) {
                        Log.log("error", getClass().getName() + " - Unknown button configuration! " + e.getMessage());
                    }
                }
                button.setSize(size);
                AbstractButton finalButton = button;
                button.setOnMouseClicked(action ->
                {
                    if(action.getButton().equals(MouseButton.SECONDARY))
                    {
                        finalButton.onLeftClickEvent();
                    }else if(action.getButton().equals(MouseButton.PRIMARY))
                    {
                        finalButton.onRightClickEvent();
                    }
                });
                button.setFocusTraversable(false);
                buttonNumber++;
                pane.add(button,i,j);
                buttonMatrix[GridPane.getColumnIndex(button) / size.width][GridPane.getRowIndex(button) / size.height] = button;
            }
        }
        RevealButton.setBombNumber(bombNumber);
        RevealButton.setButtonNumber(buttonNumber);
        Log.log("info", getClass().getName() + " - Successfully created buttons to game panel!");
        return pane;
    }
    /**
     * A method to return a matrix of tiles.
     * @return a matrix containing the generated buttons.
     */
    public static AbstractButton[][] getButtonMatrix() {
        return buttonMatrix;
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




}


