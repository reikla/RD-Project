package at.ac.fh.salzburg;

/**
 * Created by reimarklammer on 28.03.17.
 */
public class LogTypesEntity {
    private int typesId;
    private String typesDescription;

    public int getTypesId() {
        return typesId;
    }

    public void setTypesId(int typesId) {
        this.typesId = typesId;
    }

    public String getTypesDescription() {
        return typesDescription;
    }

    public void setTypesDescription(String typesDescription) {
        this.typesDescription = typesDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogTypesEntity that = (LogTypesEntity) o;

        if (typesId != that.typesId) return false;
        if (typesDescription != null ? !typesDescription.equals(that.typesDescription) : that.typesDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = typesId;
        result = 31 * result + (typesDescription != null ? typesDescription.hashCode() : 0);
        return result;
    }
}
