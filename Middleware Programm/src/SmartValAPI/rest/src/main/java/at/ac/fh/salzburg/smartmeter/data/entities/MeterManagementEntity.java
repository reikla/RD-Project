package at.ac.fh.salzburg.smartmeter.data.entities;

import javax.persistence.*;

@Entity
@Table(name = "meter_management", schema = "smart_meter", catalog = "")
@IdClass(MeterManagementEntityPK.class)
public class MeterManagementEntity {
    private int idMeter;
    private String description;
    private String serial;
    private String key;
    private int idType;
    private int idManufactor;
    private int idProtocol;
    private int idCustomer;
    private Integer period;
    private Byte active;
    private String port;

    @Id
    @Column(name = "id_meter")
    public int getIdMeter() {
        return idMeter;
    }

    public void setIdMeter(int idMeter) {
        this.idMeter = idMeter;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "serial")
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @Basic
    @Column(name = "key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Id
    @Column(name = "id_type")
    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    @Id
    @Column(name = "id_manufactor")
    public int getIdManufactor() {
        return idManufactor;
    }

    public void setIdManufactor(int idManufactor) {
        this.idManufactor = idManufactor;
    }

    @Id
    @Column(name = "id_protocol")
    public int getIdProtocol() {
        return idProtocol;
    }

    public void setIdProtocol(int idProtocol) {
        this.idProtocol = idProtocol;
    }

    @Id
    @Column(name = "id_customer")
    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    @Basic
    @Column(name = "period")
    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    @Basic
    @Column(name = "active")
    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    @Basic
    @Column(name = "port")
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeterManagementEntity that = (MeterManagementEntity) o;

        if (idMeter != that.idMeter) return false;
        if (idType != that.idType) return false;
        if (idManufactor != that.idManufactor) return false;
        if (idProtocol != that.idProtocol) return false;
        if (idCustomer != that.idCustomer) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (serial != null ? !serial.equals(that.serial) : that.serial != null) return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (period != null ? !period.equals(that.period) : that.period != null) return false;
        if (active != null ? !active.equals(that.active) : that.active != null) return false;
        if (port != null ? !port.equals(that.port) : that.port != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMeter;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (serial != null ? serial.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + idType;
        result = 31 * result + idManufactor;
        result = 31 * result + idProtocol;
        result = 31 * result + idCustomer;
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        return result;
    }
}
