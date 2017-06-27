package fr.imie.suptodo.launcher;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import fr.imie.suptodo.gui.ConnectionFrame;
import fr.imie.suptodo.model.User;

public class GuiLauncher {
	
	public static Long currentUserId;
	public static User currentUser;

	public GuiLauncher() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("A problem occured while getting the Nimbus Style");
		}
		
		new ConnectionFrame();
	}
	
	public static void main(String[] args) {
		new GuiLauncher();
		        
	}
}

