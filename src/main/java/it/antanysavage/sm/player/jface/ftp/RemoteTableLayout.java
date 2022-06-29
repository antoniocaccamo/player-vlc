package it.antanysavage.sm.player.jface.ftp;

import it.antanysavage.sm.player.bundle.LocaleManager;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class RemoteTableLayout extends TableLayout{
	
	public RemoteTableLayout(Table remotePathTable) {
		super();
		TableColumn tc = new TableColumn(remotePathTable, SWT.LEFT);
		tc.setText(LocaleManager.getText("app.video.sequence.table.column.1"));
		this.addColumnData(new ColumnWeightData(50, true));

		tc = new TableColumn(remotePathTable, SWT.LEFT);
		tc.setText(LocaleManager.getText("app.video.sequence.table.column.3"));
		this.addColumnData(new ColumnWeightData(50, true));

		tc = new TableColumn(remotePathTable, SWT.LEFT);
		tc.setText("Size");
		this.addColumnData(new ColumnWeightData(50, true));

		tc = new TableColumn(remotePathTable, SWT.LEFT);
		tc.setText("Date");
		this.addColumnData(new ColumnWeightData(50, true));
	}

}
