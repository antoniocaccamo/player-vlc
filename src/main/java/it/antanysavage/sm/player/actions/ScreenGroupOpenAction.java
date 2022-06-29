package it.antanysavage.sm.player.actions;

import org.eclipse.jface.action.Action;

public class ScreenGroupOpenAction extends Action {
//
//	private Player player;
//
//	public ScreenGroupOpenAction(Player player) {
//		super("ScreenGroup");
//		this.player = player;
//	}
//
//	@Override
//	public void run() {
//
//		try {
//			FileDialog fileDialog = new FileDialog( Display.getCurrent().getActiveShell(), SWT.OPEN );
//			fileDialog.setFilterPath(Player.getPlayerSettingPath());
//			fileDialog.setFilterExtensions(new String[] { ScreenGroupSetter.FILE_EXT_FILTER });
//			String path = fileDialog.open();
//			if ( ! ConfigurationUtils.isAnEmptyString( path )) {
//				File f = new File(path);
//				Configuration cnf = ConfigurationUtils.read(f); 
//				player.enableScreenGroup(cnf);				
//			}
//		} catch (Exception e) {
//			MessageDialog.openError(Display.getDefault().getActiveShell(), 
//					LocaleManager.TITLE, 					
//					"Error : " + e.getLocalizedMessage()
//					);
//			System.exit(1);
//		}						
//	}

}
