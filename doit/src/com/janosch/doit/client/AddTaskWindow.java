package com.janosch.doit.client;

// java.util.
import java.util.Date;

// GWT.
import com.google.gwt.core.client.GWT;

// SmartGWT.
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.Window;

// DoIt.
import com.janosch.doit.client.TaskDataSource;


public class AddTaskWindow extends Window {

	private Window this_window;

	public AddTaskWindow()
	{
		this_window = this;

		setTitle("Add task");
		setAutoSize(true);
		setAutoCenter(true);
		setIsModal(true);
		setShowModalMask(true);

		VLayout layout = createMainLayout();
		addMember(layout);
	}

	private VLayout createMainLayout()
	{
		VLayout layout = new VLayout();
		layout.addMember(createTaskForm());
		return layout;
	}

	private DynamicForm createTaskForm()
	{
		final DynamicForm task_form = new DynamicForm();

		TextItem title_item = new TextItem("title", "Title");
		SelectItem status_item = new SelectItem("status", "Status");
		status_item.setDefaultValue("deactivated");
		IntegerItem worker_id_item = new IntegerItem("worker_id");
		worker_id_item.setValue(25);	// TODO: use id of user
		worker_id_item.setHidden(true);

		ButtonItem save_button = new ButtonItem("save_button", "Save");
		save_button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event)
			{
				if (task_form.validate()) {
					task_form.saveData();
					this_window.close();
				}
			}
		});

		task_form.setFields(title_item, status_item, worker_id_item, save_button);
		task_form.setDataSource(TaskDataSource.getInstance());
		return task_form;
	}

}
