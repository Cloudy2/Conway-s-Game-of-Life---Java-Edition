import java.awt.Color;
import javax.swing.JLabel;

public class Cell extends JLabel {
   private boolean nextToLive;
   private boolean alive = false;
   private int xPos;
   private int yPos;

   public Cell(int xPos, int yPos) {
      this.setBackground(Color.black);
      this.setOpaque(true);
      this.xPos = xPos;
      this.yPos = yPos;
   }

   public int iterate(Cell[][] cellArray) {
      int liveNeighbors = this.getLiveNeighbors(cellArray);
      boolean shouldLive = this.checkRules(liveNeighbors);
      if (shouldLive) {
         this.setNextToLive();
      } else {
         this.setNextToDie();
      }

      return liveNeighbors;
   }

   public void swapStatus() {
      if (this.alive) {
         this.setDead();
      } else {
         this.setAlive();
      }

   }

   public void setAlive() {
      this.alive = true;
      this.setBackground(Color.white);
   }

   public void setDead() {
      this.alive = false;
      this.setBackground(Color.black);
   }

   private void setNextToLive() {
      this.nextToLive = true;
   }

   private void setNextToDie() {
      this.nextToLive = false;
   }

   public boolean getNextStatus() {
      return this.nextToLive;
   }

   public boolean getStatus() {
      return this.alive;
   }

   private boolean checkRules(int numLiveNeighbors) {
      if (this.alive) {
         if (numLiveNeighbors < 2) {
            return false;
         } else {
            return numLiveNeighbors <= 3;
         }
      } else {
         return numLiveNeighbors == 3;
      }
   }

   private int getLiveNeighbors(Cell[][] arr) {
      int sum = 0;
      int arrayLength = arr.length;
      
      this.yPos %= arrayLength;
      this.xPos %= arrayLength;

      for(int k = this.yPos - 1; k <= this.yPos + 1; ++k) {
         int curKIndex = (k % arrayLength + arrayLength) % arrayLength;

         for(int u = this.xPos - 1; u <= this.xPos + 1; ++u) {
            int curUIndex = (u % arrayLength + arrayLength) % arrayLength;
            if ((curKIndex != this.yPos || curUIndex != this.xPos) && arr[curKIndex][curUIndex].alive) {
               ++sum;
            }
         }
      }

      return sum;
   }
}