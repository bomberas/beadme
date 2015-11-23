package it.polimi.group03.domain;

import java.util.ArrayList;
import java.util.List;

import it.polimi.group03.util.CommonUtil;

/**
 *
 *
 *
 *
 * @see Bead
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 08/11/2015.
 */

public class Player {

    private int id;
    private String nickname;
    private String color;
    private List<Bead> beads;
    private boolean active;

    public Player(int id, String nickname, String color) {
        this.id = id;
        this.nickname = nickname;
        this.color = color;
        this.beads = new ArrayList<>();
        this.active = true;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public List<Bead> getBeads() {
        return beads;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void addBead(Bead bead) {
        if ( CommonUtil.isEmpty(beads) ) {
            beads = new ArrayList<>();
        }

        beads.add(bead);
    }

    public String getColor() {
        return color;
    }

    public List<Bead> activeBeads() {
        List<Bead> beads = new ArrayList<>();

        for ( Bead bead : this.beads ) {
            if ( bead.isActive() ) {
                beads.add(bead);
            }
        }

        return beads;
    }
}