package at.ac.fh.salzburg.smartmeter.data.entities;

import javax.persistence.*;

@Entity
@Table(name = "meter_manufactor", schema = "smart_meter", catalog = "")
public class MeterManufactorEntity {
    private int manufactorId;
    private String description;

    @Id
    @Column(name = "manufactor_id")
    public int getManufactorId() {
        return manufactorId;
    }

    public void setManufactorId(int manufactorId) {
        this.manufactorId = manufactorId;
    }

    @Basic
    @Column(name = "description")
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
