import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class Screen implements ActionListener, KeyListener, MouseListener {  
  private static final int SCREEN_WIDTH = 660;
  private static final int SCREEN_HEIGHT = 408;
  
  //set up screen parts here
  private static JFrame mainFrame = new JFrame("Main");
  private static JButton startButton = new JButton("Start");
  private static JButton recenterButton = new JButton("Recenter");

  Screen () {
    
    startButton.setBackground(new Color(2463422));
    startButton.setVisible(true);
    recenterButton.setBackground(new Color(2463422));
    
    startButton.addActionListener(this);
    recenterButton.addActionListener(this);
    mainFrame.addKeyListener(this);
    mainFrame.addMouseListener(this);
  }
  public static void scaleImage(int width, int height, ImageIcon image) {
    image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
  }
  public static void changeImage(ImageIcon image, String newImage) {
    ImageIcon newImageIcon = new ImageIcon(newImage);
    image.setImage(newImageIcon.getImage());
    newImageIcon = null;
  }
  
  public void setScreen () {
    mainFrame.setLayout(null);
    mainFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    mainFrame.setVisible(true);
    mainFrame.setFocusable(true);
    recenter();

    mainFrame.add(startButton);
    mainFrame.add(recenterButton);
  }
  public static void recenter () {
    double screenSize = (double) mainFrame.getWidth() / (double) SCREEN_WIDTH;
    startButton.setBounds(mainFrame.getWidth()/2 - mainFrame.getWidth()/6, mainFrame.getHeight()/2 - mainFrame.getWidth()/10, mainFrame.getWidth()/3, mainFrame.getHeight()/5);
    startButton.setFont(new Font("Courier", Font.BOLD, (int) (25.0 * screenSize)));
    recenterButton.setBounds(0, 0, mainFrame.getWidth()/5, mainFrame.getHeight()/20);
    recenterButton.setFont(new Font("Courier", Font.BOLD, (int) (14 * screenSize)));
    recenterButton.setVisible(true);
    for (int i = 0; i < Main.Tiles.size(); i = i + 1) {
      Main.Tiles.get(i).setPosition();
    }
  }

  public static JFrame getFrame() {
    return(mainFrame);
  }
  public static int getFrameWidth() {
    return(mainFrame.getWidth());
  }
  public static int getFrameHeight() {
    return(mainFrame.getHeight());
  }
  public static double getFrameSize() {
    return((double) mainFrame.getWidth() / (double) SCREEN_WIDTH);
  }
  public static void addTile(JButton tile) {
    mainFrame.add(tile);
  }
  

  @Override
  public void actionPerformed (ActionEvent e) {
    if (e.getSource().equals(recenterButton)) {
      recenter();
    }
    else if (e.getSource().equals(startButton)) {
      for (int i = 0; i < (Main.GAME_SIZE * Main.GAME_SIZE); i = i + 1) {
        Main.Tiles.add(new Tile(i));
        Main.Tiles.get(i).setPosition();
      }
      mainFrame.repaint();
      startButton.setVisible(false);
    }
  }
  @Override
  public void keyPressed (KeyEvent e) {
    
  }
  @Override
  public void keyReleased (KeyEvent e) {
    
  }
  @Override
  public void keyTyped (KeyEvent e) {
    
  }
  @Override
  public void mouseClicked (MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON1) {
      System.out.println("Left");
    }
    if (e.getButton() == MouseEvent.BUTTON3) {
      System.out.println("Right");
    }
  }
  @Override
  public void mouseEntered (MouseEvent e) {
    
  }
  @Override
  public void mouseExited (MouseEvent e) {
    
  }
  @Override
  public void mousePressed (MouseEvent e) {
    
  }
  @Override
  public void mouseReleased (MouseEvent e) {
    
  }
}