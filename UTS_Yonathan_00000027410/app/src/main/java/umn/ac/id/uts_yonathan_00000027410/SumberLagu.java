package umn.ac.id.uts_yonathan_00000027410;

import java.io.Serializable;

public class SumberLagu implements Serializable {
    String aPath;
    String aName;
    String aAlbum;
    String aArtist;
    int pos;

    public String getaPath() {
        return aPath;
    }

    public void setaPath(String aPath) {
        this.aPath = aPath;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getaAlbum() {
        return aAlbum;
    }

    public void setaAlbum(String aAlbum) {
        this.aAlbum = aAlbum;
    }

    public String getaArtist() {
        return aArtist;
    }

    public void setaArtist(String aArtist) {
        this.aArtist = aArtist;
    }

    public int getPos() { return pos;}

    public void setPos(int pos) {
        this.pos = pos;
    }
}
