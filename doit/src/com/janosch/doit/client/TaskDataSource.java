package com.janosch.doit.client;

// Java.
import java.util.Map;
import java.util.HashMap;

// Gwt.
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;

// SmartGwt.
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;

// DoIt.
import com.janosch.doit.client.CustomRestDataSource;


public class TaskDataSource extends CustomRestDataSource {
	
	private static TaskDataSource instance = null;

	public static TaskDataSource getInstance()
	{
		if (instance == null) {
			instance = new TaskDataSource();
			instance.setID("TaskDataSource");
		}
		return instance;
	}

	private TaskDataSource()
	{
		setDataURL(GWT.getHostPageBaseURL() + "rest/rest.mhtml");

		DataSourceIntegerField id = new DataSourceIntegerField("id", "ID");
		id.setPrimaryKey(true);
		DataSourceTextField title = new DataSourceTextField("title", "Title");
		title.setRequired(true);
		DataSourceTextField description =
			new DataSourceTextField("description", "description");
		DataSourceDateTimeField created_datetime =
			new DataSourceDateTimeField("created_datetime", "created-datetime");
		DataSourceDateTimeField modified_datetime =
			new DataSourceDateTimeField("modified_datetime", "modified-datetime");
		DataSourceDateTimeField due_datetime =
			new DataSourceDateTimeField("due_datetime", "due-datetime");
		DataSourceIntegerField worker_id =
			new DataSourceIntegerField("worker_id", "worker-id");
		worker_id.setRequired(true);
		DataSourceTextField status = new DataSourceTextField("status", "status");	// TODO: use lists instead (see Trello)
		Map<String, String> status_value_map = new HashMap<String, String>();
		status_value_map.put("deactivated", "0 - deactivated");	// numbers for sorting
		status_value_map.put("todo", "1 - todo");
		status_value_map.put("in_progress", "2 - in progress");
		status_value_map.put("done", "3 - done");
		status.setValueMap(status_value_map);
		status.setRequired(true);

		setFields(id, title, description, created_datetime, modified_datetime, due_datetime,
				worker_id, status);
	}

}
