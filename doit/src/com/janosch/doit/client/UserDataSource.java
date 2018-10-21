package com.janosch.doit.client;

// Gwt.
import com.google.gwt.core.client.GWT;

// SmartGwt.
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.data.fields.DataSourceDateField;

// DoIt.
import com.janosch.doit.client.CustomRestDataSource;


public class UserDataSource extends CustomRestDataSource {
	
	private static UserDataSource instance = null;

	public static UserDataSource getInstance()
	{
		if (instance == null) {
			instance = new UserDataSource();
			instance.setID("UserDataSource");
		}
		return instance;
	}

	private UserDataSource()
	{
		setDataURL(GWT.getHostPageBaseURL() + "rest/rest.mhtml");

		DataSourceIntegerField id = new DataSourceIntegerField("id", "ID");
		id.setPrimaryKey(true);
		DataSourceTextField first_name = new DataSourceTextField("first_name", "first name");
		first_name.setRequired(true);
		DataSourceTextField last_name = new DataSourceTextField("last_name", "last name");
		last_name.setRequired(true);
		DataSourceDateField birth_date = new DataSourceDateField("birth_date", "birth date");
		DataSourceTextField mail_addr = new DataSourceTextField("mail_addr", "mail address");
		DataSourceTextField phone_num = new DataSourceTextField("phone_num", "phone number");

		setFields(id, first_name, last_name, birth_date, mail_addr, phone_num);
	}
}
