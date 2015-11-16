package it.polimi.group03.domain;

import java.util.ArrayList;
import java.util.List;

import it.polimi.group03.util.CommonUtil;

/**
 * Created by tatibloom on 08/11/2015.
 */
public class Player {

    private String nickname;
    private List<Bead> beads;
    private boolean active;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Bead> getBeads() {
        return beads;
    }

    public void setBeads(List<Bead> beads) {
        this.beads = beads;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void addBead(Bead bead) {
        if ( CommonUtil.isEmpty(beads) ) {
            beads = new ArrayList<Bead>();
        }

        beads.add(bead);
    }

    public List<Bead> activeBeads() {
        List<Bead> beads = new ArrayList<Bead>();

        for ( Bead bead : this.beads ) {
            if ( bead.isActive() ) {
                beads.add(bead);
            }
        }

        return beads;
    }
}