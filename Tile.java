import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Tile extends Screen {
  private ImageIcon squareImage = new ImageIcon("Images/MinesweeperUnknown.png");
  private JButton square = new JButton(squareImage);
  private int squareSize;
  private int ID = -1;
  private int onSide = 0;
  private int onBottom = 0;
  private boolean hasMine = false;
  private boolean exposed = false;
  private boolean flag = false;
  private int numNeighbors = 0;
  private static boolean firstTile = true;
  //an ID of -1 is never used

  Tile (int ID) {
    square.addMouseListener(this);
    this.ID = ID;
    if (ID % Main.GAME_SIZE == 0) {
      onSide = 1;
    }
    else if (ID % Main.GAME_SIZE == Main.GAME_SIZE - 1) {
      onSide = 2;
    }
    if (ID / Main.GAME_SIZE == 0) {
      onBottom = 2;
    }
    else if (ID / Main.GAME_SIZE == Main.GAME_SIZE - 1) {
      onBottom = 1;
    }
  }
  public void hide() {
    square.setVisible(false);
  }
  public void show() {
    square.setVisible(true);
  }
  public int getID() {
    return(ID);
  }
  public void setPosition() {
    squareSize = (Main.BUFFER_SIZE * getFrameHeight()) / ((Main.BUFFER_SIZE + 1) * (Main.GAME_SIZE + 1));
    int buffer = squareSize / Main.BUFFER_SIZE;
    int startPos = (getFrameWidth() / 2) - (squareSize + buffer) * (Main.GAME_SIZE / 2);
    square.setBounds(startPos + (ID % Main.GAME_SIZE) * (squareSize + buffer), buffer + (ID / Main.GAME_SIZE) * (squareSize + buffer), squareSize, squareSize);
    addTile(square);
    scaleImage(squareSize, squareSize, squareImage);
    square.setVisible(true);
  }
  private static void randomizeMines(int numMines, int excludeID) {
    ArrayList<Integer> possibleMines = new ArrayList<>();
    Random rand = new Random();
    int index;
    for (int i = 0; i < Main.GAME_SIZE * Main.GAME_SIZE; i = i + 1) {
      possibleMines.add(i);
    }
    for (int i = 0; i < 9; i = i + 1) {
      int j = 0;
      switch (i) {
        case 0:
        case 1:
        case 2:
          j = excludeID - Main.GAME_SIZE - 1 + i;
          break;
        case 3:
          j = excludeID - 1;
          break;
        case 4:
          j = excludeID + 1;
          break;
        case 5:
        case 6:
        case 7:
          j = excludeID + Main.GAME_SIZE - 6 + i;
          break;
        case 8:
          j = excludeID;
          break;
      }
      if (j >= 0 && j < Main.GAME_SIZE * Main.GAME_SIZE) {
        possibleMines.set(j, -1);
      }
    }
    for (int i = 0; i < 9; i = i + 1) {
      for (int k = 0; k < excludeID + Main.GAME_SIZE + 2; k = k + 1) {
        if (possibleMines.get(k) == -1) {
          possibleMines.remove(k);
          break;
        }
      }
    }
    
    for (int i = 0; i < numMines; i = i + 1) {
      index = rand.nextInt(possibleMines.size());
      
      Main.Tiles.get(possibleMines.get(index)).setMine(true);
      possibleMines.remove(index);
    }
  }
  public boolean getMine() {
    return(hasMine);
  }
  public void setMine(boolean bool) {
    hasMine = bool;
  }

  private void setNeighbors() {
    numNeighbors = getNeighbors();
  }

  private int getNeighbors() {
    int output = 0;
    if (onSide != 1 && onBottom != 2 && Main.Tiles.get(ID - Main.GAME_SIZE - 1).getMine()) {
      output = output + 1;
    }
    if (onBottom != 2 && Main.Tiles.get(ID - Main.GAME_SIZE).getMine()) {
      output = output + 1;
    }
    if (onSide != 2 && onBottom != 2 && Main.Tiles.get(ID - Main.GAME_SIZE + 1).getMine()) {
      output = output + 1;
    }
    if (onSide != 1 && Main.Tiles.get(ID - 1).getMine()) {
      output = output + 1;
    }
    if (onSide != 2 && Main.Tiles.get(ID + 1).getMine()) {
      output = output + 1;
    }
    if (onSide != 1 && onBottom != 1 && Main.Tiles.get(ID + Main.GAME_SIZE - 1).getMine()) {
      output = output + 1;
    }
    if (onBottom != 1 && Main.Tiles.get(ID + Main.GAME_SIZE).getMine()) {
      output = output + 1;
    }
    if (onSide != 2 && onBottom != 1 && Main.Tiles.get(ID + Main.GAME_SIZE + 1).getMine()) {
      output = output + 1;
    }
    return(output);
  }
  private void exposeNeighbors() {
    if (onSide != 1 && onBottom != 2) {
      Main.Tiles.get(ID - Main.GAME_SIZE - 1).leftClick();
    }
    if (onBottom != 2) {
      Main.Tiles.get(ID - Main.GAME_SIZE).leftClick();
    }
    if (onSide != 2 && onBottom != 2) {
      Main.Tiles.get(ID - Main.GAME_SIZE + 1).leftClick();
    }
    if (onSide != 1) {
      Main.Tiles.get(ID - 1).leftClick();
    }
    if (onSide != 2) {
      Main.Tiles.get(ID + 1).leftClick();
    }
    if (onSide != 1 && onBottom != 1) {
      Main.Tiles.get(ID + Main.GAME_SIZE - 1).leftClick();
    }
    if (onBottom != 1) {
      Main.Tiles.get(ID + Main.GAME_SIZE).leftClick();
    }
    if (onSide != 2 && onBottom != 1) {
      Main.Tiles.get(ID + Main.GAME_SIZE + 1).leftClick();
    }
    getFrame().repaint();
  }
  
  private void leftClick() {
    if (!getMine() && !exposed) {
      exposed = true;
      changeImage(squareImage, "Images/Minesweeper" + numNeighbors + ".png");
      scaleImage(squareSize,squareSize, squareImage);
      if (numNeighbors == 0) {
        explodeAnimation();
        exposeNeighbors();
      }
    }
    if (getMine() && !exposed) {
      exposed = true;
      changeImage(squareImage, "Images/Mine.png");
      scaleImage(squareSize, squareSize, squareImage);
      System.out.println("You have lost! Win/lose process is a work in progress.");
    }
  }

  private void rightClick() {
    if (!exposed && !flag) {
      changeImage(squareImage, "Images/MinesweeperFlag.png");
      scaleImage(squareSize, squareSize, squareImage);
      flag = true;
    }
    else if (!exposed && flag) {
      changeImage(squareImage, "Images/MinesweeperUnknown.png");
      scaleImage(squareSize, squareSize, squareImage);
      flag = false;
    }
    square.repaint();
  }

  private void explodeAnimation() {
    ImageIcon explosionImage = new ImageIcon("Images/MinesweeperFire.png");
    JLabel explosion = new JLabel(explosionImage);
    Timer timer = new Timer();
    scaleImage(squareSize, squareSize + squareSize / Main.BUFFER_SIZE, explosionImage);
    getFrame().add(explosion);
    explosion.setBounds(square.getX(), square.getY() - squareSize / Main.BUFFER_SIZE + squareSize, squareSize, squareSize + squareSize / Main.BUFFER_SIZE);
    explosion.setVisible(true);
    getFrame().setComponentZOrder(explosion, 0);
    getFrame().repaint();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        explosion.setVisible(false);
        getFrame().remove(explosion);
      }
    }, 2000);
  }




  @Override
  public void actionPerformed (ActionEvent e) {
    
  }
  @Override
  public void mouseClicked (MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON3) {
      rightClick();
    }
    else if (e.getButton() == MouseEvent.BUTTON1) {
      if (firstTile) {
        randomizeMines(Main.NUM_MINES, ID);
        for (int i = 0; i < Main.Tiles.size(); i = i + 1) {
          Main.Tiles.get(i).setNeighbors();
        }
        firstTile = false;
      }
      leftClick();
    }
  }
  @Override
  public void mouseReleased (MouseEvent e) {
    
  }
  @Override
  public void mouseExited (MouseEvent e) {
    
  }
  @Override
  public void mouseEntered (MouseEvent e) {
    
  }
  @Override
  public void mousePressed (MouseEvent e) {
    
  }
}