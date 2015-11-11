package it.polimi.group03.domain;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Megi on 11/11/2015.
 */
public class Statistics {

    ArrayList<Integer> points;
    ArrayList<Integer> victories = new ArrayList<Integer>();
    ArrayList<Integer> losses = new ArrayList<Integer>();
    ArrayList<Integer> gameCount = new ArrayList<Integer>();

    public void addPoints(int p) {
        /*if ( Collections ) {

        }*/
        points.add(p);
    }

    public int sumPoints() {
        int sum = 0;

        for ( int i = 0; i < points.size(); i++ ) {
            sum += points.get(i);
        }

        return sum;
    }

    public void addVictories (int v) {
        victories.add(v);
    }

    public void addLosses (int l) {
        losses.add(l);
    }

    public void addGameCount(int gc) {
        gameCount.add(gc);
    }

    public int sumVictories() {
        int sum = 0;
        for ( int i = 0; i < victories.size(); i++ ) {
            sum += victories.get(i);
        }

        return sum;
    }

    public int sumLosses() {
        int sum = 0;
        for ( int i = 0; i < losses.size(); i++ ) {
            sum += losses.get(i);
        }

        return sum;
    }

    public int sumGameCount() {
        int sum = 0;
        for ( int i = 0; i < gameCount.size(); i++ ) {
            sum += gameCount.get(i);
        }

        return sum;
    }

}