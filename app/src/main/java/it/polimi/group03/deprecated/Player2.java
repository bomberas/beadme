package it.polimi.group03.deprecated;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by megi on 11/11/2015.
 */
public class Player2 {

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
