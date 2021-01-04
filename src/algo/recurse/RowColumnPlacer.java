package algo.recurse;

public class RowColumnPlacer {
    static void placeRowColumn(int[] array, int numColumns) {
        int size = array.length;
        int fullRowCount = size / numColumns;
        int partialRowLength = size % numColumns;
        int numRows = (partialRowLength == 0) ? fullRowCount : fullRowCount + 1;

        for (int i = 0; i < numRows; i++) {
            int index = i;
            for (int j = 0; j < numColumns; j++) {
                /*
                 * Last row might be partial. Handle it correctly.
                 */
                if ((partialRowLength > 0) && (i == numRows - 1) && (j >= partialRowLength)) {
                    break;
                }
                System.out.print(array[index] + " ");
                if (j < partialRowLength) {
                    index = index + numRows;
                    if (index >= size)
                        break;
                } else {
                    index = index + fullRowCount;
                    if (index >= size)
                        break;
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22 };
        placeRowColumn(array, 6);
    }
}
