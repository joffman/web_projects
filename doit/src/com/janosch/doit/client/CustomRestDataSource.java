package com.janosch.doit.client;

// SmartGwt.
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;


public abstract class CustomRestDataSource extends RestDataSource {

	protected CustomRestDataSource()	// private?
	{
		setDataFormat(DSDataFormat.JSON);
		setDataProtocol(DSProtocol.POSTMESSAGE);
		setJsonPrefix(""); // see RestDataSource docs
		setJsonSuffix("");
		setDropExtraFields(true);
		setSendExtraFields(false);
		setAllOperationBindings();
	}

	private void setAllOperationBindings()
	{
		OperationBinding add = new OperationBinding();
		add.setOperationType(DSOperationType.ADD);
		add.setDataProtocol(DSProtocol.POSTMESSAGE);
		setOperationBindings(add);

		OperationBinding fetch = new OperationBinding();
		fetch.setOperationType(DSOperationType.FETCH);
		fetch.setDataProtocol(DSProtocol.POSTMESSAGE);
		setOperationBindings(fetch);

		OperationBinding remove = new OperationBinding();
		remove.setOperationType(DSOperationType.REMOVE);
		remove.setDataProtocol(DSProtocol.POSTMESSAGE);
		setOperationBindings(remove);

		OperationBinding update = new OperationBinding();
		update.setOperationType(DSOperationType.UPDATE);
		update.setDataProtocol(DSProtocol.POSTMESSAGE);
		setOperationBindings(update);
	}

}
