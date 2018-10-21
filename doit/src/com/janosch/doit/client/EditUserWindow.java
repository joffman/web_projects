package com.janosch.doit.client;

// java.util.
import java.util.Date;

// Gwt.
import com.google.gwt.core.client.GWT;

// SmartGwt.
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.Window;

// DoIt.
import com.janosch.doit.client.UserDataSource;


public class EditUserWindow extends Window {

	private DynamicForm form = new DynamicForm();
	private boolean is_new_record = true;

	public EditUserWindow()
	{
		init();
	}

	public EditUserWindow(Record record)
	{
		is_new_record = false;
		init();
		form.editRecord(record);
	}

	private void init()
	{
		setTitle("Edit user");
		setAutoSize(true);
		setAutoCenter(true);
		setIsModal(true);
		setShowModalMask(true);

		VLayout layout = createUserFormLayout();
		addMember(layout);
	}

	private VLayout createUserFormLayout()
	{
		form = createUserForm();
		form.setDataSource(UserDataSource.getInstance());	// TODO put this into createForm function

		final Button save_button = new Button("Save");
		save_button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				form.saveData(new DSCallback() {
					@Override
					public void execute(DSResponse response,
							java.lang.Object data,
							DSRequest request)
					{
						Dialog dialog = new Dialog();
						if (is_new_record)
							dialog.setMessage("User added successfully.");
						else
							dialog.setMessage("User updated successfully.");
						Button ok_button = Dialog.OK;
						dialog.setButtons(ok_button);
						dialog.setIsModal(true);
						dialog.show();
						is_new_record = false;
					}
				});
			}
		});

		VLayout layout = new VLayout();
		layout.addMember(form);
		layout.addMember(save_button);
		return layout;
	}

	private DynamicForm createUserForm()
	{
		TextItem first_name_item = new TextItem("first_name", "First name");
		TextItem last_name_item = new TextItem("last_name", "Last name");

		DateItem birth_date_item = new DateItem("birth_date", "Birth date");
		birth_date_item.setStartDate(new Date(0, 1, 1));	// this is 1900, 1, 1
		birth_date_item.setEndDate(new Date());

		TextItem mail_addr_item = new TextItem("mail_addr", "Email address");	// TODO: set width
		TextItem phone_num_item = new TextItem("phone_num", "Phone number");

		DynamicForm form = new DynamicForm();
		form.setFields(first_name_item, last_name_item, birth_date_item,
				mail_addr_item, phone_num_item);
		return form;
	}
	
}
