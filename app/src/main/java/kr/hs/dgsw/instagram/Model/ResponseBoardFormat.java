package kr.hs.dgsw.instagram.Model;

public class ResponseBoardFormat {

    private int status;
    private String message;
    private BoardModel data;

    @Override
    public String toString() {
        return "ResponseBoardFormat{" +
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

    public BoardModel getData() {
        return data;
    }

    public void setData(BoardModel data) {
        this.data = data;
    }
}
