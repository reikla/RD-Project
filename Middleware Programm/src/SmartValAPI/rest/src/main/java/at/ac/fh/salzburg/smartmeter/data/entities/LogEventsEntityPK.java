package at.ac.fh.salzburg.smartmeter.data.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
@Embeddable
public class LogEventsEntityPK implements Serializable {
    private int logId;
    private int idType;

    @Column(name = "log_id")
    @Id
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    @Column(name = "id_type")
    @Id
    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogEventsEntityPK that = (LogEventsEntityPK) o;

        if (logId != that.logId) return false;
        if (idType != that.idType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = logId;
        result = 31 * result + idType;
        return result;
    }
}
