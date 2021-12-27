package net.lc.stack;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * 341
 * Stack
 */
interface NestedInteger {

    // @return true if this NestedInteger holds a single integer, rather than a nested list.
    public boolean isInteger();

    // @return the single integer that this NestedInteger holds, if it holds a single integer
    // Return null if this NestedInteger holds a nested list
    public Integer getInteger();

    // @return the nested list that this NestedInteger holds, if it holds a nested list
    // Return null if this NestedInteger holds a single integer
    public List<NestedInteger> getList();
}

public class FlattenNestedListIterator implements Iterator<Integer> {
    static class ListInfo {
        List<NestedInteger> ni;
        int cursor = 0;

        public ListInfo(List<NestedInteger> ni) {
            this.ni = ni;
        }
    }

    private Stack<FlattenNestedListIterator.ListInfo> stack = new Stack<>();

    public FlattenNestedListIterator(List<NestedInteger> nestedList) {
        stack.push(new FlattenNestedListIterator.ListInfo(nestedList));
    }

    @Override
    public boolean hasNext() {
        while (!stack.isEmpty()) {
            final FlattenNestedListIterator.ListInfo info = stack.peek();

            final List<NestedInteger> list = info.ni;
            if (info.cursor == list.size()) {
                stack.pop();
                continue;
            }

            final NestedInteger obj = list.get(info.cursor);
            if (obj.isInteger()) {
                return true;
            } else {
                info.cursor++;
                stack.push(new FlattenNestedListIterator.ListInfo(obj.getList()));
            }

        }
        return false;
    }

    @Override
    public Integer next() {

        final FlattenNestedListIterator.ListInfo info = stack.peek();
        final List<NestedInteger> list = info.ni;
        final NestedInteger obj = list.get(info.cursor++);
        if (obj.isInteger()) {
            return obj.getInteger();
        }
        return null;
    }
}
