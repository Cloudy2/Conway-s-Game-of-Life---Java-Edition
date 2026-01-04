import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ControlPanel extends JPanel implements MouseListener, ActionListener {
   private FlowLayout flowLayout;
   private World world;

   private int SIDE_LENGTH;
   private int buttonWidth;
   private int buttonHeight;

   private ArrayList<NamedButton> buttons;

   public ControlPanel(World world) {
      SIDE_LENGTH = App.SIDE_LENGTH;
      buttons = new ArrayList<NamedButton>();
      flowLayout = new FlowLayout();

      this.world = world;

      int controlWidth = 500 / this.SIDE_LENGTH * this.SIDE_LENGTH;
      setPreferredSize(new Dimension(controlWidth, 100));
      setSize(controlWidth, 100);
      setBackground(Color.gray);

      buttonWidth = this.getWidth() / 5;
      buttonHeight = this.getHeight() / 4;

      createNewButton("Play");
      createNewButton("Next");
      createNewButton("Reset");
      createNewButton("Import");
   }

   private void createNewButton(String name) {
      NamedButton button = new NamedButton(name);
      button.addMouseListener(this);
      this.add(button);
      this.buttons.add(button);
   }

   private void convertFileToImage(File file) {
      try {
         BufferedImage image = ImageIO.read(file);
         if (image.getWidth() == SIDE_LENGTH && image.getHeight() == SIDE_LENGTH) {
            this.world.mapImageToWorld(image);
         } else {
            System.out.println("Image not the same size as the world!");
         }
      } catch (IOException e) {
         System.err.println("Error reading the image file: " + e.getMessage());
         e.printStackTrace();
      } catch (IllegalArgumentException e) {
         System.err.println("File is null or does not exist: " + e.getMessage());
      }

   }

   public void updatePausePlay() {
      NamedButton pauseButton = (NamedButton)this.buttons.get(0);
      if (this.world.timerActive) {
         pauseButton.setText("Pause");
      } else {
         pauseButton.setText("Play");
      }

   }

   public void actionPerformed(ActionEvent e) {
   }

   public void mouseClicked(MouseEvent e) {
      if (e.getSource() instanceof NamedButton) {
         NamedButton button = (NamedButton)e.getSource();
         if (button.name.equals("Play")) {
            if (this.world.timerActive) {
               button.setText("Play");
               this.world.playOrPause();
            } else {
               button.setText("Pause");
               this.world.playOrPause();
            }
         }

         if (button.name.equals("Next")) {
            this.world.iterateAll();
         }

         if (button.name.equals("Reset")) {
            this.world.clearWorld();
         }

         if (button.name.equals("Import")) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            int response = chooser.showSaveDialog(chooser);
            if (response == 0) {
               File file = new File(chooser.getSelectedFile().getAbsolutePath());
               this.convertFileToImage(file);
            }
         }
      }

   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   private class NamedButton extends JButton {
      
      public String name;

      public NamedButton(String name) {
         this.name = name;
         this.setText(name);
         this.setFocusable(false);
         this.setFont(new Font("Arial", 0, 15));
         this.setForeground(Color.black);
         this.setPreferredSize(new Dimension(ControlPanel.this.buttonWidth, ControlPanel.this.buttonHeight));
      }
   }

}
