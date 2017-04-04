package at.ac.fh.salzburg;

import java.io.Serializable;

/**
 * Created by reimarklammer on 28.03.17.
 */
public class MeterManagementEntityPK implements Serializable {
    private int idMeter;
    private int idType;
    private int idManufactor;
    private int idProtocol;
    private int idCustomer;

    public int getIdMeter() {
        return idMeter;
    }

    public void setIdMeter(int idMeter) {
        this.idMeter = idMeter;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getIdManufactor() {
        return idManufactor;
    }

    public void setIdManufactor(int idManufactor) {
        this.idManufactor = idManufactor;
    }

    public int getIdProtocol() {
        return idProtocol;
    }

    public void setIdProtocol(int idProtocol) {
        this.idProtocol = idProtocol;
    }

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
