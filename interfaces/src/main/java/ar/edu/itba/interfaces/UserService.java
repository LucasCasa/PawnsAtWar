package ar.edu.itba.interfaces;

import ar.edu.itba.model.User;

public interface UserService {

	User getByUsername(String username);

}
