import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.util.ArrayList;



public class Main {
    private static Screen screen = new Screen();
    public static final int GAME_SIZE = 16;
    public static final int NUM_MINES = 32;
    public static final int BUFFER_SIZE = 10;
    public static final ArrayList<Tile> Tiles = new ArrayList<Tile>();
    public static int winLose = 0;
    public static void main(String[] args) {
        screen.setScreen();
    }
}