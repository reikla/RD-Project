package at.ac.fh.salzburg;

import java.sql.Timestamp;

/**
 * Created by reimarklammer on 28.03.17.
 */
public class LastEventsEntity {
    private int logId;
    private int idType;
    private String content;
    private String sourceTarget;
    private Timestamp timestamp;

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSourceTarget() {
        return sourceTarget;
    }

    public void setSourceTarget(String sourceTarget) {
        this.sourceTarget = sourceTarget;
    }

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
