package algo.btree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class TreeNode {
    public TreeNode(final int value) {
        this.value = value;
    }

    public TreeNode() {
        this.value = 0;
    }

    int value;
    /*
     * Left subtree
     */
    protected TreeNode left = null;
    /*
     * Right subtree
     */
    protected TreeNode right = null;
    /*
     * Pointer to the next node at the same level (sibling or cousin)
     */
    TreeNode next = null;

	public TreeNode findNode(final int value) {
		if (value == this.value) {
			return this;
		}
		TreeNode node = null;
		if (left != null) {
			node = left.findNode(value);
		}
		if (node != null) {
			return node;
		}
		if (right != null) {
			node = right.findNode(value);
		}
		return node;
	}

	public int getHeight() {
	    final int lh = (left != null)? left.getHeight() : 0;
	    final int rh = (right != null)? right.getHeight() : 0;
	    return (lh>rh)? lh+1 : rh+1;
	}

	public TreeNode findParentNode(final int value) {
		if (this.left != null && this.left.value == value) {
			return this;
		}
		if (this.right != null && this.right.value == value) {
			return this;
		}

		TreeNode node = null;
		if (left != null) {
			node = left.findParentNode(value);
		}
		if (node != null) {
			return node;
		}
		if (right != null) {
			node = right.findParentNode(value);
		}
		return node;
	}

    public void traversePreOrder() {
        System.out.println(value + "  ");

        if (left != null) {
            left.traversePreOrder();
        }
        if (right != null) {
            right.traversePreOrder();
        }
    }

    /*
     * Non-recursive in-order traversal, using Stack data structure.
     */
    public static void traverseInOrderNonRecurse(final TreeNode root) {
        final Stack<TreeNode> stack = new Stack<>();
        TreeNode curNode = root;
        int maxLeftDepth = 0;
        TreeNode nodeAtMaxDepth = null;

        /*
         * Go all the way to the left subtree
         */
        while (curNode != null) {
            int curLeftDepth = 1;
            while (curNode.left != null) {
                stack.push(curNode);
                curNode = curNode.left;
                curLeftDepth++;
            }
            if (curLeftDepth > maxLeftDepth) {
                maxLeftDepth = curLeftDepth;
                nodeAtMaxDepth = curNode;
            }

            System.out.print(curNode.value + "  ");
            while ((curNode.right == null) && (!stack.isEmpty())) {
                curNode = stack.pop();
                System.out.print(curNode.value + "  ");
            }
            curNode = curNode.right;
        }
        System.out.println();
        System.out.println(" Max depth: " + maxLeftDepth + " value: " + nodeAtMaxDepth.value);
    }

    /*
     * Non-recursive in-order traversal, using Stack data structure.
     */
    public static int returnKthElement(final TreeNode root, final int k) {
        final Stack<TreeNode> stack = new Stack<>();
        TreeNode curNode = root;
        int iter = 0;

        /*
         * Go all the way to the left subtree
         */
        while (curNode != null) {
            while (curNode.left != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }

            System.out.print(curNode.value + "  ");
            if (++iter == k) {
                return curNode.value;
            }
            while ((curNode.right == null) && (!stack.isEmpty())) {
                curNode = stack.pop();
                System.out.print(curNode.value + "  ");
                if (++iter == k) {
                    return curNode.value;
                }
            }
            curNode = curNode.right;
        }
        return -1;
    }

    /*
     * Non-recursive in-order traversal, using Stack data structure.
     */
    public static TreeNode createCircularList(final TreeNode root) {
        final Stack<TreeNode> stack = new Stack<>();
        TreeNode head = null;

        TreeNode prev = null;
        TreeNode curNode = root;
        /*
         * Go all the way to the left subtree
         */
        while (curNode != null) {
            while (curNode.left != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }

            System.out.print(curNode.value + "  ");
            if (prev == null) {
                prev = curNode;
                head = curNode;
            } else {
                prev.right = curNode;
                curNode.left = prev;
                prev = curNode;
            }

            while ((curNode.right == null) && (!stack.isEmpty())) {
                curNode = stack.pop();
                System.out.print(curNode.value + "  ");

                if (prev == null) {
                    prev = curNode;
                    head = curNode;
                } else {
                    prev.right = curNode;
                    curNode.left = prev;
                    prev = curNode;
                }
            }
            curNode = curNode.right;
        }

        if (prev != null) {
            prev.right = null;
        }
        return head;
    }

    /*
     * Breadth-first-search using a Queue data structure.
     */
    public static void traverseBFS(final TreeNode root) {
        final Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            final TreeNode curNode = queue.remove();
            System.out.print(curNode.value + "  ");
            if (curNode.left != null) {
                queue.add(curNode.left);
            }
            if (curNode.right != null) {
                queue.add(curNode.right);
            }
        }
        System.out.println();
    }

    public static void traverseBFSMultiline(final TreeNode root) {
        TreeNode startNodePtr = null;
        final Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            final TreeNode curNode = queue.remove();
            if (curNode == startNodePtr) {
                startNodePtr = null;
                System.out.println();
            }
            System.out.print(curNode.value + "  ");
            if (curNode.left != null) {
                queue.add(curNode.left);

                if (startNodePtr == null) {
                    startNodePtr = curNode.left;
                }
            }
            if (curNode.right != null) {
                queue.add(curNode.right);

                if (startNodePtr == null) {
                    startNodePtr = curNode.right;
                }
            }
        }
        System.out.println();
    }

    public static int computeMultipleOfSumOfSameLevelLeaves(final TreeNode root) {
        int mult = 1;
        int sum = 0;
        TreeNode startNodePtr = null;
        final Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            final TreeNode curNode = queue.remove();
            if (curNode == startNodePtr) {
                startNodePtr = null;
                System.out.println();

                if (sum > 0) {
                    mult *= sum;
                    sum = 0;
                }
            }

            if (curNode.left != null) {
                queue.add(curNode.left);

                if (startNodePtr == null) {
                    startNodePtr = curNode.left;
                }
            } else if (curNode.right != null) {
                queue.add(curNode.right);

                if (startNodePtr == null) {
                    startNodePtr = curNode.right;
                }
            } else {
                sum += curNode.value;
            }
        }

        System.out.println();
        return mult;
    }

    public void traverseInOrder() {
        if (left != null) {
            left.traverseInOrder();
        }
        System.out.print(value + "  ");
        if (right != null) {
            right.traverseInOrder();
        }
    }

    public void traversePostOrder() {
        if (left != null) {
            left.traversePostOrder();
        }
        if (right != null) {
            right.traversePostOrder();
        }
        System.out.print(value + "  ");
    }
}