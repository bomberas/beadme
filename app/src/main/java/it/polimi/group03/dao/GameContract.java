package it.polimi.group03.dao;

/**
 * Created by megireci on 24-Dec-15.
 */

/*t
This is the contracter class where all the column names are saved. This will interact with SQLiteStatisticRepository
 */
public class GameContract {

    public static abstract class playerInfo
    {
        //These will be the columns of the table
        public static final String PLAYER_NICKNAME="Nickname";
        public static final String PLAYER_BEAD_COLOR="BeadColor";
        public static final String PLAYER_ID="id";
        public static final String PLAYER_VICTORY="victory";
        public static final String PLAYER_DEFEAT="defeat";
        public static final String TABLENAME="PlayerInfo";


    }
}
