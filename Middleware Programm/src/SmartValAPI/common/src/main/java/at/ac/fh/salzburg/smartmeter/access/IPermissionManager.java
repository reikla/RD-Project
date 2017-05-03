package at.ac.fh.salzburg.smartmeter.access;

/**
 * Implement this interface to regulate the access to the database
 */
public interface IPermissionManager {
    boolean IsAllowedToAccess(IUserContext userContext, IDataSourceContext dataSourceContext);
    boolean CreateUser(IUserContext userContext, IDataSourceContext dataSourceContext);
    boolean DeleteUser(IUserContext userContext);
    boolean CreateSmartmeter(IDataSourceContext dataSourceContext);
    boolean DeleteSmartmeter(IDataSourceContext dataSourceContext);
    boolean AddMeterToUser(IUserContext userContext, IDataSourceContext dataSourceContext);
    boolean AddUserToGroup(IUserContext userContext, String Group);
    boolean DeleteMeterFromUser(IUserContext userContext, IDataSourceContext dataSourceContext);
    boolean DeleteMeterfromAll(IDataSourceContext dataSourceContext);
    boolean DeleteUserFromGroup(IUserContext userContext, String Group);
    boolean DeleteUserFromAll(IUserContext userContext);
}
