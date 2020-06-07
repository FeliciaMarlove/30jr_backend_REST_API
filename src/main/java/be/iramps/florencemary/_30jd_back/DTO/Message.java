package be.iramps.florencemary._30jd_back.DTO;

/**
 * DTO GET Message
 */
public class Message implements DTOEntity {
    private String msg;
    private boolean aBoolean;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    /**
     * Constructeur de message
     * @param msg String le message
     */
    public Message(String msg) {
        this.msg = msg;
    }

    /**
     * Constructeur de message
     * @param msg String le message
     * @param aBoolean boolean un booléen
     */
    public Message(String msg, boolean aBoolean) {
        this(msg);
        this.aBoolean = aBoolean;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
