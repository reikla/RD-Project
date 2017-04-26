package at.ac.fh.salzburg.smartmeter.ldap;

import java.util.List;

public interface ContactDAO {

    List getAllContactNames();
    List getContactDetails(String commonName);
}