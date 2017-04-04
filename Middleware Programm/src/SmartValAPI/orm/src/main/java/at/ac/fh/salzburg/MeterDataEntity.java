package at.ac.fh.salzburg;

import java.sql.Timestamp;

/**
 * Created by reimarklammer on 28.03.17.
 */
public class MeterDataEntity {
    private int dataId;
    private int meterId;
    private Timestamp timestamp;
    private Double countTotal;
    private Double countRegister1;
    private Double countRegister2;
    private Double countRegister3;
    private Double countRegister4;
    private Double powerP1;
    private Double powerP2;
    private Double powerP3;
    private Double workP1;
    private Double workP2;
    private Double workP3;
    private Double frequency;
    private Double voltage;

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public int getMeterId() {
        return meterId;
    }

    public void setMeterId(int meterId) {
        this.meterId = meterId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Double getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(Double countTotal) {
        this.countTotal = countTotal;
    }

    public Double getCountRegister1() {
        return countRegister1;
    }

    public void setCountRegister1(Double countRegister1) {
        this.countRegister1 = countRegister1;
    }

    public Double getCountRegister2() {
        return countRegister2;
    }

    public void setCountRegister2(Double countRegister2) {
        this.countRegister2 = countRegister2;
    }

    public Double getCountRegister3() {
        return countRegister3;
    }

    public void setCountRegister3(Double countRegister3) {
        this.countRegister3 = countRegister3;
    }

    public Double getCountRegister4() {
        return countRegister4;
    }

    public void setCountRegister4(Double countRegister4) {
        this.countRegister4 = countRegister4;
    }

    public Double getPowerP1() {
        return powerP1;
    }

    public void setPowerP1(Double powerP1) {
        this.powerP1 = powerP1;
    }

    public Double getPowerP2() {
        return powerP2;
    }

    public void setPowerP2(Double powerP2) {
        this.powerP2 = powerP2;
    }

    public Double getPowerP3() {
        return powerP3;
    }

    public void setPowerP3(Double powerP3) {
        this.powerP3 = powerP3;
    }

    public Double getWorkP1() {
        return workP1;
    }

    public void setWorkP1(Double workP1) {
        this.workP1 = workP1;
    }

    public Double getWorkP2() {
        return workP2;
    }

    public void setWorkP2(Double workP2) {
        this.workP2 = workP2;
    }

    public Double getWorkP3() {
        return workP3;
    }

    public void setWorkP3(Double workP3) {
        this.workP3 = workP3;
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeterDataEntity that = (MeterDataEntity) o;

        if (dataId != that.dataId) return false;
        if (meterId != that.meterId) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        if (countTotal != null ? !countTotal.equals(that.countTotal) : that.countTotal != null) return false;
        if (countRegister1 != null ? !countRegister1.equals(that.countRegister1) : that.countRegister1 != null)
            return false;
        if (countRegister2 != null ? !countRegister2.equals(that.countRegister2) : that.countRegister2 != null)
            return false;
        if (countRegister3 != null ? !countRegister3.equals(that.countRegister3) : that.countRegister3 != null)
            return false;
        if (countRegister4 != null ? !countRegister4.equals(that.countRegister4) : that.countRegister4 != null)
            return false;
        if (powerP1 != null ? !powerP1.equals(that.powerP1) : that.powerP1 != null) return false;
        if (powerP2 != null ? !powerP2.equals(that.powerP2) : that.powerP2 != null) return false;
        if (powerP3 != null ? !powerP3.equals(that.powerP3) : that.powerP3 != null) return false;
        if (workP1 != null ? !workP1.equals(that.workP1) : that.workP1 != null) return false;
        if (workP2 != null ? !workP2.equals(that.workP2) : that.workP2 != null) return false;
        if (workP3 != null ? !workP3.equals(that.workP3) : that.workP3 != null) return false;
        if (frequency != null ? !frequency.equals(that.frequency) : that.frequency != null) return false;
        if (voltage != null ? !voltage.equals(that.voltage) : that.voltage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dataId;
        result = 31 * result + meterId;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (countTotal != null ? countTotal.hashCode() : 0);
        result = 31 * result + (countRegister1 != null ? countRegister1.hashCode() : 0);
        result = 31 * result + (countRegister2 != null ? countRegister2.hashCode() : 0);
        result = 31 * result + (countRegister3 != null ? countRegister3.hashCode() : 0);
        result = 31 * result + (countRegister4 != null ? countRegister4.hashCode() : 0);
        result = 31 * result + (powerP1 != null ? powerP1.hashCode() : 0);
        result = 31 * result + (powerP2 != null ? powerP2.hashCode() : 0);
        result = 31 * result + (powerP3 != null ? powerP3.hashCode() : 0);
        result = 31 * result + (workP1 != null ? workP1.hashCode() : 0);
        result = 31 * result + (workP2 != null ? workP2.hashCode() : 0);
        result = 31 * result + (workP3 != null ? workP3.hashCode() : 0);
        result = 31 * result + (frequency != null ? frequency.hashCode() : 0);
        result = 31 * result + (voltage != null ? voltage.hashCode() : 0);
        return result;
    }
}
