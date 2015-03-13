package com.as;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.Callable;

import tp.jasperreports.TPJRFetchSpecificationReportTask;

import com.as.model.Client;
import com.as.model.Person;
import com.as.model.Project;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOQualifier;

import er.extensions.eof.ERXEC;
import er.extensions.eof.ERXFetchSpecification;
import er.extensions.eof.ERXSortOrdering.ERXSortOrderings;

public class Reports {
	private static File reportFile;
	
	
	public static Callable<File> createProjectReportForAE(Person anAE) {
		EOEditingContext ec = ERXEC.newEditingContext();
		ec.lock();

		TPJRFetchSpecificationReportTask reportTask = null;
		try {
			
			ERXSortOrderings sortOrderings = Project.CLIENT.dot(Client.CLIENT_NAME.ascs());
			EOQualifier qualifier = Project.COMPLETE.eq(false).and(Project.CLIENT.dot(Client.ACCOUNT_EXEC.eq(anAE)));

			ERXFetchSpecification<Project> fs = new ERXFetchSpecification<Project>(Project.ENTITY_NAME, qualifier, sortOrderings);

			String reportDescription = "Open Project(s) for " + anAE.fullName();

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("reportDescription", reportDescription);

			reportTask = new TPJRFetchSpecificationReportTask(fs, "ProjectReportForAE.jasper", parameters);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();


		} finally {
			//NSLog.out.appendln("finally : "  );
			ec.unlock();
		}

		return reportTask;

	}
	
	
	
}