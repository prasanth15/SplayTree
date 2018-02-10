/** 
 *  Implementation of RedBlack Tree
 *  @author Varun Muthanna(vkm150030), Prasanth Kesava Pillai(pxk163630)
 *	& Shivan Pandya(srp150330)
 **/
package cs6301.g60;

import java.util.Scanner;

public class RedBlackTree<T extends Comparable<? super T>> extends BST<T> {
    static class Entry<T> extends BST.Entry<T> {
        boolean isRed;
        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            isRed = false;
        }
    }

    RedBlackTree() {
	super();
    }

    protected Entry<T> createSentinalNode(){
        return new Entry<>(null, null, null);
    }

    protected void postDoubleRotationColoring(Entry<T> gp, Entry<T> curr){
        gp.isRed = true;
        curr.isRed = false;
    }

    protected void postSingleRotationColoring(Entry<T> p, Entry<T> gp) {
        p.isRed = false;
        gp.isRed = true;
    }

        @Override
    public boolean add(T element){
        if(root==null){
            Entry<T> root = new Entry<T>(element, createSentinalNode(), createSentinalNode());
            root.isRed = false;

            super.root = root;
            size = 1;
            return true;
        }

        Entry<T> dummyRoot = new Entry<T>(null, null, (Entry<T>) root);
        Entry<T> gp = null, gp_p = dummyRoot, p = null, curr;
        curr = (Entry<T>) root;

        while (true){

            if(curr.element==null) {
                if(p.element.compareTo(element)>0){
                    curr = new Entry<T>(element, createSentinalNode(), createSentinalNode());
                    curr.isRed = true;
                    p.left = curr;
                }else {
                    curr = new Entry<T>(element, createSentinalNode(), createSentinalNode());
                    curr.isRed = true;
                    p.right = curr;
                }

                size++;
            }else if( ((Entry<T>)curr.left).isRed && ((Entry<T>)curr.right).isRed){
                curr.isRed = true;
                ((Entry<T>)curr.left).isRed = false;
                ((Entry<T>)curr.right).isRed = false;
            }

            if(curr.isRed && (p!=null && p.isRed)) {
                BST.Entry<T> result = null;
                if(gp!=null) {
                    if (gp.left.element != null && gp.left.equals(p)) {
                        if (p.left != null && p.left.equals(curr)) {
                            result = Balance.singleRightRotate(gp);
                            postSingleRotationColoring(p, gp);
                        } else if (p.right != null) {
                            result = Balance.doubleLeftRightRotate(gp);
                            postDoubleRotationColoring(gp, curr);
                        }
                    } else {
                        if (p.left != null && p.left.equals(curr)) {
                            result = Balance.doubleRightLeftRotate(gp);
                            postDoubleRotationColoring(gp, curr);
                        } else if (p.right != null) {
                            result = Balance.singleLeftRotate(gp);
                            postSingleRotationColoring(p, gp);
                        }
                    }
                    if (gp_p == null) {
                        root = result;
                    } else {
                        if (gp_p.element != null && gp_p.left.equals(gp)) {
                            gp_p.left = result;
                        } else {
                            gp_p.right = result;
                        }
                    }
                }
            }

            if(curr.element.equals(element)) {
                break;
            }

            gp_p = gp;
            gp = p;
            p = curr;
            if(curr.element.compareTo(element)>0) {
                curr = (Entry<T>) curr.left;
            }else{
                curr = (Entry<T>) curr.right;
            }
        }
        ((Entry<T>) root).isRed = false;
        return true;
    }

    @Override
    public T remove(T element) {
        Entry<T> dummyRoot = new Entry<T>(null, null, (Entry<T>) root);
        Entry<T> nodeToDelete = null, p = null, g = null, curr;

        curr = dummyRoot;
        Entry<T> next = traverse(curr, element);

        while ( next.element != null){
            // sets up the pointers
            g = p;
            p = curr;
            curr = next;
            next = traverse(curr, element);

            if(curr.element.equals(element)) {
                nodeToDelete = curr;
            }
            /**
             * if curr node is black
             * and 'next' node is also black
             */
            if(!curr.isRed &&!next.isRed){

                Entry<T> sibling = sibling(curr, next);
                /**
                 * check on the 'next's'  sibling's is red
                 * case 2B
                 */
                if(sibling.isRed) {
                    BST.Entry<T> res = null;
                    if(curr.left.equals(next)){
                        res = Balance.singleLeftRotate(curr);

                    }else {
                        res = Balance.singleRightRotate(curr);
                    }
                    if(p.right.equals(curr)){
                        p.right = res;
                        p = (Entry<T>) p.right;
                    }else{
                        p.left = res;
                        p = (Entry<T>) p.left;
                    }
                    setColorsAfterRotation(curr, sibling);
                }
                // if next's sibling is black
                else if(!sibling.isRed) {
                    //find curr's sibling
                    Entry<T> siblingWithParent = sibling(p, curr);
                    if(siblingWithParent.element!=null){

                        // if siblingWithParent has black children
                        if(!((Entry<T>)siblingWithParent.left).isRed && !((Entry<T>)siblingWithParent.right).isRed){
                            p.isRed = false; curr.isRed = true; siblingWithParent.isRed = true;
                        }
                        // double rotations needed
                        else {
                            //left is red
                            if(((Entry<T>)siblingWithParent.left).isRed){
                                BST.Entry<T> result = null;

                                if(p.left.equals(siblingWithParent)) {
                                    if (g.right.equals(p)) {
                                        g.right = Balance.singleRightRotate(p);
                                        setColorsAfterRotation(p, siblingWithParent);
                                        setColors((Entry<T>) g.right);
                                    } else {
                                        g.left = Balance.singleRightRotate(p);
                                        setColorsAfterRotation(p, siblingWithParent);
                                        setColors((Entry<T>) g.left);

                                    }
                                }else{
                                    if (g.right.equals(p)) {
                                        g.right = Balance.doubleRightLeftRotate(p);
                                        setColors((Entry<T>) g.right);
                                    } else {
                                        g.left = Balance.doubleRightLeftRotate(p);
                                        setColors((Entry<T>) g.left);
                                    }
                                }
                            } else if(((Entry<T>)siblingWithParent.right).isRed){
                                BST.Entry<T> result = null;

                                if(p.left.equals(siblingWithParent)) {
                                    if (g.right.equals(p)) {
                                        g.right = Balance.doubleLeftRightRotate(p);
                                        setColors((Entry<T>) g.right);
                                    } else {
                                        g.left = Balance.doubleLeftRightRotate(p);
                                        setColors((Entry<T>) g.left);
                                    }
                                } else {
                                    if (g.right.equals(p)) {
                                        g.right = Balance.singleLeftRotate(p);
                                        setColorsAfterRotation(p, siblingWithParent);
                                        setColors((Entry<T>) g.right);
                                    } else {
                                        g.left = Balance.singleLeftRotate(p);
                                        setColorsAfterRotation(p, siblingWithParent);
                                        setColors((Entry<T>) g.left);
                                    }
                                }
                            }
                            curr.isRed = true;
                        }
                    }
                }
            }
        }

        if(nodeToDelete!=null){

            nodeToDelete.element = curr.element;
            if(p.left!=null && p.left.equals(curr)){
                p.left = curr.left==null?curr.left:curr.right;
            }else {
                p.right = curr.left==null?curr.left:curr.right;
            }
            size--;
        }


        root = dummyRoot.right;
        if(root!=null){  ((Entry<T>) root).isRed = false;  }

        return null;
    }

    /**
     * helper methods
     */
    private void setColors(Entry<T> entry){
        entry.isRed = true;
        ((Entry<T>) entry.left).isRed = false;
        ((Entry<T>) entry.right).isRed = false;
    }

    private Entry<T> traverse(Entry<T> entry, T element){
        if(entry.element==null){
            return (Entry<T>) entry.right;
        }
        if (entry.element.compareTo(element)>0){
            return (Entry<T>) entry.left;
        } else if(entry.element.compareTo(element)<0){
            return (Entry<T>) entry.right;
        } else{
            if(entry.left.element!=null) {
                return (Entry<T>) entry.left;
            }
            return (Entry<T>) entry.right;
        }
    }

    private Entry<T> sibling(Entry<T> parent, Entry<T> child){
        if(parent.element==null) {
            return (Entry<T>) parent.right;
        }
        if(parent.left.element!=null && parent.left.element.equals(child.element)){
            return (Entry<T>) parent.right;
        }else{
            return (Entry<T>) parent.left;
        }
    }

    private void setColorsAfterRotation(Entry<T> n1, Entry<T> n2){
        n1.isRed = true;
        n2.isRed = false;
    }

    public static void main(String[] st) {
        RedBlackTree<Integer> t = new RedBlackTree<>();
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int x = in.nextInt();
            if(x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if(x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                for(Integer node : t){
                    System.out.print(node+" ");
                }
                System.out.println();
                in.close();
                return;
            }
        }
        in.close();
    }
}
