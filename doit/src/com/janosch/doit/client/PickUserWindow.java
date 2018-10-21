package com.janosch.doit.client;

// GWT.
import com.google.gwt.core.client.GWT;

// SmartGWT.
import com.smartgwt.client.util.SC;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.Window;

// DoIt.
import com.janosch.doit.client.RecordHandler;
import com.janosch.doit.client.UserDataSource;


public class PickUserWindow extends Window {
	
	private ListGrid users_grid = new ListGrid();
	private PickUserWindow this_window;
	private RecordHandler record_handler;

	public PickUserWindow()
	{
		this_window = this;
		setTitle("Users");
		setIsModal(true);
		setAutoSize(true);

		VLayout layout = createLayout();
		addMember(layout);
	}

	private VLayout createLayout()
	{
		VLayout layout = new VLayout();
		layout.addMember(createUsersGrid());
		return layout;
	}

	private ListGrid createUsersGrid()
	{
		users_grid.setDataSource(UserDataSource.getInstance());
		users_grid.setAutoFitData(Autofit.BOTH);
		users_grid.setAutoFetchData(true);
		users_grid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event)
			{
				if (record_handler == null)
					SC.warn("RecordHandler hasn't been set yet!");
				else {
					record_handler.handleRecord(event.getRecord());
					this_window.close();
				}
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

	public void setRecordHandler(RecordHandler rh)
	{
		record_handler = rh;
	}

}
