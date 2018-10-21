package com.janosch.doit.client;

// Gwt.
import com.google.gwt.core.client.GWT;

// SmartGwt.
import com.smartgwt.client.types.Autofit;
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
import com.janosch.doit.client.EditUserWindow;
import com.janosch.doit.client.UserDataSource;


public class UsersWindow extends Window {
	
	private ListGrid users_grid = new ListGrid();

	public UsersWindow()
	{
		setTitle("Users");
		setIsModal(true);
		setMaximized(true);

		VLayout layout = createLayout();
		addMember(layout);
	}

	private VLayout createLayout()
	{
		VLayout layout = new VLayout();
		layout.addMember(createUsersGrid());
		layout.addMember(createButtonBar());
		return layout;
	}

	private ListGrid createUsersGrid()
	{
		users_grid.setDataSource(UserDataSource.getInstance());
		users_grid.setAutoFitData(Autofit.VERTICAL);
		users_grid.setAutoFetchData(true);
		users_grid.setCanRemoveRecords(true);
		users_grid.setWarnOnRemoval(true);
		users_grid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event)
			{
				EditUserWindow window = new EditUserWindow(event.getRecord());
				window.show();
			}
		});

		ListGridField id_field = new ListGridField("id", 40);
		ListGridField first_name_field = new ListGridField("first_name", 120);
		ListGridField last_name_field = new ListGridField("last_name", 120);
		ListGridField birth_date_field = new ListGridField("birth_date", 80);
		ListGridField mail_addr_field = new ListGridField("mail_addr", 220);
		ListGridField phone_num_field = new ListGridField("phone_num", 130);

		users_grid.setFields(id_field, first_name_field, last_name_field,
				birth_date_field, mail_addr_field, phone_num_field);
		return users_grid;
	}

	private HLayout createButtonBar()
	{
		HLayout button_bar = new HLayout();
		button_bar.addMember(createRefreshButton());
		button_bar.addMember(createAddButton());
		return button_bar;
	}

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

	private Button createAddButton()
	{
		final Button add_user_button = new Button("add user");
		add_user_button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				EditUserWindow window = new EditUserWindow();
				window.show();
			}
		});
		return add_user_button;
	}

}
