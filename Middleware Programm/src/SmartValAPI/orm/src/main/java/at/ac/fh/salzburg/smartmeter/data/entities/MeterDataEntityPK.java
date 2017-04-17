package at.ac.fh.salzburg.smartmeter.data.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class MeterDataEntityPK implements Serializable {
    private int dataId;
    private int meterId;

    @Column(name = "data_id")
    @Id
    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    @Column(name = "meter_id")
    @Id
    public int getMeterId() {
        return meterId;
    }

    public void setMeterId(int meterId) {
        this.meterId = meterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeterDataEntityPK that = (MeterDataEntityPK) o;

        if (dataId != that.dataId) return false;
        if (meterId != that.meterId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dataId;
        result = 31 * result + meterId;
        return result;
    }
}
