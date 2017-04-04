package at.ac.fh.salzburg;

/**
 * Created by reimarklammer on 28.03.17.
 */
public class MeterManufactorEntity {
    private int manufactorId;
    private String description;

    public int getManufactorId() {
        return manufactorId;
    }

    public void setManufactorId(int manufactorId) {
        this.manufactorId = manufactorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeterManufactorEntity that = (MeterManufactorEntity) o;

        if (manufactorId != that.manufactorId) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = manufactorId;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
