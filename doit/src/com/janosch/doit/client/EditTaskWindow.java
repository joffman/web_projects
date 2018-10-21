package com.janosch.doit.client;

// java.util.
import java.util.Date;

// GWT.
import com.google.gwt.core.client.GWT;

// SmartGWT.
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.Window;

// DoIt.
import com.janosch.doit.client.PickUserWindow;
import com.janosch.doit.client.RecordHandler;
import com.janosch.doit.client.TaskDataSource;
import com.janosch.doit.client.TaskCommentDataSource;


public class EditTaskWindow extends Window {

	private DynamicForm task_form = new DynamicForm();
	private int task_id;

	public EditTaskWindow(Record record)
	{
		task_id = record.getAttributeAsInt("id");
		initGUI();
		task_form.editRecord(record);
	}

	private void initGUI()
	{
		setTitle("Edit task");
		setAutoSize(true);
		setAutoCenter(true);
		setIsModal(true);
		setShowModalMask(true);

		VLayout layout = createMainLayout();
		addMember(layout);
	}

	private VLayout createMainLayout()
	{
		task_form = createTaskForm();
		
		DynamicForm new_comment_form = createNewCommentForm();
		//new_comment_form.setDataSource(TaskCommentDataSource.getInstance());	// TODO remove
		ListGrid comments_grid = createCommentsGrid();
		//comments_grid.setDataSource(TaskCommentDataSource.getInstance());

		VLayout layout = new VLayout();
		layout.addMember(task_form);
		layout.addMember(new_comment_form);
		layout.addMember(comments_grid);
		return layout;
	}

	private DynamicForm createTaskForm()		// TODO: Clean this up.
	{
		TextItem title_item = new TextItem("title", "Title");
		TextAreaItem description_item =
			new TextAreaItem("description", "Description");

		DateTimeItem created_datetime_item =
			new DateTimeItem("created_datetime", "Creation date");
		created_datetime_item.setCanEdit(false);
		DateTimeItem modified_datetime_item =
			new DateTimeItem("modified_datetime", "Modification date");
		modified_datetime_item.setCanEdit(false);
		DateTimeItem due_datetime_item =
			new DateTimeItem("due_datetime", "Due date");

		final IntegerItem worker_id_item = new IntegerItem("worker_id", "Worker-ID");
		ButtonItem pick_user_button = new ButtonItem("pick_user", "pick user");
		pick_user_button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event)
			{
				PickUserWindow pick_user_window = new PickUserWindow();
				pick_user_window.setRecordHandler(new RecordHandler() {
					@Override
					public void handleRecord(Record record)
					{
						worker_id_item.setValue(record.getAttribute("id"));
					}
				});
				pick_user_window.show();
			}
		});
		pick_user_button.setStartRow(false);

		SelectItem status_item = new SelectItem("status", "Status");

		ButtonItem save_button = new ButtonItem("save_button", "save task");
		save_button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				task_form.saveData(new DSCallback() {
					@Override
					public void execute(DSResponse response,
							java.lang.Object data,
							DSRequest request)
					{
						Dialog dialog = new Dialog();
						dialog.setMessage("Task updated successfully.");
						dialog.setButtons(Dialog.OK);
						dialog.setIsModal(true);
						dialog.show();
					}
				});
			}
		});

		task_form.setNumCols(3);	// label, text-field, button
		task_form.setFields(title_item, description_item, created_datetime_item,
				modified_datetime_item, due_datetime_item, worker_id_item,
				pick_user_button, status_item, save_button);
		task_form.setDataSource(TaskDataSource.getInstance());
		return task_form;
	}

	private DynamicForm createNewCommentForm()
	{
		final DynamicForm comment_form = new DynamicForm();
		comment_form.setDataSource(TaskCommentDataSource.getInstance());

		TextAreaItem comment_item = new TextAreaItem("comment");
		IntegerItem task_id_item = new IntegerItem("task_id");
		task_id_item.setHidden(true);
		IntegerItem user_id_item = new IntegerItem("user_id");
		user_id_item.setHidden(true);

		ButtonItem save_comment_button = new ButtonItem("save_comment_btn", "save comment");
		save_comment_button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event)
			{
				comment_form.setValue("user_id", 25);
				// TODO dummy; this has to be the id of the user; this should be set by the back-end (from the session)
				comment_form.setValue("task_id", task_form.getValueAsString("id"));
				comment_form.saveData(new DSCallback() {
					@Override
					public void execute(DSResponse response, java.lang.Object data, DSRequest request)
					{
						// TODO check return value (validation)
						// Clear form so that the user can write another (new) comment.
						comment_form.editNewRecord();
					}
				});
			}
		});

		comment_form.setFields(comment_item, task_id_item, user_id_item, save_comment_button);
		return comment_form;
	}

	private ListGrid createCommentsGrid()
	{
		ListGrid grid = new ListGrid();
		grid.setDataSource(TaskCommentDataSource.getInstance());
		Criteria criteria = new Criteria();
		criteria.addCriteria("task_id", task_id);
		grid.setInitialCriteria(criteria);
		grid.setAutoFetchData(true);
		grid.setCanRemoveRecords(true);	// TODO: check user rights
		grid.setWrapCells(true);	// comments can be pretty long and have multiple lines
		grid.setFixedRecordHeights(false);
		grid.setWidth(1000);
		grid.setHeight(500);
		grid.setEditEvent(ListGridEditEvent.CLICK);

		ListGridField user_id_field = new ListGridField("user_id", 80);
		ListGridField comment_field = new ListGridField("comment", 220);
		comment_field.setCanEdit(true);
		ListGridField created_datetime_field = new ListGridField("created_datetime", 120);
		ListGridField modified_datetime_field = new ListGridField("modified_datetime", 120);

		grid.setFields(user_id_field, comment_field,
				created_datetime_field, modified_datetime_field);
		return grid;
	}
}
