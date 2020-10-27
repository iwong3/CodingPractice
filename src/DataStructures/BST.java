package DataStructures;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BST {

    /**
     * Node class
     */
    static class Node {

        int value;
        Node left;
        Node right;

        Node(int x) {
            this.value = x;
        }

        public void setValue(int x) {
            this.value = x;
        }

        public void setLeft(Node node) {
            this.left = node;
        }

        public void setRight(Node node) {
            this.right = node;
        }

    }

    private Node root;
    private int height;
    private int num_nodes;

    public BST(int root_val) {

        Node node = new Node(root_val);
        this.root = node;
        this.height = 1;
        this.num_nodes = 1;

    }

    public Node getRoot() {
        return this.root;
    }

    /**
     * Add a node to the BST. Duplicates are stored in the left child.
     * @param x - value of node
     */
    public void addNode(int x) {

        Node node = new Node(x);
        Node curr = root;
        boolean done = false;

        while (!done) {
            if (curr.value >= x) {
                if (curr.left == null) {
                    curr.left = node;
                    done = true;
                } else {
                    curr = curr.left;
                }
            } else if (curr.value < x) {
                if (curr.right == null) {
                    curr.right = node;
                    done = true;
                } else {
                    curr = curr.right;
                }
            }
        }

    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        
        Node self_curr;
        Node other_curr;
        Queue<Node> to_check = new LinkedList<>();
        to_check.add(this.getRoot());
        to_check.add(((BST) obj).getRoot());

        while (to_check.size() > 0) {

            self_curr = to_check.poll();
            other_curr = to_check.poll();

            // different values
            if (self_curr.value != other_curr.value) return false;

            // both have left
            if (self_curr.left != null && other_curr.left != null) {
                to_check.add(self_curr.left);
                to_check.add(other_curr.left);
            }
            // left is different
            else if (self_curr.left != null || other_curr.left != null) {
                return false;
            }

            // both have right
            if (self_curr.right != null && other_curr.right != null) {
                to_check.add(self_curr.right);
                to_check.add(other_curr.right);
            }
            // right is different
            else if (self_curr.right != null || other_curr.right != null) {
                return false;
            }

        }

        return true;
        
    }

}

@Nested
@DisplayName("BST spec")
class BSTSpec {

    @Test
    void addNodeHappy() {
        BST bst = new BST(5);
        bst.addNode(2);
        bst.addNode(8);

        BST expected_ans = new BST(5);
        expected_ans.getRoot().setLeft(new BST.Node(2));
        expected_ans.getRoot().setRight(new BST.Node(8));

        assertEquals(expected_ans, bst);
    }

    @Test
    void addNodeLeft() {
        BST bst = new BST(5);
        bst.addNode(4);
        bst.addNode(3);

        BST expected_ans = new BST(5);
        expected_ans.getRoot().setLeft(new BST.Node(4));
        expected_ans.getRoot().left.setLeft(new BST.Node(3));

        assertEquals(expected_ans, bst);
    }

    @Test
    void addNodeRight() {
        BST bst = new BST(5);
        bst.addNode(6);
        bst.addNode(7);

        BST expected_ans = new BST(5);
        expected_ans.getRoot().setRight(new BST.Node(6));
        expected_ans.getRoot().right.setRight(new BST.Node(7));

        assertEquals(expected_ans, bst);
    }

}