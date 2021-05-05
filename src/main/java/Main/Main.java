package Main;

import Control.SingletonHolder;
import Window.GameWindow;

public class Main {

    public static void main(String[] args) {
        SingletonHolder.getInstance().setInstances();
        GameWindow.launch(GameWindow.class,args);

    }
}
