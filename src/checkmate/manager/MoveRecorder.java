/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.manager;

import checkmate.Launcher;
import java.util.ArrayList;

/**
 *
 * @author Isaac
 */
public class MoveRecorder {

    private final ArrayList<ArrayList<Movement>> disc;

    public MoveRecorder() {
        this.disc = new ArrayList<>(Launcher.resource.getIntConfig("Recording.max"));
    }

    public void addToDisc(ArrayList<Movement> data) {
        disc.add(data);
    }

}
