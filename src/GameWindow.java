import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame implements KeyListener {

   private ImageIcon logo = new ImageIcon("resources/lifeIcon2.png");
   private World world;
   private ControlPanel controlPanel;

   public GameWindow() {
      this.setIconImage(this.logo.getImage());
      this.setTitle("Game of Life - Size: " + App.SIDE_LENGTH + "x" + App.SIDE_LENGTH);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setLayout(new BorderLayout());

      this.addKeyListener(this);

      this.setFocusable(true);

      this.world = new World();
      this.controlPanel = new ControlPanel(world);

      this.add(this.controlPanel, "North");
      this.add(this.world, "South");

      this.pack();
      this.setResizable(false);

      this.setVisible(true);
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
