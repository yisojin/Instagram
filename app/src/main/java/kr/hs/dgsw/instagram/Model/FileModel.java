package kr.hs.dgsw.instagram.Model;

public class FileModel {

    private int id;
    private int BoardId;
    private String originalName;
    private String serverName;

    @Override
    public String toString() {
        return "FileModel{" +
                "id=" + id +
                ", BoardId=" + BoardId +
                ", originalName='" + originalName + '\'' +
                ", serverName='" + serverName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoardId() {
        return BoardId;
    }

    public void setBoardId(int boardId) {
        BoardId = boardId;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
