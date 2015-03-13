package com.as;

import com.as.model.Client;
import com.as.model.Person;
import com.as.model.Project;
import com.webobjects.eoaccess.EODatabaseDataSource;
import com.webobjects.eocontrol.EODataSource;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;

import er.directtoweb.delegates.ERDQueryDataSourceDelegateInterface;
import er.directtoweb.pages.ERD2WQueryPage;
import er.extensions.eof.ERXQ;
import er.extensions.foundation.ERXThreadStorage;

public class ProjectsForUserDelegate implements ERDQueryDataSourceDelegateInterface {

	@Override
	public EODataSource queryDataSource(ERD2WQueryPage sender) {
		EODataSource ds = sender.dataSource();

		if (ds == null || !(ds instanceof EODatabaseDataSource)) {
			ds = new EODatabaseDataSource(sender.session().defaultEditingContext(), sender.entity().name());
			sender.setDataSource(ds);
		}

		EOFetchSpecification fs = ((EODatabaseDataSource) ds).fetchSpecification();
		fs.setQualifier(qualifierFromSender(sender));
		fs.setIsDeep(sender.isDeep());
		fs.setUsesDistinct(sender.usesDistinct());
		fs.setRefreshesRefetchedObjects(sender.refreshRefetchedObjects());

		int limit = sender.fetchLimit();
		if (limit != 0) {
			fs.setFetchLimit(limit);
		}

		@SuppressWarnings("unchecked")
		NSArray<String> prefetchingRelationshipKeyPaths = sender.prefetchingRelationshipKeyPaths();
		if (prefetchingRelationshipKeyPaths != null && prefetchingRelationshipKeyPaths.count() > 0) {
			fs.setPrefetchingRelationshipKeyPaths(prefetchingRelationshipKeyPaths);
		}
		return ds;
	}

	/*
	 * get the current user from the thread storage and and it to the query.
	 */
	private EOQualifier qualifierFromSender(ERD2WQueryPage sender) {
		
		Person thePerson = (Person) ERXThreadStorage.valueForKey("currentUser");
		EOQualifier q = sender.qualifier();
		
		EOQualifier personQ = Project.CLIENT.dot(Client.ACCOUNT_EXEC.eq(thePerson));
		
		q = ERXQ.and(personQ, sender.qualifier());
		System.out.println("qualifierFromSender = " + q);
		return q;
	}


}
