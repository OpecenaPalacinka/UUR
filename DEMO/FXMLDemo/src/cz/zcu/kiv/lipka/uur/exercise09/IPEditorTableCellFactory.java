package cz.zcu.kiv.lipka.uur.exercise09;

import java.net.InetAddress;

import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Factory that creates IPEditorTableCell
 * 
 * 
 * @author Richard Lipka
 *
 */
public class IPEditorTableCellFactory implements Callback<TableColumn<Connection, InetAddress>, IPEditorTableCell>{
	public IPEditorTableCell call(TableColumn<Connection, InetAddress> param) {
		return new IPEditorTableCell();
	}	
}
