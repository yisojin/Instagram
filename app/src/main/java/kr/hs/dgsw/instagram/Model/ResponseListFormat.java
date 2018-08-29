package kr.hs.dgsw.instagram.Model;

import java.util.List;

public class ResponseListFormat {

    private int status;
    private String message;
    private List<BoardModel> data;

    @Override
    public String toString() {
        return "ResponseListFormat{" +
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

    public List<BoardModel> getData() {
        return data;
    }

    public void setData(List<BoardModel> data) {
        this.data = data;
    }
}
