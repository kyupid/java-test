package org.example;

public class TreeProcessor {
    public static int lookup(String k, int defaultval, Tree t) {
        if (t == null) {
            return defaultval;
        }
        if (k.equals(t.key)) {
            return t.val;
        }
        return lookup(k, defaultval, k.compareTo(t.key) < 0 ? t.left : t.right);
    }

//    public static void update(String k, int newval, Tree t) {
//        if (t == null) {
//            return;
//        } else if (k.equals(t.key)) {
//            t.val = newval;
//        } else {
//            update(k, newval, k.compareTo(t.key) < 0 ? t.left : t.right);
//        }
//    }

    public static Tree update(String k, int newval, Tree t) {
        if (t == null) {
            // 새로운 노드를 만들어서 반환
            return new Tree(k, newval, null, null);
        } else if (k.equals(t.key)) {
            t.val = newval;
        } else if (k.compareTo(t.key) < 0) {
            t.left = update(k, newval, t.left);
        } else {
            t.right = update(k, newval, t.right);
        }
        return t;
    }

    // q) 보통 update 라고 하면, 기존 객체를 수정하는 것을 의미하지 않나?
    // 새로 객체를 리턴한다면 update가 아닌것 같은데..
    public static Tree fupdate(String k, int newval, Tree t) {
        return (t == null) ?
                new Tree(k, newval, null, null) :
                k.equals(t.key) ?
                        new Tree(k, newval, t.left, t.right) :
                        k.compareTo(t.key) < 0 ?
                                new Tree(t.key, t.val, fupdate(k, newval, t.left), t.right) :
                                new Tree(t.key, t.val, t.left, fupdate(k, newval, t.right));
    }
}
