package at.ac.fh.salzburg.smartmeter.access;

/**
 * Created by reimarklammer on 20.06.17.
 */

/**
 * Interface to encapsulate the retrieval of the UserContext out of a request.
 */
public interface IUserContextFactory{

    /**
     * returns the IUserContext of a request.
     */
    IUserContext getUserContext();
}
