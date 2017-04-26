package at.ac.fh.salzburg.smartmeter.ldap;

import java.util.List;

public interface ContactDAO {

    List getAllUserID();
    List getContactDetails(String commonName);
}