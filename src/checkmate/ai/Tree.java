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
    int currentDepth = 0;
    int id = 1;
    Node root = new Node();
    Random rand = new Random();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Tree tree = new Tree();
        System.out.println("Enter depth: ");
        tree.depth = scan.nextInt();
        tree.createNodes(tree.root);
        tree.currentDepth = 0;
        tree.displayNodes(tree.root);
    }

    public void createNodes(Node parent) {
        parent.id = id++;
        while (depth < parent.depth) {
            currentDepth++;
            parent.child1 = new Node();
            parent.child1.parent = parent;
            parent.child1.depth = parent.depth+1;
            createNodes(parent.child1);
            parent.child2 = new Node();
            parent.child2.parent = parent;
            parent.child2.depth = parent.depth+1;
            createNodes(parent.child2);
        }
        if (depth == parent.depth) {
            parent.value = rand.nextInt();
        }
    }

    public void displayNodes(Node parent) {
        System.out.println("Value of node = " + parent.value + ", id = " + parent.id);
        currentDepth++;
        while (depth < parent.depth) {
            displayNodes(parent.child1);
            displayNodes(parent.child2);
        }
    }
}
