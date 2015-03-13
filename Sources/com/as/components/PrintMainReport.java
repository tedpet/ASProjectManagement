package com.as.components;

import java.io.File;
import java.util.concurrent.Callable;

import com.as.FileTaskDownloadController;
import com.as.Reports;
import com.as.model.Person;
import com.sun.org.apache.xml.internal.security.Init;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOComponent;

import er.coolcomponents.CCAjaxLongResponsePage;
import er.directtoweb.pages.templates.ERD2WInspectPageTemplate;
import er.extensions.eof.ERXEC;

import com.webobjects.foundation.NSArray;
import com.webobjects.appserver.WOActionResults;

public class PrintMainReport  extends ERD2WInspectPageTemplate {
    
	private NSArray<Person> _accountExecList;
	private Person _selectedPerson;
	public Person _thePerson;


	public PrintMainReport(WOContext context) {
        super(context);

	}
	
	@Override
	public void awake() {
		// TODO Auto-generated method stub
		super.awake();
		
        System.out.println("the list is" );
        _accountExecList = Person.fetchPersons(ERXEC.newEditingContext(), Person.IS_ACCOUNT_EXEC.eq(true).and(Person.IS_ACTIVE.eq(true)), Person.LAST_NAME.ascs());
	}

	/**
	 * @return the accountExecList
	 */
	public NSArray<Person> accountExecList() {
		return _accountExecList;
	}

	/**
	 * @param accountExecList the accountExecList to set
	 */
	public void setAccountExecList(NSArray<Person> accountExecList) {
		this._accountExecList = accountExecList;
	}

	/**
	 * @return the selectedPerson
	 */
	public Person selectedPerson() {
		return _selectedPerson;
	}

	/**
	 * @param selectedPerson the selectedPerson to set
	 */
	public void setSelectedPerson(Person selectedPerson) {
		System.out.println("Setting SelectedPerson: " + selectedPerson.fullName());
		this._selectedPerson = selectedPerson;
	}
	

	public WOActionResults mainPopupAction() {
		System.out.println("mainPopupAction ");

		return null;
	}

	public WOActionResults reportForAE() {
		// Create the task
	    Callable<File> reportTask = Reports.createProjectReportForAE(selectedPerson());

	    // Create the long response page
	    CCAjaxLongResponsePage nextPage = pageWithName(CCAjaxLongResponsePage.class);

	    // Push the task into the long response page
	    nextPage.setTask(reportTask);

	    // Controller for handling the Callable result in the long response page
	    FileTaskDownloadController nextPageController = new FileTaskDownloadController();

	    // Hyperlink text on the "Your file is downloaded page" to get back here
	    nextPageController.setReturnLinkText("Reports Menu");

	    // The filename for the download
	    nextPageController.setDownloadFileNameForClient("Project List for AE.pdf");

	    nextPage.setNextPageForResultController(nextPageController);

	 
	    
	    return nextPage;    
	}

	
	
}