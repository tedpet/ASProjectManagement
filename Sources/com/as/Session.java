package com.as;
/*
 * ASProjectManagement
 */
import com.as.model.Person;

import er.corebl.ERCoreBL;
import er.extensions.appserver.ERXSession;
import er.extensions.foundation.ERXThreadStorage;

public class Session extends ERXSession {
	private static final long serialVersionUID = 1L;

	private MainNavigationController _navController;
	private Person _user;
	
	public Session() {
	}
	
	@Override
	public void awake() {
		// TODO Auto-generated method stub
		super.awake();
		ERXThreadStorage.takeValueForKey(user(), "currentUser");
		
		if (user() != null) {
			ERCoreBL.setActor(user());
		}
	}
	
	public MainNavigationController navController() {
		if (_navController == null) {
			_navController = new MainNavigationController(this);
		}
		return _navController;
	}

	public Person user() {
		return _user;
	}

	public void setUser(Person _user) {
		this._user = _user;
		ERCoreBL.setActor(user());
	}


}
