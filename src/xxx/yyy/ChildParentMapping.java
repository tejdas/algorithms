package xxx.yyy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ChildParentMapping {

    /*
     * Class representing a RandomListNode, and a columnId that it is at.
     */
    static class NodeInfo {

        public NodeInfo(final int columnId, final int nodeId) {
            super();
            this.columnId = columnId;
            this.nodeId = nodeId;
        }
        int columnId;
        int nodeId;
    }

    /*
     * Map of RowId->NodeInfo (since each row contains ONLY ONE RandomListNode)
     * Space: O(n)
     */
    static Map<Integer, NodeInfo> nodePerRowMap = new HashMap<>();

    /*
     * Contains a list of RowIds (corresponding to Nodes) per-columnId
     * ColumnId -> {List of RowIds where a node is present}
     * This list is used to determine the parent of a RandomListNode.
     *
     * Per definition, the parent of a node is the node at the highest {parentRow, parentColumn} such that:
     * parentRow < childRow and parentColumn < childColumn
     * Space: O(n)
     */
    static Map<Integer, List<Integer>> nodesPerColumnMap = new HashMap<>();

    /*
     * Map of Child NodeId => Parent NodeId. This contains the final output.
     * Space: O(n)
     */
    static Map<Integer, Integer> childParentMap = new HashMap<>();

    /*
     * This method is called after the initial processing of CSV, and contains a list of NodeInfo,
     * with the index in the list implicitly representing the rowId, since each row contains only
     * one RandomListNode.
     */
    static void processCSV(final List<NodeInfo> input) {

        /*
         * Build the nodePerRowMap and nodesPerColumnMap.
         * Time complexity: O(n)
         */
        for (int rowId = 0; rowId < input.size(); rowId++) {
            final NodeInfo info = input.get(rowId);
            nodePerRowMap.put(rowId,  info);

            List<Integer> nodes = nodesPerColumnMap.get(info.columnId);
            if (nodes == null) {
                nodes = new ArrayList<>();
                nodesPerColumnMap.put(info.columnId, nodes);
            }

            nodes.add(rowId);
        }

        computeChildParentRelationship(input);
    }

    /*
     * Evaluate the Nodes row-by-row and compute
     * the childNode => parentNode relationship
     */
    private static void computeChildParentRelationship(final List<NodeInfo> input) {

        for (int rowId = 0; rowId < input.size(); rowId++) {
            final NodeInfo info = nodePerRowMap.get(rowId);

            if (info.columnId == 0) {
                childParentMap.put(info.nodeId, -1);
            } else {
                /*
                 * Go back to the previous columns to find the parentNode.
                 * The parentNode is at position {parentRow, parentCol}
                 * such that, parentRow is the highest number < currentRow
                 * and parentCol is the highest number < currentColumn
                 */
                for (int prevCol = info.columnId-1; prevCol >= 0; prevCol--) {

                    final List<Integer> nodesPerColumn = nodesPerColumnMap.get(prevCol);
                    if (nodesPerColumn == null) {
                        continue;
                    }

                    boolean foundParent = false;
                    /*
                     * Scan the nodes from bottom-up to find out the nearest node that
                     * would be the parent.
                     */
                    for (int iter = nodesPerColumn.size()-1; iter >= 0; iter--) {
                        final int rowIndex = nodesPerColumn.get(iter);
                        if (rowIndex < rowId) {
                            foundParent = true;
                            childParentMap.put(info.nodeId, nodePerRowMap.get(rowIndex).nodeId);
                            break;
                        }
                    }

                    if (foundParent) {
                        break;
                    } else {
                        // Did not find any node in the previous column that contains a rowId less
                        // than current rowId, so continue looking for in the previous column
                    }
                }
            }
        }
    }

    public static void main(final String[] args) {

        /*
         * Assume that the following input has been parsed from a CSV in a format
         * {rowId, columnId, nodeId}
         *
         * The rowId is implicit from the index in the ArrayList.
         */
        final List<NodeInfo> input = new ArrayList<>();
        input.add(new NodeInfo(0, 1));
        input.add(new NodeInfo(1, 2));
        input.add(new NodeInfo(1, 3));
        input.add(new NodeInfo(3, 4));

        input.add(new NodeInfo(2, 5));
        input.add(new NodeInfo(3, 6));

        input.add(new NodeInfo(2, 7));


        input.add(new NodeInfo(5, 9));

        processCSV(input);

        for (final Entry<Integer, Integer> pair : childParentMap.entrySet()) {
            System.out.println("node: " + pair.getKey() + "  parentNode: " + pair.getValue());
        }
    }
}
