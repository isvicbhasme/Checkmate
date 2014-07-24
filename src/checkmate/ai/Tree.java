/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.ai;

import checkmate.design.Piece;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author bhasme
 */
public class Tree implements IAi {

    int depth = 0;
    int id = 1;
    Node root;
    Random rand;

    protected Tree() {
        root = new Node();
        root.depth = 0;
        rand = new Random();
    }

    protected static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Tree tree = new Tree();
        //System.out.println("Enter depth: ");
        tree.depth = 5;//scan.nextInt();
        tree.createNodes(tree.root);
        tree.displayNodes(tree.root);
        tree.computeScores(tree.root);
        System.out.println("New Scores!");
        tree.displayNodes(tree.root);
    }

    protected void createNodes(Node parent) {
        parent.id = id++;
        if (depth > parent.depth) {
            for (checkmate.design.Piece piece : getPieces()) {
                for (Node child : getPossibleMoves()) {
                    Node node = new Node();
                    node.parent = parent;
                    node.depth = parent.depth + 1;
                    parent.children.add(node);
                    createNodes(node);
                }
            }
        }

    }

    protected void displayNodes(Node parent) {
        String parentId = (parent.parent != null) ? "" + parent.parent.id : "root";
        System.out.format("Node, value = %1$-12.12s, depth = %2$-12.12s, id = %3$-12.12s, parentId = %4$-12.12s\n", parent.value, parent.depth, parent.id, parentId);
        if (depth > parent.depth) {
            for (Node child : parent.children) {
                displayNodes(child);
            }
        }
    }

    protected void computeScores(Node node) {
        if (depth == node.depth) {
            node.value = rand.nextInt(50);
        } else if (node.depth % 2 == 0) {
            node.value = Integer.MIN_VALUE;
        } else {
            node.value = Integer.MAX_VALUE;
        }
        if (depth > node.depth) {
            for (Node child : node.children) {
                computeScores(child);
            }
        }
        if (node.parent != null) {
            if (node.parent.depth % 2 == 0) {
                node.parent.value = Math.max(node.parent.value, node.value);
            } else {
                node.parent.value = Math.min(node.parent.value, node.value);
            }
        }
    }

    @Override
    public void generateMove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Iterable<Piece> getPieces() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Iterable<Node> getPossibleMoves() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
