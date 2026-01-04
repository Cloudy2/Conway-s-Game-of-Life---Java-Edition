import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame implements KeyListener {

   private ImageIcon logo = new ImageIcon("resources/lifeIcon2.png");
   private World world;
   private ControlPanel controlPanel;

   public GameWindow() {
      setIconImage(logo.getImage());
      setTitle("Game of Life - Size: " + App.SIDE_LENGTH + "x" + App.SIDE_LENGTH);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLayout(new BorderLayout());

      addKeyListener(this);

      setFocusable(true);

      world = new World();
      controlPanel = new ControlPanel(world);

      add(controlPanel, "North");
      add(world, "South");

      pack();
      setResizable(false);

      setVisible(true);
   }

   public World getWorld() {
      return this.world;
   }

   public ControlPanel getControlPanel() {
      return this.controlPanel;
   }

   public void keyTyped(KeyEvent e) {
   }

   public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == 32) {
         this.world.playOrPause();
         this.controlPanel.updatePausePlay();
      }

      if (e.getKeyCode() == 39) {
         this.world.iterateAll();
      }

   }

   public void keyReleased(KeyEvent e) {
   }
}
