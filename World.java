// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class World extends JPanel implements MouseListener {
   private int SIDE_LENGTH;
   private Cell[][] cellArray;
   private GridLayout grid;
   private boolean dragActive;
   public Timer timer;
   public boolean timerActive;

   public World() {
      SIDE_LENGTH = App.SIDE_LENGTH;
      int controlWorldSize = 500 / this.SIDE_LENGTH * this.SIDE_LENGTH;
      setPreferredSize(new Dimension(controlWorldSize, controlWorldSize));
      setBackground(Color.white);
      setOpaque(true);

      cellArray = new Cell[this.SIDE_LENGTH][this.SIDE_LENGTH];
      grid = new GridLayout(this.SIDE_LENGTH, this.SIDE_LENGTH);
      
      setLayout(this.grid);

      timer = new Timer(100, new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            World.this.iterateAll();
            World.this.updateAllStatus();
         }
      });
      timerActive = false;

      loadCells();
   }

   public Cell[][] getCellArray() {
      return this.cellArray;
   }

   public void mapImageToWorld(BufferedImage image) {
      for(int y = 0; y < image.getWidth(); ++y) {
         for(int x = 0; x < image.getHeight(); ++x) {
            int pixel = image.getRGB(y, x);
            if (pixel == -16777216) {
               System.out.println("HEAR YE! PIXEL IS BLACK!");
               this.cellArray[y][x].setDead();
            } else if (pixel == -1) {
               System.out.println("HEAR YE! PIXEL IS WHITE!");
               this.cellArray[y][x].setAlive();
            } else {
               System.out.println("HEAR YE! PIXEL IS TWEAKING!");
            }
         }
      }

   }

   private void loadCells() {
      for(int y = 0; y < this.SIDE_LENGTH; ++y) {
         for(int x = 0; x < this.SIDE_LENGTH; ++x) {
            Cell newCell = new Cell(x, y);
            newCell.addMouseListener(this);
            this.cellArray[y][x] = newCell;
            this.add(newCell);
         }
      }

   }

   public void iterateAll() {
      Cell[][] var4;
      int var3 = (var4 = this.cellArray).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         Cell[] arr = var4[var2];
         Cell[] var8 = arr;
         int var7 = arr.length;

         for(int var6 = 0; var6 < var7; ++var6) {
            Cell cell = var8[var6];
            cell.iterate(this.cellArray);
         }
      }

   }

   private void updateAllStatus() {
      Cell[][] var4;
      int var3 = (var4 = this.cellArray).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         Cell[] arr = var4[var2];
         Cell[] var8 = arr;
         int var7 = arr.length;

         for(int var6 = 0; var6 < var7; ++var6) {
            Cell cell = var8[var6];
            if (cell.getNextStatus()) {
               cell.setAlive();
            } else {
               cell.setDead();
            }
         }
      }

   }

   public void clearWorld() {
      Cell[][] var4;
      int var3 = (var4 = this.cellArray).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         Cell[] arr = var4[var2];
         Cell[] var8 = arr;
         int var7 = arr.length;

         for(int var6 = 0; var6 < var7; ++var6) {
            Cell cell = var8[var6];
            cell.setDead();
         }
      }

   }

   public void playOrPause() {
      if (this.timerActive) {
         this.timer.stop();
      } else {
         this.timer.start();
      }

      this.timerActive = !this.timerActive;
   }

   public void mouseClicked(MouseEvent e) {
      if (e.getSource() instanceof Cell) {
         Cell cell = (Cell)e.getSource();
         cell.swapStatus();
      }

   }

   public void mousePressed(MouseEvent e) {
      this.dragActive = true;
   }

   public void mouseReleased(MouseEvent e) {
      this.dragActive = false;
   }

   public void mouseEntered(MouseEvent e) {
      Cell cell;
      if (e.getSource() instanceof Cell && !this.timerActive) {
         cell = (Cell)e.getSource();
         cell.setBackground(Color.gray);
      }

      if (this.dragActive) {
         cell = (Cell)e.getSource();
         cell.setAlive();
      }

   }

   public void mouseExited(MouseEvent e) {
      if (e.getSource() instanceof Cell && !this.timerActive) {
         Cell cell = (Cell)e.getSource();
         if (cell.getStatus()) {
            cell.setBackground(Color.white);
         } else {
            cell.setBackground(Color.black);
         }
      }

   }
}
