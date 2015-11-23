package it.polimi.group03.domain;

/**
 *
 * This class is used to hold more specific information about an action perform in the game. It contains
 * a <tt>code</tt> that identifies the result of the action e.g. <i>0x0</i> indicates success, and a <tt>message</tt>
 * that describes the result e.g <i>"Action performed successfully"</i>.
 *
 * <p>This class is used for retrieving information after a validation of one or more rules in the game.
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 22/11/2015.
 */

public class StatusMessage {

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "\n[" + code + ": " + message + "]";
    }
}
