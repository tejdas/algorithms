package algo.btree;

public class BinarySearchTreeValidator {
    static int lastSeenValue = Integer.MIN_VALUE;

    static int findMax(final TreeNode node) {
        final int leftMax = (node.left != null) ? findMax(node.left) : Integer.MIN_VALUE;
        final int rightMax = (node.right != null) ? findMax(node.right) : Integer.MIN_VALUE;

        return Math.max(node.value,  Math.max(leftMax, rightMax));
    }

    static int findMin(final TreeNode node) {
        final int leftMin = (node.left != null) ? findMin(node.left) : Integer.MAX_VALUE;
        final int rightMin = (node.right != null) ? findMin(node.right) : Integer.MAX_VALUE;

        return Math.min(node.value,  Math.min(leftMin, rightMin));
    }

    static boolean isBSTRecurse(final TreeNode node) {
        if (node.left != null) {
            if (findMax(node.left) > node.value) {
                return false;
            }
        }

        if (node.right != null) {
            if (findMin(node.right) < node.value) {
                return false;
            }
        }

        if (node.left != null) {
            if (!isBSTRecurse(node.left)) {
                return false;
            }
        }

        if (node.right != null) {
            if (!isBSTRecurse(node.right)) {
                return false;
            }
        }

        return true;
    }

    static boolean isBST(final TreeNode root) {
        lastSeenValue = Integer.MIN_VALUE;
        return isBinarySearchTree(root);
    }

    static boolean isBinarySearchTree(final TreeNode node) {
        if (node.left != null) {
            if (!isBinarySearchTree(node.left))
                return false;
        }

        if (node.value < lastSeenValue)
            return false;
        lastSeenValue = node.value;
        if (node.right != null)
            return isBinarySearchTree(node.right);
        else
            return true;
    }

    static void flipBinaryTree(final TreeNode node) {
        if (node.left != null)
            flipBinaryTree(node.left);

        if (node.right != null)
            flipBinaryTree(node.right);

        final TreeNode temp = node.left;
        node.left = node.right;
        node.right = temp;
    }

    static int findLowestCommonParent(final TreeNode node, final int valA, final int valB) {
        if (valA > valB)
            return -1;
        if (node == null)
            return -1;
        if (valA < node.value && valB > node.value) {
            return node.value;
        }

        if (valA < node.value && valB < node.value) {
            return findLowestCommonParent(node.left, valA, valB);
        }

        if (valA > node.value && valB > node.value) {
            return findLowestCommonParent(node.right, valA, valB);
        }
        return -1;
    }

    static boolean isArraySorted(final int[] array) {
        return isArraySorted(array, 0, array.length - 1);
    }

    static int callCount = 0;
    static boolean isArraySorted(final int[] array, final int from, final int to) {
        System.out.println(from + "   " + to);
        callCount++;
        if (from == to)
            return true;
        if (to == from + 1)
            return (array[from] < array[to]);
        final int median = (from + to) / 2;
        if ((array[from] > array[median]) || (array[median] > array[to])) {
            return false;
        }

        if (from < median-1) {
            if (!isArraySorted(array, from, median-1))
                return false;
        }

        if (to > median+1) {
            if (!isArraySorted(array, median+1, to))
                return false;
        }
        return true;
    }

    public static void main1(final String[] args) {
        final int[] array = {1, 4, 8, 9, 12, 15, 19, 22, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100, 105, 110, 115, 120,125, 130, 135, 140, 145, 150};
        final TreeNode root = BinaryTreeBuilder.buildTree(array, 0, array.length - 1);

        System.out.println(isBST(root));


        System.out.println(isArraySorted(array));
        System.out.println(callCount);
        System.out.println("array length: " + array.length);

    }

    public static void main2(final String[] args) {
        final int[] array = {1, 4, 8, 9, 12, 15, 19, 22, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100, 105, 110, 115, 120,125, 130, 135, 140, 145, 150};
        final TreeNode root = BinaryTreeBuilder.buildTree(array, 0, array.length - 1);
        //flipBinaryTree(root);
        root.traverseInOrder();
        System.out.println();

        final int parent = findLowestCommonParent(root, 1, 4);
        System.out.println(parent);

    }

    public static void main(final String[] args) {
        final int[] array = {1, 4, 8, 9, 12, 15, 19, 22, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100, 155, 110, 115, 120,125, 130, 135, 140, 145, 150};
        //final int[] array = {1, 2, 10, 4, 5};
        final TreeNode root = BinaryTreeBuilder.buildTree(array, 0, array.length - 1);
        root.traverseInOrder();
        System.out.println();
        //root.traversePreOrder();
        System.out.println();
        System.out.println(isBSTRecurse(root));
    }
}
