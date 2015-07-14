import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.Dimension;
import data.packages.UNICODE.*;


public class BUTTON_DOCK_2 extends UNICODE_MenuPanel
{
    //custom components imported
//         value.add ( applicationFrame ); //0
//         value.add ( configurationManager ); //1
//         value.add ( buiField ); //2
//         value.add ( configurationManager.getBufferDimensionFromFile ( ) ); //3
//         value.add ( this ); //4

    public BUTTON_DOCK_2 ( ArrayList <Object> customComponentList, boolean menuVisibility, int _xCoord, int _yCoord, int _MAXIMUM_BUTTONS, int BUTTON_PROXIMITY, int AXIS_PROXIMITY, String axisDirection, String axisLayoutType, String buttonListDirectory, int buttonWidth, int buttonHeight, Color bgColour, Color buttonOutlineColour, String buttonShapeType, int arcHeight, int arcDepth, int lastButtonChopValue )
    {
        super ( customComponentList, menuVisibility, _xCoord, _yCoord, _MAXIMUM_BUTTONS, BUTTON_PROXIMITY, AXIS_PROXIMITY, axisDirection, axisLayoutType, buttonListDirectory, buttonWidth, buttonHeight, bgColour, buttonOutlineColour, buttonShapeType, arcHeight, arcDepth, lastButtonChopValue );
    }
    
    //abstract method
    public void mouseClickDefinition ( MouseEvent mEvent )
    {
        //0.open file/folder
        if ( getMenu ( ).getButtonList ( ).get ( 0 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) ); 
            
            UNICODE_ConfigurationManager configurationManager = ( UNICODE_ConfigurationManager ) getCustomComponentList ( ).get ( 1 );
            UNICODE_BUI_FIELD buiField = ( UNICODE_BUI_FIELD ) getCustomComponentList ( ).get ( 2 );
            ArrayList <UNICODE_BUI_SHAPE> buiList = buiField.getBuiList ( );
            Dimension screenDimension = ( Dimension ) getCustomComponentList ( ).get ( 3 );
            UNICODE_GuiPanel guiPanel = ( UNICODE_GuiPanel ) getCustomComponentList ( ).get ( 4 );
            
            int currentBuiIndex = buiField.getCurrentBuiIndex ( );

            String accessedDirectoryOfCurrentBui = buiList.get ( currentBuiIndex ).getBody ( ).getEntityStream ( );
            
            buiField.getBuiList ( ).get ( currentBuiIndex ).getBody ( ).openRepresentedBuiVersion ( guiPanel, buiField, accessedDirectoryOfCurrentBui, true );
            
            buiField.getDirectoryDisplayer ( ).update ( );

            //show user the name of the bui field just accessed.
            if ( buiField.getStreamEmptyEnquiry ( accessedDirectoryOfCurrentBui ) )
                new UNICODE_MessageBoxWindow ( true, "Accessed folder : " + accessedDirectoryOfCurrentBui, 0.9f, new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 20, 20, 10, 10, -800, 0 );              
        }
        
        //1.toggle properties
        if ( getMenu ( ).getButtonList ( ).get ( 1 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) ); 
            
            UNICODE_BUI_FIELD buiField = ( UNICODE_BUI_FIELD ) getCustomComponentList ( ).get ( 2 );
            ArrayList <UNICODE_BUI_SHAPE> buiList = buiField.getBuiList ( );
            int currentBuiIndex = buiField.getCurrentBuiIndex ( );
            
            String directoryName = "name : " + buiList.get ( currentBuiIndex ).getBody ( ).getRepresentedName ( );
            String directorySize = "size : " + buiList.get ( currentBuiIndex ).getBody ( ).getRepresentedSize ( );
            String directoryHiddenEnquiry = "File hidden? : " + buiList.get ( currentBuiIndex ).getBody ( ).getRepresentedHiddenEnquiry ( );
            String directoryLastModified = "last modified : " + buiList.get ( currentBuiIndex ).getBody ( ).getRepresentedLastModified ( );
            
            String messageBoxDelimiter = "::";
            
            String messageBoxString = directoryName + messageBoxDelimiter + directorySize + messageBoxDelimiter + directoryHiddenEnquiry + messageBoxDelimiter + directoryLastModified;
            
            new UNICODE_MessageBoxWindow ( true, messageBoxString, messageBoxDelimiter, 0.9f, new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 20, 20, 10, 10, -800, 0 );
        }
        
        
        //2.copy file/folder
        if ( getMenu ( ).getButtonList ( ).get ( 2 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) );
        }
        
        
        //3.rename file/folder
        if ( getMenu ( ).getButtonList ( ).get ( 3 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) );
            
            Thread renameEntityThread = new Thread  
            (
                new Runnable ( )
                {
                    public void run ( )
                    {
                    }
                }
            );
            
            UNICODE_MessageBoxWindow window = new UNICODE_MessageBoxWindow ( true, renameEntityThread, 0.9f, new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay - threaded/","rr", 20, 20, 10, 10, -800, 0 );
            
            UNICODE_BUI_FIELD buiField = ( UNICODE_BUI_FIELD ) getCustomComponentList ( ).get ( 2 );
            ArrayList <UNICODE_BUI_SHAPE> buiList = buiField.getBuiList ( );
            int currentBuiIndex = buiField.getCurrentBuiIndex ( );
            
            buiList.get ( currentBuiIndex ).getBody ( ).renameRepresented ( window.getPanel ( ).getTextFieldText ( ) );
        }
        
        
        //4.delete
        if ( getMenu ( ).getButtonList ( ).get ( 4 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) );
            
            UNICODE_BUI_FIELD buiField = ( UNICODE_BUI_FIELD ) getCustomComponentList ( ).get ( 2 );
            ArrayList <UNICODE_BUI_SHAPE> buiList = buiField.getBuiList ( );
            int currentBuiIndex = buiField.getCurrentBuiIndex ( );
            
            buiList.get ( currentBuiIndex ).getBody ( ).deleteRepresented ( );
        }
        
        
        //5.set read only
        if ( getMenu ( ).getButtonList ( ).get ( 5 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) );
            
            UNICODE_BUI_FIELD buiField = ( UNICODE_BUI_FIELD ) getCustomComponentList ( ).get ( 2 );
            ArrayList <UNICODE_BUI_SHAPE> buiList = buiField.getBuiList ( );
            int currentBuiIndex = buiField.getCurrentBuiIndex ( );
            
            buiList.get ( currentBuiIndex ).getBody ( ).setRepresentedReadOnly ( );
            
            new UNICODE_MessageBoxWindow ( true, "Enabled read only mode on node file!", 0.9f, new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/ookay/","rr", 20, 20, 10, 10, -800, 0 );
        }
        
        
        //exit actions panel
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            UNICODE_BUI_FIELD buiField = ( UNICODE_BUI_FIELD ) getCustomComponentList ( ).get ( 2 );
            audioPlayer.playAudio ( "" + getAudioByAlias ( "t" ) ); 
            this.setVisible ( false );
            buiField.disableBuiActionDonutWindow ( );
        }
    }
    
    public void mouseMovedExtendedDefinition ( MouseEvent mEvent )
    {
    }
    
    
    public void mouseWheelRolledExtendedDefinition ( MouseWheelEvent mwEvent )
    {
        int rotationDirection = mwEvent.getWheelRotation ( );
        
        UNICODE_BUI_FIELD buiField = ( UNICODE_BUI_FIELD ) getCustomComponentList ( ).get ( 2 );    
        UNICODE_DonutMenuWindow actionWindow = buiField.getBuiActionWindow ( );
        UNICODE_MenuPanel actionPanel = buiField.getBuiWaveActionDock ( );
        
        if ( rotationDirection < 0 )
            actionPanel.performAxisAnimation ( 70, 180, 0.756f, 0.3f, "clockwise" );
                
        else if ( rotationDirection > 0 )
            actionPanel.performAxisAnimation ( 70, 180, 0.756f, 0.3f, "anti-clockwise" );  
    }
}  