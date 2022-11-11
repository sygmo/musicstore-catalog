package com.trilogyed.musicstorecatalog.viewModel;

import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.model.Label;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class AlbumViewModel {

    private long id;
    @NotEmpty(message = "Album title is required")
    private String title;
    @NotNull(message = "Album artist id is required")
    private long artistId;
    @NotNull(message = "Album release date is required")
    private LocalDate releaseDate;
    @NotNull(message = "Album label id is required")
    private long labelId;
    @NotNull(message = "Album list price is required")
    @DecimalMin(value = "1.00", inclusive = true, message = "Price for this album is lower than $1.00")
    @DecimalMax(value = "999.99", inclusive = true, message = "Price for this album exceeds $999.99")
    private BigDecimal listPrice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public long getLabelId() {
        return labelId;
    }

    public void setLabelId(long labelId) {
        this.labelId = labelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumViewModel that = (AlbumViewModel) o;
        return getId() == that.getId() && getArtistId() == that.getArtistId() && getLabelId() == that.getLabelId() && Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getReleaseDate(), that.getReleaseDate()) && Objects.equals(getListPrice(), that.getListPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getArtistId(), getReleaseDate(), getLabelId(), getListPrice());
    }

    @Override
    public String toString() {
        return "AlbumViewModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artistId=" + artistId +
                ", releaseDate=" + releaseDate +
                ", labelId=" + labelId +
                ", listPrice=" + listPrice +
                '}';
    }
}
