package com.trilogyed.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "track")
public class Track implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id")
    private long id;
    @Column(name = "album_id")
    private long albumId;
    private String title;
    @Column(name = "run_time")
    private long runTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getRunTime() {
        return runTime;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return getId() == track.getId() && getAlbumId() == track.getAlbumId() && getRunTime() == track.getRunTime() && Objects.equals(getTitle(), track.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAlbumId(), getTitle(), getRunTime());
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", albumId=" + albumId +
                ", title='" + title + '\'' +
                ", runTime=" + runTime +
                '}';
    }
}
