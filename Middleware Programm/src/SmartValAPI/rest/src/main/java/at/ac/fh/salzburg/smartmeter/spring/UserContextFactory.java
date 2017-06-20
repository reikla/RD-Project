package at.ac.fh.salzburg.smartmeter.spring;

import at.ac.fh.salzburg.smartmeter.access.IUserContext;
import at.ac.fh.salzburg.smartmeter.access.IUserContextFactory;
import at.ac.fh.salzburg.smartmeter.access.UserContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserContextFactory implements IUserContextFactory {
    public IUserContext getUserContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserContext userContext = new UserContext(authentication.getName(),null);
        return userContext;
    }
}
