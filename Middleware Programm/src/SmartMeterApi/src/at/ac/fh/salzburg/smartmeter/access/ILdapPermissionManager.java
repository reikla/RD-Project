package at.ac.fh.salzburg.smartmeter.access;

/**
 * The Permission Manager is indented to query the LDAP Server an decide if a users is allowed to access the specified datasource
 */
public interface ILdapPermissionManager {
    public boolean IsAllowedToAccess(IUserContext userContext, IDataSourceContext dataSourceContext);
}
