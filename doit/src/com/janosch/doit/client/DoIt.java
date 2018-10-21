package com.janosch.doit.client;

// Gwt.
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

// SmartGwt.
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

// DoIt.
import com.janosch.doit.client.UsersWindow;
import com.janosch.doit.client.TasksWindow;


public class DoIt implements EntryPoint {

	public void onModuleLoad()
	{
		VLayout main_layout = new VLayout();
		main_layout.addMember(createUsersButton());
		main_layout.addMember(createTasksButton());

		RootPanel.get().add(main_layout);
	}

	private Button createUsersButton()
	{
		final Button users_button = new Button("manage users");
		users_button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UsersWindow window = new UsersWindow();
				window.show();
			}
		});
		return users_button;
	}

	private Button createTasksButton()
	{
		final Button tasks_button = new Button("manage tasks");
		tasks_button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TasksWindow window = new TasksWindow();
				window.show();
			}
		});
		return tasks_button;
	}

}
