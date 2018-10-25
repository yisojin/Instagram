package kr.hs.dgsw.instagram.Model;

import okhttp3.MultipartBody;

public class BoardModel {
    private String id;
    private String title;
    private String fileName;
    private String writer;
    private int likes;
    private String likesUser;
    private boolean isLike;

    @Override
    public String toString() {
        return "BoardModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", fileName='" + fileName + '\'' +
                ", writer='" + writer + '\'' +
                ", likes=" + likes +
                ", likesUser='" + likesUser + '\'' +
                ", isLike=" + isLike +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getLikesUser() {
        return likesUser;
    }

    public void setLikesUser(String likesUser) {
        this.likesUser = likesUser;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
