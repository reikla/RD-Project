package at.ac.fh.salzburg.smartmeter.data.entities;

import javax.persistence.*;

@Entity
@Table(name = "meter_protocol", schema = "smart_meter", catalog = "")
public class MeterProtocolEntity {
    private int protocolId;
    private String protocol;
    private String dataScheme;

    @Id
    @Column(name = "protocol_id")
    public int getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(int protocolId) {
        this.protocolId = protocolId;
    }

    @Basic
    @Column(name = "protocol")
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Basic
    @Column(name = "data_scheme")
    public String getDataScheme() {
        return dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeterProtocolEntity that = (MeterProtocolEntity) o;

        if (protocolId != that.protocolId) return false;
        if (protocol != null ? !protocol.equals(that.protocol) : that.protocol != null) return false;
        if (dataScheme != null ? !dataScheme.equals(that.dataScheme) : that.dataScheme != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = protocolId;
        result = 31 * result + (protocol != null ? protocol.hashCode() : 0);
        result = 31 * result + (dataScheme != null ? dataScheme.hashCode() : 0);
        return result;
    }
}
