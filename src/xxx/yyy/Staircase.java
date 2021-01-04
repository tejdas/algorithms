package xxx.yyy;

public class Staircase {
public static void main(final String args[] ) throws Exception {
    final int height = 6; //Integer.valueOf(args[0]);
    printStairCase(height);
}

    static void printStairCase(final int height) {
        for (int index = 1; index <= height; index++) {
            for (int i = 0; i < (height-index); i++) {
                System.out.print(" ");
            }

            for (int i = height-index; i < height; i++) {
                System.out.print("#");
            }
            System.out.println();
        }
    }
}
