package algo.recurse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class RecursiveList {

    static class ListInfo {
        public ListInfo(final List<Object> list) {
            this.list = list;
        }
        List<Object> list;
        int posInList = 0;
    }

    private final List<Object> list = new ArrayList<>();
    private final Stack<ListInfo> stack = new Stack<>();

    public void build(final char[] array) {
        build(array, 0, list);
    }

    private int build(final char[] array, int pos, final List<Object> parentList) {
        char c;
        do {
            if (pos == array.length) return pos;
            c = array[pos++];
            if (c == ' ' || c == ',') continue;
            if (c == ']') return pos;
            if (c == '[') {
                final List<Object> child = new ArrayList<>();
                parentList.add(child);
                pos = build(array, pos, child);
            } else {
                parentList.add(c);
            }
        } while (pos < array.length);
        return pos;
    }

    static void printList(final List<Object> list) {
        for (final Object obj : list) {
            if (obj instanceof Character) {
                System.out.print(obj + " ");
            } else if (obj instanceof List) {
                printList((List<Object>) obj);
            }
        }
    }

    public void reset() {
        stack.clear();
    }

    public void initialize() {
        stack.push(new ListInfo(list));
    }

    public Object getNext() {
        while (!stack.isEmpty()) {
            final ListInfo info = stack.peek();
            final List<Object> list = info.list;
            if (info.posInList == list.size()) {
                stack.pop();
                continue;
            }

            final Object obj = list.get(info.posInList++);
            if (obj instanceof Character) {
                return obj;
            } else if (obj instanceof List) {
                stack.push(new ListInfo((List<Object>) obj));
            }
        }
        return null;
    }

    public void delete(final char c) {
        stack.clear();

        stack.push(new ListInfo(list));

        boolean found = false;

        while (!stack.isEmpty()) {
            ListInfo info = stack.peek();
            List<Object> curList = info.list;

            if (info.posInList == info.list.size()) {
                stack.pop();
                continue;
            }

            final Object obj = curList.get(info.posInList++);
            if (obj instanceof Character) {
                final char val = (char) obj;
                if (val == c) {
                    found = true;
                }
            } else if (obj instanceof List) {
                stack.push(new ListInfo((List<Object>) obj));
            }

            if (found) {
                do {
                    info = stack.pop();
                    curList = info.list;
                    curList.remove(--info.posInList);
                } while (!stack.isEmpty() && curList.isEmpty());
                break;
            }
        }
    }

    public boolean hasNext() {
        final Iterator<ListInfo> it = stack.iterator();
        while (it.hasNext()) {
            final ListInfo info = it.next();
            if (info.posInList < info.list.size()) {
                return true;
            }
        }
        return false;
    }

    public static void main1(final String[] args) {
        final RecursiveList recList = new RecursiveList();
        recList.build("[a, b, [c, d], [e, f], g, [h, [i,j, [k], l], m, n], [o, p], [], q]".toCharArray());
        //recList.build("[[[a]], b, [[c, [d]], e, f, ]]".toCharArray());
        recList.initialize();
        while (recList.hasNext()) {
            final Object obj = recList.getNext();
            if (obj == null) break;
            System.out.println(obj);
        }
    }

    public static void main(final String[] args) {
        final RecursiveList recList = new RecursiveList();
        recList.build("[a, b, [c, d], [e, f], g, [h, [i,j, [[k]], l], m, n], [o, p], [], q]".toCharArray());

        //recList.build("[a, [b, [[e]], f], c, k, q]".toCharArray());

        //recList.build("[a, [b]]".toCharArray());
        printList(recList.list);
        System.out.println();

        recList.delete('k');

        printList(recList.list);
        System.out.println();
    }
}
