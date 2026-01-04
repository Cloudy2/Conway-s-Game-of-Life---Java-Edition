import java.util.Scanner;

public class App {
   public static int SIDE_LENGTH;
   
   public static void main(String[] args) throws Exception {
      System.out.println(System.getProperty("java.class.path"));
      Scanner input = new Scanner(System.in);
      System.out.print("Input side length of board: ");
      SIDE_LENGTH = 50;
      input.close();
      new GameWindow();
      System.out.println("You can draw on the world.\nPress [SPACE] to play and pause.");
   }
}