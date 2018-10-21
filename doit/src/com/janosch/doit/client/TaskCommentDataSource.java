package com.janosch.doit.client;

// Java.

// Gwt.
import com.google.gwt.core.client.GWT;

// SmartGwt.
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.util.SC;

// DoIt.
import com.janosch.doit.client.CustomRestDataSource;


public class TaskCommentDataSource extends CustomRestDataSource {
	
	private static TaskCommentDataSource instance = null;

	public static TaskCommentDataSource getInstance()
	{
		if (instance == null) {
			instance = new TaskCommentDataSource();
			instance.setID("TaskCommentDataSource");
		}
		return instance;
	}

	private TaskCommentDataSource()
	{
		setDataURL(GWT.getHostPageBaseURL() + "rest/rest.mhtml");

		DataSourceIntegerField id = new DataSourceIntegerField("id", "ID");
		id.setPrimaryKey(true);
		DataSourceTextField comment = new DataSourceTextField("comment", "comment");
		comment.setRequired(true);
		DataSourceIntegerField task_id =
			new DataSourceIntegerField("task_id", "task-id");
		task_id.setRequired(true);
		DataSourceIntegerField user_id =
			new DataSourceIntegerField("user_id", "user-id");
		user_id.setRequired(true);	// TODO: I think this shouldn't be required, because it should be set by backend (session)
		DataSourceDateTimeField created_datetime =
			new DataSourceDateTimeField("created_datetime", "created-datetime");
		DataSourceDateTimeField modified_datetime =
			new DataSourceDateTimeField("modified_datetime", "modified-datetime");

		setFields(id, comment, task_id, user_id, created_datetime, modified_datetime);
	}

	@Override
	protected void transformResponse(DSResponse response, DSRequest request, java.lang.Object data)
	{
		// TODO Maybe already transform the request, so that the data is directly stored correctly in the DB.
		// I don't understand it, but super.transformResponse has to be called first,
		// otherwise the default centralized error handling (showing a warning-dialog) doesn't work.
		super.transformResponse(response, request, data);

		DSOperationType operation_type = request.getOperationType();
		if (operation_type == DSOperationType.FETCH || operation_type == DSOperationType.UPDATE ||
				operation_type == DSOperationType.ADD) {
			for (Record record : response.getData()) {
				// comment can consist of multiple lines.
				// Since comment is contained in a <td> element, we have to use the <br/> element.
				String comment = record.getAttribute("comment");
				comment = comment.replace("\n", "<br/>");
				record.setAttribute("comment", comment);
			}
		}
	}

}
