package at.ac.fh.salzburg.smartmeter.ldap;

import at.ac.fh.salzburg.smartmeter.access.IDataSourceContext;
import at.ac.fh.salzburg.smartmeter.access.IPermissionManager;
import at.ac.fh.salzburg.smartmeter.access.IUserContext;

public interface ILDAPManager extends IPermissionManager{

    boolean CreateCustomer(IUserContext userContext, IDataSourceContext dataSourceContext);
    boolean CreateConsultant(IUserContext source, IUserContext destination);
    boolean CreateSmartMeter(IDataSourceContext dataSourceContext);
    boolean DeleteSmartMeter(IDataSourceContext dataSourceContext);
    boolean DeleteUser(IUserContext userContext);
    boolean IsAllowedToAccess(IUserContext userContext, IDataSourceContext dataSourceContext);
    boolean AddMeterToUser(IUserContext userContext, IDataSourceContext dataSourceContext);
    boolean AddUserToUser(IUserContext source, IUserContext destination);
    boolean AddUserToGroup(IUserContext userContext, String Group);
    boolean DeleteMeterFromUser(IUserContext userContext, IDataSourceContext dataSourceContext);
    boolean DeleteMeterfromAll(IDataSourceContext dataSourceContext);
    boolean DeleteUserFromGroup(IUserContext userContext, String Group);
    boolean DeleteUserFromAll(IUserContext userContext);
}