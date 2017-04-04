package at.ac.fh.salzburg;

/**
 * Created by reimarklammer on 28.03.17.
 */
public class CustomerEntity {
    private int customerId;
    private String lastname;
    private String firstname;
    private String street;
    private String postalCode;
    private String city;
    private String alias;
    private String companyCustomerId;
    private String signature;
    private String key;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCompanyCustomerId() {
        return companyCustomerId;
    }

    public void setCompanyCustomerId(String companyCustomerId) {
        this.companyCustomerId = companyCustomerId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerEntity that = (CustomerEntity) o;

        if (customerId != that.customerId) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (alias != null ? !alias.equals(that.alias) : that.alias != null) return false;
        if (companyCustomerId != null ? !companyCustomerId.equals(that.companyCustomerId) : that.companyCustomerId != null)
            return false;
        if (signature != null ? !signature.equals(that.signature) : that.signature != null) return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = customerId;
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (companyCustomerId != null ? companyCustomerId.hashCode() : 0);
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }
}
