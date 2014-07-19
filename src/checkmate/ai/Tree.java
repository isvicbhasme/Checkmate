/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.ai;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author bhasme
 */
public class Tree {

    int depth = 0;
    int id = 1;
    Node root;
    Random rand;

    public Tree() {
        root = new Node();
        root.depth = 0;
        rand = new Random();
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Tree tree = new Tree();
        System.out.println("Enter depth: ");
        tree.depth = scan.nextInt();
        tree.createNodes(tree.root);
        tree.displayNodes(tree.root);
        tree.computeScores(tree.root);
        System.out.println("New Scores!");
        tree.displayNodes(tree.root);
    }

    public void createNodes(Node parent) {
        parent.id = id++;
        if (depth > parent.depth) {
            parent.child1 = new Node();
            parent.child1.parent = parent;
            parent.child1.depth = parent.depth + 1;
            createNodes(parent.child1);
            parent.child2 = new Node();
            parent.child2.parent = parent;
            parent.child2.depth = parent.depth + 1;
            createNodes(parent.child2);
        }

    }

    public void displayNodes(Node parent) {
        String parentId = (parent.parent != null) ? "" + parent.parent.id : "root";
        System.out.format("Node, value = %1$-12.12s, depth = %2$-12.12s, id = %3$-12.12s, parentId = %4$-12.12s\n", parent.value, parent.depth, parent.id, parentId);
        if (depth > parent.depth) {
            displayNodes(parent.child1);
            displayNodes(parent.child2);
        }
    }
    
    public void computeScores(Node node) {
        if (depth == node.depth) {
            node.value = rand.nextInt(50);
        } else if (node.depth % 2 == 0) {
            node.value = Integer.MIN_VALUE;
        } else {
            node.value = Integer.MAX_VALUE;
        }
        if(depth > node.depth) {
            computeScores(node.child1);
            computeScores(node.child2);
        }
        if(node.parent!=null) {
            if(node.parent.depth % 2 == 0) {
                node.parent.value = Math.max(node.parent.value, node.value);
            } else {
                node.parent.value = Math.min(node.parent.value, node.value);
            }
        }
    }
}
