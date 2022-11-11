package com.trilogyed.musicstorecatalog.viewModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class TrackViewModel {

    private long id;
    @NotNull(message = "Track album id is required")
    private long albumId;
    @NotEmpty(message = "Track title is required")
    private String title;
    @NotNull(message = "Track runtime is required")
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
        TrackViewModel that = (TrackViewModel) o;
        return getId() == that.getId() && getAlbumId() == that.getAlbumId() && getRunTime() == that.getRunTime() && Objects.equals(getTitle(), that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAlbumId(), getTitle(), getRunTime());
    }

    @Override
    public String toString() {
        return "TrackViewModel{" +
                "id=" + id +
                ", albumId=" + albumId +
                ", title='" + title + '\'' +
                ", runTime=" + runTime +
                '}';
    }
}
