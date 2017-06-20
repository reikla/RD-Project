package at.ac.fh.salzburg.smartmeter.access;

/**
 * Created by reimarklammer on 20.06.17.
 */
public class UserContext implements IUserContext {

    private String userId;
    private String password;


    public UserContext(String userId, String password){
        this.userId = userId;
        this.password = password;
    }


    @Override
    public String userid() {
        return userId;
    }

    @Override
    public String password() {
        return password;
    }
}
