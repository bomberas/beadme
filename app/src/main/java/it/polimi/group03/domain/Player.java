package it.polimi.group03.domain;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by tatibloom on 08/11/2015.
 */
public class Player {

    String name;
    Scanner s=new Scanner(System.in);
    ArrayList<String> players=new ArrayList<String>(); //An arraylist to keep track of the player's names.



    public void addPlayers()
    {
        name= s.next();
        players.add(name);

    }

    public void chooseBeads ()
    {

    }
}