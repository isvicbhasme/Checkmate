/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.ai;

import java.util.ArrayList;

/**
 *
 * @author bhasme
 */
public class Node {

    protected Node() {
    }
    
    Node parent = null;
    ArrayList<Node> children = new ArrayList<>();
    int value = 0;
    int id = 0;
    int depth = 0;
}
