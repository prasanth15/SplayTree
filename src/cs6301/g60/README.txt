CS 6301.502. Implementation of advanced data structures and algorithms
Fall 2017
Short Project 7: Binary search trees
Group: g 60
Group Members: Varun Muthanna(vkm150030), Prasanth Kesava Pillai(pxk163630)
& Shivan Pandya(srp150330)

Question 1:
BST:
Class file corresponding to this problem is BST.java. All the functions are included in this file.

Question 2:
AVL Tree: It is implemented in AVLTree.java.
It has overridden the add and remove methods of the BST and all other
methods are used from BST class. In the add and remove the first pass of adding is done using add of BST which
is the single pass algorithm. And Using the stack the height is updated and the tree is balanced in single pass from bottom to top. 

Question 3:
RedBlack Trees:
Class file corresponding to this problem is RedBlackTree.java and it uses Balance.java for rotation.
There are two functions add and remove implemented in this class.

Question 4:
Splay Trees:
Class file corresponding to this problem is SplayTree.java and it uses Balance.java for rotation.
There are three operations: add, remove and contains implemented in this class.
In addition, there is a SplayDriver.java which generates random add/remove/contains on the SplayTree.java.


Question 5:
BSTMap: It is implemented in BSTMap.java.
TreeMap called BST Map is implemented using AVL tree implemented for Question 2. A class called
Entry which holds the key and value is used and stored in the tree and it has a comparable which compares
The values of the two objects based on the value of the key.


For all the questions the rotation methods are implemented as static methods in separate a class Balance (Balance.java) so it could be
Used by all the different algorithms.


References:
http://mypages.valdosta.edu/dgibson/courses/cs3410/notes/ch19_5.pdf
http://www.eternallyconfuzzled.com/tuts/datastructures/jsw_tut_rbtree.aspx
https://www.cs.usfca.edu/~galles/visualization/SplayTree.html
http://lcm.csa.iisc.ernet.in/dsa/node94.html
