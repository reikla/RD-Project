package at.ac.fh.salzburg.smartmeter.access;

/**
 * Implement this interface to regulate the access to the database
 */
public interface IPermissionManager {
    boolean IsAllowedToAccess(IUserContext userContext, IDataSourceContext dataSourceContext);
    String GiveGroupName(IUserContext userContext);
}
