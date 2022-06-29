package it.antanysavage.sm.player.actions;

import it.antanysavage.sm.player.Player;
import org.apache.commons.lang.StringUtils;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;


public class ComputerNameAction extends Action {
    
    public ComputerNameAction() {
        super( "Computer Name" , AS_PUSH_BUTTON);
    }
    
    public void run() {
        InputDialog dlg = new InputDialog(
                Display.getCurrent().getActiveShell(),
                "Computer Name", "Enter a name ..",  Player.COMPUTER,  
                new IInputValidator() {
                    @Override
                    public String isValid(String string) {
                        if  ( StringUtils.isNotEmpty(string) )
                            return null;
                        return string;
                    }
                }
        );
        if (dlg.open() == Window.OK) {
            // User clicked OK; update the label with the input
            Player.COMPUTER =  dlg.getValue();
        }
    }
}
