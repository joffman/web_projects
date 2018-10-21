package com.janosch.doit.client;

// Gwt.
import com.google.gwt.core.client.GWT;

// SmartGwt.
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

// DoIt.
import com.janosch.doit.client.AddTaskWindow;
import com.janosch.doit.client.EditTaskWindow;
import com.janosch.doit.client.TaskDataSource;


public class TasksWindow extends Window {
	
	public TasksWindow()
	{
		setTitle("Tasks");
		setIsModal(true);
		setMaximized(true);

		VLayout layout = createMainLayout();
		addMember(layout);
	}

	private VLayout createMainLayout()
	{
		VLayout vlayout = new VLayout();

		vlayout.addMember(createButtonBar());
		vlayout.addMember(createTaskGrid());

		return vlayout;
	}

	private HLayout createButtonBar()
	{
		HLayout button_bar = new HLayout();
		//button_bar.addMember(createRefreshButton());
		button_bar.addMember(createAddButton());
		return button_bar;
	}

	/*
	private Button createRefreshButton()
	{
		final Button refresh_button = new Button("refresh");
		refresh_button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				users_grid.refreshData();
			}
		});
		return refresh_button;
	}
	*/

	private Button createAddButton()
	{
		final Button add_task_button = new Button("add task");
		add_task_button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AddTaskWindow window = new AddTaskWindow();
				window.show();
			}
		});
		return add_task_button;
	}

	private ListGrid createTaskGrid()
	{
		ListGrid task_grid = new ListGrid();
		task_grid.setAutoFitData(Autofit.VERTICAL);	// or both?
		task_grid.setGroupStartOpen(GroupStartOpen.ALL);
		task_grid.setGroupByField("status");
		task_grid.setSortByGroupFirst(true);
		// todo: Additionaly, sort by due-datetime.
		task_grid.setEditEvent(ListGridEditEvent.CLICK);
		task_grid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event)
			{
				EditTaskWindow window = new EditTaskWindow(event.getRecord());
				window.show();
			}
		});
		task_grid.setDataSource(TaskDataSource.getInstance());
		task_grid.setAutoFetchData(true);

		ListGridField id_field = new ListGridField("id", 80);
		ListGridField title_field = new ListGridField("title", 260);
		ListGridField due_datetime_field = new ListGridField("due_datetime", 120);
		ListGridField worker_id_field = new ListGridField("worker_id", 80);
		ListGridField status_field = new ListGridField("status", 150);
		status_field.setCanEdit(true);

		task_grid.setFields(id_field, title_field, due_datetime_field, worker_id_field, status_field);
		return task_grid;
	}

}
