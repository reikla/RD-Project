package at.ac.fh.salzburg.smartmeter.data.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "last_events", schema = "smart_meter", catalog = "")
@IdClass(LogEventsEntityPK.class)
public class LastEventsEntity {
    private int logId;
    private int idType;
    private String content;
    private String sourceTarget;
    private Timestamp timestamp;

    @Id
    @Column(name = "log_id")
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    @Id
    @Column(name = "id_type")
    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "source_target")
    public String getSourceTarget() {
        return sourceTarget;
    }

    public void setSourceTarget(String sourceTarget) {
        this.sourceTarget = sourceTarget;
    }

    @Basic
    @Column(name = "timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LastEventsEntity that = (LastEventsEntity) o;

        if (logId != that.logId) return false;
        if (idType != that.idType) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (sourceTarget != null ? !sourceTarget.equals(that.sourceTarget) : that.sourceTarget != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = logId;
        result = 31 * result + idType;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (sourceTarget != null ? sourceTarget.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
