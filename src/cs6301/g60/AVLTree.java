/** 
 *  Implementation of AVL Tree 
 *  @author Varun Muthanna(vkm150030), Prasanth Kesava Pillai(pxk163630)
 *	& Shivan Pandya(srp150330)
 **/
package cs6301.g60;

import java.util.Scanner;

public class AVLTree<T extends Comparable<? super T>> extends BST<T> {
	//Entry<T> AVLroot;
    static class AVLEntry<T> extends BST.Entry<T> {
        int height;
        AVLEntry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            height = 0;
        }
    }

    AVLTree() {
    	super();
    }
    
    /**
     * Override the BST add
     */
    @Override
    public boolean add(T x) {
    	
    	if(super.add(new AVLEntry<>(x, null, null)) == false){
    		return false;
    	}
    	if(this.size == 1){
    		return true;
    	}
    	
    	balanceTree();
    	return true;
    }
    
    /**
     * Override the BST remove
     */
    @Override
    public T remove(T x){
    	T ans = super.remove(x);
    	if(ans == null){
    		return null;
    	}
    	
    	balanceTree();
    	return ans;
    }
    
    
  //helper function
    
    /**
     * Every ancestor node of the node newly added is checked for
     * AVL structural property and is balanced. The ancestors are
     * found using the stack used in the add of BST
     */
    private void balanceTree(){
    	AVLEntry<T> parent = null;
    	AVLEntry<T> e = null;
    	if(this.stack.size() > 0 && this.stack.peek().element != null){
    		e = (AVLEntry<T>) this.stack.pop();
    	}else{
    		return;
    	}
    	

    	while(!this.stack.isEmpty() && this.stack.peek().element != null){
    		parent = (AVLEntry<T>) this.stack.pop();
    		updateHeightAndBalance(e, parent);
    		e = parent;
    		parent = null;
    	}
    	updateHeightAndBalance(e, parent);
    }
    
    /**
     * Updates the height and balances if the AVL structure is not 
     * satisfied
     * @param e
     * @param parent
     */
    private void updateHeightAndBalance(AVLEntry<T> e, AVLEntry<T> parent ){
    	updateNodeHeight(e);
		
		int heightDiff = getHeightDiff(e);
		Entry<T> newNode = e;
		if(heightDiff > 1 && getHeightDiff(e.left) >= 0){
			newNode = rightRotate(e);
		}
		
		if(heightDiff > 1 && getHeightDiff(e.left) < 0){
			e.left = rightRotate(e.left);
			newNode = leftRotate(e);
		}
		
		if(heightDiff < -1 && getHeightDiff(e.right) <= 0 ){
			newNode = leftRotate(e);
		}
		
		if(heightDiff < -1 && getHeightDiff(e.right) > 0 ){
			e.right = leftRotate(e.right);
			newNode = rightRotate(e);
		}
		
		if(parent == null){
			this.root = newNode;
			return;
		}
		
		if(parent.element.compareTo(newNode.element) > 0){
			parent.left = newNode;
		}else{
			parent.right = newNode;
		}
    }
    
    /**
     * given the height of left and right nodes update the node height
     * @param node
     */
    private void updateNodeHeight(Entry<T> node){
    	if(node == null || (node.left == null && node.right == null)){
    		((AVLEntry<T>)node).height = 0;
    	}else{
    		((AVLEntry<T>)node).height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    	}
    }

    /**
     * Get  height of node in the tree
     * @param x
     * @return
     */
    private int getHeight(Entry<T> x){
    	if(x == null){
    		return 0;
    	}else{
    		return ((AVLEntry<T>)x).height;
    	}
    }
    
    /**
     * Get difference in height of left and right subtree
     * @param x
     * @return
     */
    private int getHeightDiff(Entry<T> x){
    	if(x == null || (x.left == null && x.right == null)){
    		return 0;
    	}else{
    		if(x.left == null){
    			return -(getHeight(x.right)+1);
    		}else if(x.right == null){
    			return getHeight(x.left) + 1;
    		}else{
    			return getHeight(x.left) - getHeight(x.right);
    		}
    	}
    }
    
    /**
     * Rotate left and simultaneously update the height
     * of the new tree.
     * @param node
     * @return root of new tree
     */
    private Entry<T> leftRotate(Entry<T> node){
    	node = Balance.singleLeftRotate(node);
    	updateNodeHeight(node.left);
    	updateNodeHeight(node);
    	return node;
    }
    
    /**
     * Rotate right and simultaneously update the height
     * of the new tree.
     * @param node
     * @return root of new tree
     */
    private Entry<T> rightRotate(Entry<T> node){
    	node = Balance.singleRightRotate(node);
    	updateNodeHeight(node.right);
    	updateNodeHeight(node);
    	return node;
    }
    
    /**
     * Function to validate the AVL tree. 
     * checks if it is a BST, AVL structure
     * @param  : root
     * @return : true or false
     */
    private boolean isAVLTreeValid(){
    	Entry<T> tree = this.root;
    	return validateAVLTree(tree) >= 0 ? true : false;
    }
    
    /**
     * Function to validate the AVL tree. 
     * checks if it is a BST, AVL structure
     * @param root
     * @return height of the tree
     */
    private int validateAVLTree(Entry<T> root){
    	if(root == null){
    		return 0;
    	}
    	if (Math.abs(getHeightDiff(root)) > 1){
    		return -1;
    	}
    	if(root.left == null && root.right == null){
    		return 0;
    	}
    	int left = 0;
    	int right = 0;
    	if(root.left != null){
    		if (root.element.compareTo(root.left.element) < 0){
    			return -1;
    		}
    		left = validateAVLTree(root.left);
    	}
    	if(root.right != null){
    		if(root.element.compareTo(root.right.element) > 0){
    			return -1;
    		}
    		right = validateAVLTree(root.right);
    	}
    	if(left == -1 || right == -1){
    		return -1;
    	}
    	int height = 1 + Math.max(left,right);
    	if(height != ((AVLEntry<T>)root).height){
    		return -1;
    	}else{
    	  return height;	
    	}
    }
    
    public static void main(String[] args) {
    	AVLTree<Integer> t = new AVLTree<>();
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

                for(Integer node: t){
                    System.out.println(node);
                }
                System.out.println("valid tree: " + t.isAVLTreeValid());
                

                System.out.println();
                in.close();
                return;
            }
        }
        in.close();
        
    }
}

