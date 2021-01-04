package xxx.yyy;

public class PascalTriangleBuilder {
    private static void buildTriangle(int rowCount) {
        int[] newRow = new int[] {1};
        int[] oldRow;
        for (int i=0; i < rowCount; i++) {
            for (int j = 0; j < newRow.length; j++) {
                System.out.print(newRow[j] + "  " );
            }
            System.out.println();

            oldRow = newRow;
            newRow = new int[oldRow.length+1];
            newRow[0] = 1;
            newRow[newRow.length-1] = 1;
            for (int k = 0; k < oldRow.length-1; k++) {
                newRow[k+1] = oldRow[k] + oldRow[k+1];
            }
        }
    }

    public static void main(String[] args) {
        buildTriangle(10);
    }
}
