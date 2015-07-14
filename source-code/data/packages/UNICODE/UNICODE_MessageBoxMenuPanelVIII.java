package data.packages.UNICODE; //Author(s): Jordan Micah Bennett
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import data.packages.UNICODE.*;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;

public class UNICODE_MessageBoxMenuPanelVIII extends UNICODE_MenuPanel
{
    //constructor
    public UNICODE_MessageBoxMenuPanelVIII ( ArrayList <Object> customComponentList, boolean menuVisibility, int _xCoord, int _yCoord, int _MAXIMUM_BUTTONS, int BUTTON_PROXIMITY, int AXIS_PROXIMITY, String axisDirection, String axisLayoutType, String buttonListDirectory, int buttonWidth, int buttonHeight, Color bgColour, Color buttonOutlineColour, String buttonShapeType, int arcHeight, int arcDepth, int lastButtonChopValue )
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
		}
    }
	
    public void mouseMovedExtendedDefinition ( MouseEvent mEvent )
    {
    }
	
    public void mouseWheelRolledExtendedDefinition ( MouseWheelEvent mwEvent )
    {
    }
}