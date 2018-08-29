package kr.hs.dgsw.instagram.Model;

public class ResponseFormat {

    private int status;
    private String message;
    private Object data;


    @Override
    public String toString() {
        return "ResponseFormat{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(UserModel data) {
        this.data = data;
    }
}
