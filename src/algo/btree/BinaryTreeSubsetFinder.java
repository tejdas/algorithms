package algo.btree;

import java.util.LinkedList;
import java.util.Queue;

import algo.btree.BinaryTreeBuilder.TreeInfo;

public class BinaryTreeSubsetFinder {
	public static boolean isSubsetOf(TreeNode parent, TreeNode child) {
		if (parent == null) {
			return false;
		}

		if (child == null) {
			return true;
		}
		if (parent.value == child.value) {
			return isExactMatch(parent, child);
		} else {
			return isSubsetOf(parent.left, child) || isSubsetOf(parent.right, child);
		}
	}
	
	public static boolean isExactMatch(TreeNode t1, TreeNode t2) {
		if (t1==null && t2==null) {
			return true;
		}
		if (t1!=null && t2==null) {
			return false;
		}
		if (t1==null && t2!=null) {
			return false;
		}

		if (t1.value != t2.value) {
			return false;
		}
		return isExactMatch(t1.left, t2.left) && isExactMatch(t1.right, t2.right);
	}

    public static void main(String[] args) {
        Queue<TreeInfo> inputs = new LinkedList<>();
        inputs.add(new TreeInfo(1, true, true));
        inputs.add(new TreeInfo(2, true, true));
        inputs.add(new TreeInfo(3, false, false));
        inputs.add(new TreeInfo(4, true, true));
        inputs.add(new TreeInfo(5, false, false));
        inputs.add(new TreeInfo(6, true, true));
        inputs.add(new TreeInfo(7, true, false));
        inputs.add(new TreeInfo(8, true, false));
        inputs.add(new TreeInfo(9, false, false));
        inputs.add(new TreeInfo(10, false, false));
        inputs.add(new TreeInfo(11, true, true));
        inputs.add(new TreeInfo(12, false, false));
        inputs.add(new TreeInfo(13, true, true));
        inputs.add(new TreeInfo(14, false, true));

        inputs.add(new TreeInfo(18, true, false));
        inputs.add(new TreeInfo(19, true, true));
        inputs.add(new TreeInfo(20, true, false));
        inputs.add(new TreeInfo(21, true, false));
        inputs.add(new TreeInfo(22, false, true));
        inputs.add(new TreeInfo(23, false, false));
        inputs.add(new TreeInfo(24, false, false));

        inputs.add(new TreeInfo(15, false, true));
        inputs.add(new TreeInfo(16, true, false));
        inputs.add(new TreeInfo(17, false, false));

        Queue<TreeInfo> inputs2 = new LinkedList<>();
        inputs2.addAll(inputs);
        
        Queue<TreeInfo> inputs3 = new LinkedList<>();
        for (TreeInfo input : inputs) {
        	if (input.value!=23 && input.value!=17) {
        		inputs3.add(input);
        	}
        }

        TreeNode root = BinaryTreeBuilder.buildTree(inputs);
        TreeNode root2 = BinaryTreeBuilder.buildTree(inputs2);
        TreeNode root3 = BinaryTreeBuilder.buildTree(inputs3);

        boolean matches = isExactMatch(root, root2);
        System.out.println(matches);
        matches = isExactMatch(root, root);
        System.out.println(matches);
        matches = isExactMatch(root, root3);
        System.out.println(matches);        
    }
}
