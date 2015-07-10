package data.packages.UNICODE; //Author(s): Jordan Micah Bennett
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import data.packages.UNICODE.*;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;

//takes two threads, but does not exit the window when buttons are toggled.
//used for word celocity wizard field descriptor one, where I needed two thread
//capable buttons, and a Jlabel.
public class UNICODE_MessageBoxMenuPanelX extends UNICODE_MenuPanel
{
    //constructor
    public UNICODE_MessageBoxMenuPanelX ( ArrayList <Object> customComponentList, boolean menuVisibility, int _xCoord, int _yCoord, int _MAXIMUM_BUTTONS, int BUTTON_PROXIMITY, int AXIS_PROXIMITY, String axisDirection, String axisLayoutType, String buttonListDirectory, int buttonWidth, int buttonHeight, Color bgColour, Color buttonOutlineColour, String buttonShapeType, int arcHeight, int arcDepth, int lastButtonChopValue )
    {
        super ( customComponentList, menuVisibility, _xCoord, _yCoord, _MAXIMUM_BUTTONS, BUTTON_PROXIMITY, AXIS_PROXIMITY, axisDirection, axisLayoutType, buttonListDirectory, buttonWidth, buttonHeight, bgColour, buttonOutlineColour, buttonShapeType, arcHeight, arcDepth, lastButtonChopValue );
    }
    
    public void mouseClickDefinition ( MouseEvent mEvent )
    {
        if ( getMenu ( ).getButtonList ( ).get ( 0 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
			audioPlayer.playAudio ( getAudioByAlias ( "e" ) );
			Thread thread = ( Thread ) getCustomComponentList ( ).get ( 0 );
			thread.start ( );
			
			UNICODE_MessageBoxPanel messageBoxPanel = ( UNICODE_MessageBoxPanel ) getCustomComponentList ( ).get ( 1 );
			JFrame frame = ( JFrame ) getCustomComponentList ( ).get ( 2 );
			messageBoxPanel.setVisible ( false );
			frame.setVisible ( false );
		}
        if ( getMenu ( ).getButtonList ( ).get ( 2 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
			UNICODE_MessageBoxPanel messageBoxPanel = ( UNICODE_MessageBoxPanel ) getCustomComponentList ( ).get ( 1 );
			JFrame frame = ( JFrame ) getCustomComponentList ( ).get ( 2 );
			messageBoxPanel.setVisible ( false );
			frame.setVisible ( false );
		}
    }
	
    public void mouseMovedExtendedDefinition ( MouseEvent mEvent )
    {
    }
	
    public void mouseWheelRolledExtendedDefinition ( MouseWheelEvent mwEvent )
    {
    }
}