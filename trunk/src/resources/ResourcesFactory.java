package resources;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ResourcesFactory {
	public static Icon getAddIcon(){
		return new ImageIcon("src/resources/add_16.png");
	}
	
	public static Icon getNewIcon(){
		return new ImageIcon("src/resources/page_new.gif");
	}
	
	public static Icon getOpenIcon(){
		return new ImageIcon("src/resources/folder_explore.png");
	}
	
	public static Icon getSaveIcon(){
		return new ImageIcon("src/resources/table_save.png");
	}
	
	public static Icon getRemoveIcon(){
		return new ImageIcon("src/resources/delete.png");
	}
	
	public static Icon getEditIcon(){
		return new ImageIcon("src/resources/application_form_edit.png");
	}
	
	public static Icon getGenerateIcon(){
		return new ImageIcon("src/resources/mario_32.png");
	}
}
