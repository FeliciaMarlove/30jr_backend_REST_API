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

    public Message(String msg) {
        this.msg = msg;
    }

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
