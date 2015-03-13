package com.as;

import com.as.model.Client;
import com.as.model.Person;
import com.as.model.Project;
import com.webobjects.appserver.WOComponent;
import com.webobjects.directtoweb.D2W;
import com.webobjects.directtoweb.D2WPage;
import com.webobjects.directtoweb.EditPageInterface;
import com.webobjects.directtoweb.ErrorPageInterface;
import com.webobjects.directtoweb.ListPageInterface;
import com.webobjects.directtoweb.QueryPageInterface;
import com.webobjects.eoaccess.EODatabaseDataSource;
import com.webobjects.eocontrol.EOEditingContext;

import er.extensions.eof.ERXEC;
import er.extensions.eof.ERXFetchSpecification;
import er.extensions.eof.ERXQ;

public class MainNavigationController {

	private Session _session;
	public String PERSON = "Person";
	public String PROJECT = "Project";
	public String CLIENT = "Client";

	public MainNavigationController(Session s) {
		super();
		_session = s;
	}

	// NAV ACTIONS
	
	public WOComponent homeAction() {
        return D2W.factory().defaultPage(session());
    }
	
	public WOComponent personAction() {
		EOEditingContext ec = ERXEC.newEditingContext();
		ec.lock();

		ListPageInterface lpi;
		try {
			EODatabaseDataSource ds = new EODatabaseDataSource(ec, PERSON);

			//					ERXQ.equals(Person.IS_ACTIVE_KEY, false).and(Person.PERSON.eq(session().user())), null);

			ERXFetchSpecification<Person> fs = 
			new ERXFetchSpecification<Person>(Person.ENTITY_NAME, 
					ERXQ.equals(Person.IS_ACTIVE_KEY, true), null);

			ds.setFetchSpecification(fs);

			lpi = D2W.factory().listPageForEntityNamed(Person.ENTITY_NAME, session());
			lpi.setDataSource(ds);
			
			if(lpi instanceof D2WPage) {
				
				((D2WPage) lpi).d2wContext().takeValueForKey("Person", "navigationState");
				//((D2WPage) lpi).d2wContext().takeValueForKey("AgendaInstructions", "headerInstructionComponentName");
			}			
		}
		finally {
			ec.unlock();
		}
		return (WOComponent) lpi;
    }
	
	public WOComponent createPersonAction() {
		return newObjectForEntityName(Person.ENTITY_NAME);
	}
	
	public WOComponent searchPersonAction() {
		return queryPageForEntityName(Person.ENTITY_NAME);
	}
	

	public WOComponent clientAction() {
		EOEditingContext ec = ERXEC.newEditingContext();
		ec.lock();

		ListPageInterface lpi;
		try {
			EODatabaseDataSource ds = new EODatabaseDataSource(ec, CLIENT);

			//					ERXQ.equals(Person.IS_ACTIVE_KEY, false).and(Person.PERSON.eq(session().user())), null);

			ERXFetchSpecification<Client> fs = 
			new ERXFetchSpecification<Client>(Client.ENTITY_NAME, 
					ERXQ.equals(Client.IS_ACTIVE_KEY, true), null);

			ds.setFetchSpecification(fs);

			lpi = D2W.factory().listPageForEntityNamed(Client.ENTITY_NAME, session());
			lpi.setDataSource(ds);
			
			if(lpi instanceof D2WPage) {
				
				((D2WPage) lpi).d2wContext().takeValueForKey("Client", "navigationState");
				//((D2WPage) lpi).d2wContext().takeValueForKey("AgendaInstructions", "headerInstructionComponentName");
			}			
		}
		finally {
			ec.unlock();
		}
		return (WOComponent) lpi;
    }
	
	public WOComponent createClientAction() {
		return newObjectForEntityName(Client.ENTITY_NAME);
	}
	
	public WOComponent searchClientAction() {
		return queryPageForEntityName(Client.ENTITY_NAME);
	}
	
	public WOComponent projectAction() {
		EOEditingContext ec = ERXEC.newEditingContext();
		ec.lock();

		ListPageInterface lpi;
		try {
			EODatabaseDataSource ds = new EODatabaseDataSource(ec, PROJECT);

			//					ERXQ.equals(Person.IS_ACTIVE_KEY, false).and(Person.PERSON.eq(session().user())), null);

			ERXFetchSpecification<Project> fs = new ERXFetchSpecification<Project>(Project.ENTITY_NAME, ERXQ.equals(Project.COMPLETE_KEY, false), null);

			ds.setFetchSpecification(fs);

			lpi = D2W.factory().listPageForEntityNamed(Project.ENTITY_NAME, session());
			lpi.setDataSource(ds);
			
			if(lpi instanceof D2WPage) {
				
				((D2WPage) lpi).d2wContext().takeValueForKey("Project", "navigationState");
				//((D2WPage) lpi).d2wContext().takeValueForKey("AgendaInstructions", "headerInstructionComponentName");
			}			
		}
		finally {
			ec.unlock();
		}
		return (WOComponent) lpi;
    }
	
	public WOComponent createProjectAction() {
		return newObjectForEntityName(Project.ENTITY_NAME);
	}
	
	public WOComponent searchProjectAction() {
		return queryPageForEntityName(Project.ENTITY_NAME);
	}
	
	public WOComponent printProjectReportAction() {
		return D2W.factory().pageForConfigurationNamed("PrintMainReport", session());
	}
	
//	// ADMIN
//	
//	public WOComponent adminAction() {
//		return queryPageForEntityName(Talent.ENTITY_NAME);
//	}
//	
//	// MOVIES
//	
//	public WOComponent queryMovieAction() {
//		return queryPageForEntityName(MOVIE);
//	}
//	
//	public WOComponent createMovieAction() {
//		return newObjectForEntityName(MOVIE);
//	}
//	
//	// STUDIOS
//	
//	public WOComponent queryStudioAction() {
//		return queryPageForEntityName(STUDIO);
//	}
//	
//	public WOComponent createStudioAction() {
//		return newObjectForEntityName(STUDIO);
//	}
//	
//	// TALENT
//	
//	public WOComponent queryTalentAction() {
//		return queryPageForEntityName(Talent.ENTITY_NAME);
//	}
//	
//	public WOComponent createTalentAction() {
//		return newObjectForEntityName(Talent.ENTITY_NAME);
//	}
//	
//	// VOTING
//	
//	public WOComponent queryVotingAction() {
//		return queryPageForEntityName(Voting.ENTITY_NAME);
//	}
//	
//	public WOComponent createVotingAction() {
//		return newObjectForEntityName(Voting.ENTITY_NAME);
//	}
//	
//	// REVIEW
//	
//	public WOComponent queryReviewAction() {
//		return queryPageForEntityName(REVIEW);
//	}
//	
//	public WOComponent createReviewAction() {
//		return newObjectForEntityName(REVIEW);
//	}
	
	// GENERIC ACTIONS
	
    public WOComponent queryPageForEntityName(String entityName) {
        QueryPageInterface newQueryPage = D2W.factory().queryPageForEntityNamed(entityName, session());
        return (WOComponent) newQueryPage;
    }
    
    public WOComponent newObjectForEntityName(String entityName) {
        WOComponent nextPage = null;
        try {
            EditPageInterface epi = D2W.factory().editPageForNewObjectWithEntityNamed(entityName, session());
            epi.setNextPage(session().context().page());
            nextPage = (WOComponent) epi;
        } catch (IllegalArgumentException e) {
            ErrorPageInterface epf = D2W.factory().errorPage(session());
            epf.setMessage(e.toString());
            epf.setNextPage(session().context().page());
            nextPage = (WOComponent) epf;
        }
        return nextPage;
    }
    
    // ACCESSORS
    
    public Session session() {
		return _session;
	}

	public void setSession(Session s) {
		_session = s;
	}
}
