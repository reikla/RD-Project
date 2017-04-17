package at.ac.fh.salzburg.smartmeter.data.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class MeterManagementEntityPK implements Serializable {
    private int idMeter;
    private int idType;
    private int idManufactor;
    private int idProtocol;
    private int idCustomer;

    @Column(name = "id_meter")
    @Id
    public int getIdMeter() {
        return idMeter;
    }

    public void setIdMeter(int idMeter) {
        this.idMeter = idMeter;
    }

    @Column(name = "id_type")
    @Id
    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    @Column(name = "id_manufactor")
    @Id
    public int getIdManufactor() {
        return idManufactor;
    }

    public void setIdManufactor(int idManufactor) {
        this.idManufactor = idManufactor;
    }

    @Column(name = "id_protocol")
    @Id
    public int getIdProtocol() {
        return idProtocol;
    }

    public void setIdProtocol(int idProtocol) {
        this.idProtocol = idProtocol;
    }

    @Column(name = "id_customer")
    @Id
    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeterManagementEntityPK that = (MeterManagementEntityPK) o;

        if (idMeter != that.idMeter) return false;
        if (idType != that.idType) return false;
        if (idManufactor != that.idManufactor) return false;
        if (idProtocol != that.idProtocol) return false;
        if (idCustomer != that.idCustomer) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMeter;
        result = 31 * result + idType;
        result = 31 * result + idManufactor;
        result = 31 * result + idProtocol;
        result = 31 * result + idCustomer;
        return result;
    }
}
