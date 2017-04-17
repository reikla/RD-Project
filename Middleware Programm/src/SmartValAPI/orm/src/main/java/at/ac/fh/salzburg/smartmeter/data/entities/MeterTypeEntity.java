package at.ac.fh.salzburg.smartmeter.data.entities;

import javax.persistence.*;

@Entity
@Table(name = "meter_type", schema = "smart_meter", catalog = "")
public class MeterTypeEntity {
    private int typeId;
    private String description;

    @Id
    @Column(name = "type_id")
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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

        MeterTypeEntity that = (MeterTypeEntity) o;

        if (typeId != that.typeId) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = typeId;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
