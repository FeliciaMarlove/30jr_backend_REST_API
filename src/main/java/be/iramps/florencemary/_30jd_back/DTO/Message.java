package be.iramps.florencemary._30jd_back.DTO;

public class Message implements DTOEntity {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Message(String msg) {
        this.msg = msg;
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
