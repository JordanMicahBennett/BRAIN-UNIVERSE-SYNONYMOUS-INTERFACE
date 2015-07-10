import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.text.NumberFormat;
import data.packages.UNICODE.*;


public class BUTTON_DOCK_0 extends UNICODE_MenuPanel
{
    //custom components imported
//         value.add ( applicationFrame ); //0
//         value.add ( configurationManager ); //1
//         value.add ( buiField ); //2
//         value.add ( antiAliasController ); //3
//         value.add ( this ); //4

    boolean clusterModeEnquiry = false;

    public BUTTON_DOCK_0 ( ArrayList <Object> customComponentList, boolean menuVisibility, int _xCoord, int _yCoord, int _MAXIMUM_BUTTONS, int BUTTON_PROXIMITY, int AXIS_PROXIMITY, String axisDirection, String axisLayoutType, String buttonListDirectory, int buttonWidth, int buttonHeight, Color bgColour, Color buttonOutlineColour, String buttonShapeType, int arcHeight, int arcDepth, int lastButtonChopValue )
    {
        super ( customComponentList, menuVisibility, _xCoord, _yCoord, _MAXIMUM_BUTTONS, BUTTON_PROXIMITY, AXIS_PROXIMITY, axisDirection, axisLayoutType, buttonListDirectory, buttonWidth, buttonHeight, bgColour, buttonOutlineColour, buttonShapeType, arcHeight, arcDepth, lastButtonChopValue );
    }
    
    //abstract method
    public void mouseClickDefinition ( MouseEvent mEvent )
    { 
        //item 0 - return to previous directory.
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON - 11 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) );      
            
            UNICODE_ConfigurationManager configurationManager = ( UNICODE_ConfigurationManager ) getCustomComponentList ( ).get ( 1 );
            UNICODE_BUI_FIELD buiField = ( UNICODE_BUI_FIELD ) getCustomComponentList ( ).get ( 2 );
            ArrayList <UNICODE_BUI_SHAPE> buiList = buiField.getBuiList ( );
            UNICODE_GuiPanel guiPanel = ( UNICODE_GuiPanel ) getCustomComponentList ( ).get ( 4 );
            UNICODE_DirectoryDisplayer directoryDisplayer = buiField.getDirectoryDisplayer ( );
            String rootFolderName = directoryDisplayer.getRootFolderName ( );
            
            int currentBuiIndex = buiField.getCurrentBuiIndex ( );
            
                
            String previousDirectory = "";
            if ( !buiField.getFirstLetterSearchModeEnquiry ( ) ) //test to see if search mode was toggled. 
            {
                //If not, then the privious directory is used to mutate bui field for regeneration of buis on screen.
                buiField.decFieldAccessCounter ( );
                previousDirectory = ( String ) buiField.getPreviousFieldStreamList ( ).get ( buiField.getFieldAccessCounter ( ) );
                buiField.setForwardNavigationActionEnquiry ( true );
            }
            else if ( buiField.getFirstLetterSearchModeEnquiry ( ) )
            {
                //else, the current field directory will be utilized, since we really haven't moved beyond the current directory.
                previousDirectory = ( String ) buiField.getFieldStream  ( );
                buiField.setFirstLetterSearchModeEnquiry ( false );
                buiField.setForwardNavigationActionEnquiry ( false );
            }
            
            buiField.getBuiList ( ).get ( currentBuiIndex ).getBody ( ).openRepresentedBuiVersion ( guiPanel, buiField, previousDirectory, false );
            buiField.updateDirectoryDisplayer ( );
            
            //show user the name of the bui field just accessed.
            new UNICODE_MessageBoxWindow ( true, "Returned to : " + previousDirectory.replace ( "C:", rootFolderName ), 0.9f, new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 20, 20, 10, 10, 0, 0 );    
            
            
            if ( buiField.getForwardNavigationActionEnquiry ( ) ) //test to see if search mode was toggled. 
            {
                //remove last stored previous field stream list
                buiField.shrinkPreviousFieldStreamList ( );
            }
        }
        
        //item 1 - enable/disable bui field descriptor
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON - 10 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) ); 
            
            UNICODE_ConfigurationManager configurationManager = ( UNICODE_ConfigurationManager ) getCustomComponentList ( ).get ( 1 );
            
            //establish main dock
            final UNICODE_BUI_FIELD buiField = ( UNICODE_BUI_FIELD ) getCustomComponentList ( ).get ( 2 );
            
            //toggle/de-toggle bui field descriptors
            buiField.toggleDescriptionModeControl ( audioPlayer, "descriptionViewActivated.wav", "descriptionViewCanceled.wav", configurationManager );
        }
        
        //item 2 - search for folders/fles that commence with letter OR a particular folder/file via a keyword
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON - 9 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) ); 
            
            //establish main dock
            UNICODE_ConfigurationManager configurationManager = ( UNICODE_ConfigurationManager ) getCustomComponentList ( ).get ( 1 );
            UNICODE_BUI_FIELD buiField = ( UNICODE_BUI_FIELD ) getCustomComponentList ( ).get ( 2 );
            UNICODE_GuiPanel guiPanel = ( UNICODE_GuiPanel ) getCustomComponentList ( ).get ( 4 );
            UNICODE_MessageBoxWindow searchBox = ( UNICODE_MessageBoxWindow ) getCustomComponentList ( ).get ( 5 );
            ArrayList <UNICODE_BUI_SHAPE> buiList = buiField.getBuiList ( );
            
            //tell Bui UI that a first search has been toggled - this is to guide back functionality as to how bui field re-generation
            //should occur
            buiField.setFirstLetterSearchModeEnquiry ( true );
            
            //show the search box
            int searchBoxX = ( ( ( int ) configurationManager.getBufferDimensionFromFile ( ).getWidth ( ) / 2 ) - ( searchBox.getWidth ( ) / 2 ) ) + 12;
            int searchBoxY = 70 + searchBox.getHeight ( ) / 2; //80 represents the size of the buttonDock0
            searchBox.setVisible ( true );
            searchBox.setBounds ( searchBoxX, searchBoxY, searchBox.getWidth ( ), searchBox.getHeight ( ) );
            
            String searchItem = searchBox.getPanel ( ).getTextFieldText ( );
            
            //increment the search button click index each time button is toggled
            buiField.incSearchButtonClickIndex ( );
            
            int aModeSearchNameCardinality = buiField.generateStreamFolderSearchNamesSearchModeA ( buiField.getFieldStream ( ), searchItem ).size ( );
            int bModeSearchNameCardinality = buiField.generateStreamFolderSearchNamesSearchModeB ( buiField.getFieldStream ( ), searchItem ).size ( );
            int cModeSearchNameCardinality = buiField.generateStreamFolderSearchNamesSearchModeC ( buiField.getFieldStream ( ) ).size ( );
            int dModeSearchNameCardinality = buiField.generateStreamFolderSearchNamesSearchModeD ( buiField.getFieldStream ( ) ).size ( );
            
            String searchType = "", resultEnding = "";
            
            //only perform the re-generation / field mutation 'openRepresentedBuiVersionSearchModeA' when there is a aggerable searchItem.
            //agreeable implies that it is not empty.
            if ( ( ! searchItem.equals ( "" ) ) && ( buiField.getSearchButtonClickIndex ( ) > 1 ) ) 
            {
                if ( !searchItem.equals ( "infoverses:" ) && !searchItem.equals ( "infowaves:" ) )
                {
                    //check if any letters match the search, if so, generate bui field, else return message of null search result.
                    //only perform the re-generation / field mutation 'openRepresentedBuiVersionSearchModeA' when there is a aggerable searchItem.
                    //agreeable implies that it is not empty.
                    if ( ( !searchItem.equals ( "" ) ) && ( buiField.getSearchButtonClickIndex ( ) > 1 ) ) 
                    {
                        if ( ( aModeSearchNameCardinality == 1 ) || ( bModeSearchNameCardinality == 1 ) )
                            resultEnding = "";
                        else
                            resultEnding = "s";
                        
                        //there are two search types.
                        //1.Commencing letter search
                        //2.Keyword search.
                        if ( searchItem.length ( ) == 1 )
                        {
                            searchType = "letter";
                            
                            if ( aModeSearchNameCardinality > 0 )
                            {
                                //mutate the bui field to reflect search results...the value passed her
                                //the 0 index in 'buiField.getBuiList ( ).get ( 0 )' is trivial. Any valid bui field object may be used to perform the search function.
                                //remember, we're not navigating based on an buis eneityStream object, but rather mutating the bui field to display search results.
                                buiField.getBuiList ( ).get ( 0 ).getBody ( ).openRepresentedBuiVersionSearchModeA ( guiPanel, buiField, buiField.getFieldStream ( ), searchItem );
                                //show user success message
                                new UNICODE_MessageBoxWindow ( true, "" + buiField.getStreamFolderFileList ( ).size ( ) + " result" + resultEnding + " discovered for " + searchType + " '" + searchItem + "'", 0.9f, new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 20, 20, 10, 10, 0, 0 );   
                            }
                            else
                                new UNICODE_MessageBoxWindow ( true, "no result discovered for " + searchType + " '" + searchItem + "'", 0.9f, new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 20, 20, 10, 10, 0, 0 );        
                        }
                        
                        else if ( searchItem.length ( ) > 1 )
                        {
                            searchType = "key word";
                            
                            if ( bModeSearchNameCardinality > 0 )
                            {
                                buiField.getBuiList ( ).get ( 0 ).getBody ( ).openRepresentedBuiVersionSearchModeB ( guiPanel, buiField, buiField.getFieldStream ( ), searchItem );
                                //show user success message
                                new UNICODE_MessageBoxWindow ( true, "" + buiField.getStreamFolderFileList ( ).size ( ) + " result" + resultEnding + " discovered for " + searchType + " '" + searchItem + "'", 0.9f, new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 20, 20, 10, 10, 0, 0 );   
                            }
                            else
                                new UNICODE_MessageBoxWindow ( true, "no result discovered for " + searchType + " '" + searchItem + "'", 0.9f, new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 20, 20, 10, 10, 0, 0 );        
                        }
                    }
                }
                else
                {
                    //determine result ending
                    if ( ( cModeSearchNameCardinality == 1 ) || ( dModeSearchNameCardinality == 1 ) )
                        resultEnding = "";
                    else
                        resultEnding = "s";
                            
                    //determine if this is a folder search
                    if ( searchItem.equals ( "infoverses:" ) )
                    {
                        if ( cModeSearchNameCardinality > 0 )
                        {
                            //state search type
                            searchType = "infoverse";
                            //generate relevant bui field
                            buiField.getBuiList ( ).get ( 0 ).getBody ( ).openRepresentedBuiVersionSearchModeC ( guiPanel, buiField, buiField.getFieldStream ( ) );
                            //show user success message
                            new UNICODE_MessageBoxWindow ( true, "" + buiField.getStreamFolderFileList ( ).size ( ) + " result" + resultEnding + " discovered for " + searchType + " search.", 0.9f, new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 20, 20, 10, 10, 0, 0 );   
                        }
                        else
                            new UNICODE_MessageBoxWindow ( true, "no result discovered for " + searchType + " search.", 0.9f, new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 20, 20, 10, 10, 0, 0 );        
                    }
                    //determine if this is a file search
                    else if ( searchItem.equals ( "infowaves:" ) )
                    {
                        if ( dModeSearchNameCardinality > 0 )
                        {
                            //state search type
                            searchType = "infowave";
                            //generate relevant bui field
                            buiField.getBuiList ( ).get ( 0 ).getBody ( ).openRepresentedBuiVersionSearchModeD ( guiPanel, buiField, buiField.getFieldStream ( ) );
                            //show user success message
                            new UNICODE_MessageBoxWindow ( true, "" + buiField.getStreamFolderFileList ( ).size ( ) + " result" + resultEnding + " discovered for " + searchType + " search.", 0.9f, new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 20, 20, 10, 10, 0, 0 );   
                        }
                        else
                            new UNICODE_MessageBoxWindow ( true, "no result discovered for " + searchType + " search.", 0.9f, new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 20, 20, 10, 10, 0, 0 );        
                    }
                }
            }
            else
            {
                //if the search box is empty, but the click index has exceeded 0, then this probably implies the user wants the search box gone.
                if ( ( searchItem.equals ( "" ) ) && ( buiField.getSearchButtonClickIndex ( ) > 1 ) )
                {
                    searchBox.setBounds ( -100, -100, searchBox.getWidth ( ), searchBox.getHeight ( ) );
                    buiField.resetSearchButtonClickIndex ( );
                }
            }
        }
            
            
            
        
        //item 3 - settings dock
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON - 8 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) );
            
            //establish customized components
            final JFrame applicationFrame = ( JFrame ) getCustomComponentList ( ).get ( 0 );
            final UNICODE_ConfigurationManager configurationManager = ( UNICODE_ConfigurationManager ) getCustomComponentList ( ).get ( 1 );
            final UNICODE_BUI_FIELD buiField = ( UNICODE_BUI_FIELD ) getCustomComponentList ( ).get ( 2 );
            final UNICODE_AntiAliasingController antiAliasingController = ( UNICODE_AntiAliasingController ) getCustomComponentList ( ).get ( 3 );

            //establish lineVisibilityControlThread thread
            Thread settingsThread0 = new Thread
            (
               new Runnable ( )
               {
                   public void run ( )
                   {
                       buiField.toggleLineVisibilityControl ( );
                   }
               }
            );
            
            //establish antiAliasingControlThread thread
            Thread settingsThread1 = new Thread
            (
               new Runnable ( )
               {
                   public void run ( )
                   {
                       if ( antiAliasingController.getRenderingAnswer ( ).equals ( "on" ) )
                       {
                           antiAliasingController.setRenderingAnswer ( "off" );
                           audioPlayer.playAudio ( "aliasingCanceledVoice.wav" );
                       }
                       else if ( antiAliasingController.getRenderingAnswer ( ).equals ( "off" ) )
                       {
                           antiAliasingController.setRenderingAnswer ( "on" );
                           audioPlayer.playAudio ( "aliasingEnabledVoice.wav" );
                       }
                           
                       //update config file
                       configurationManager.updateAntiAliasing ( antiAliasingController );
                   }
               }
            );
            
            //establish applicationMinimizationThread 
            Thread settingsThread2 = new Thread
            (
                new Runnable ( )
                {
                    public void run ( )
                    {
    
                        audioPlayer.playAudio ( "minimization success.wav" );
                        UNICODE_HideMechanism2 hider = new UNICODE_HideMechanism2 ( "Brain Universe-Synonymous Interface","B.U.I. restoration options", "data/images/trayIcon.png", false, applicationFrame, "restoration success.wav", "Do you wish to restore B.U.I.?", "data/images/all/message box/unhide/", "rr", 0.9f, new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ), 20, 20, 10, 10, 0, 8, true );
    
                        hider.triggerFrameResized ( applicationFrame );
                    }
                }
            );
            new UNICODE_MessageBoxWindow ( true, settingsThread0, settingsThread1, settingsThread2, "LINES ~ ALIASING ~ MINIMIZE", 0.9f,  new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/settings/","rr", 20, 20, 10, 10, 0, 0 );
        }
        
        //param bubble
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON - 7 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            final UNICODE_BUI_FIELD buiField = ( UNICODE_BUI_FIELD ) getCustomComponentList ( ).get ( 2 );
            
            buiField.toggleParameterBubbleEnableEnquiry ( );
            
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) ); 
        }
        
        //param bubble
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON - 6 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            UNICODE_BUI_FIELD buiField = ( UNICODE_BUI_FIELD ) getCustomComponentList ( ).get ( 2 );
            UNICODE_GuiPanel guiPanel = ( UNICODE_GuiPanel ) getCustomComponentList ( ).get ( 4 );
            
            String homeFolderStream = buiField.getHomeStream ( );
            
            buiField.reInitializeField ( homeFolderStream, false );
            guiPanel.repaint ( );
            
            audioPlayer.playAudio ( "interface regeneration.wav" ); 
        }
        
        //AI - alert, infotainment selection. (in addition to default mode)
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON - 5 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            final UNICODE_BUI_FIELD buiField = ( UNICODE_BUI_FIELD ) getCustomComponentList ( ).get ( 2 );
            final UNICODE_GuiPanel guiPanel = ( UNICODE_GuiPanel ) getCustomComponentList ( ).get ( 4 );
            final UNICODE_ConfigurationManager configurationManager = ( UNICODE_ConfigurationManager ) getCustomComponentList ( ).get ( 1 );
            final UNICODE_CoreCurrentGlowColourController coreCurrentGlowColourController = ( UNICODE_CoreCurrentGlowColourController ) getCustomComponentList ( ).get ( 6 );
            final ArrayList <UNICODE_BUI_SHAPE> buiList = buiField.getBuiList ( );
            final UNICODE_BinaryClock binaryClockDock = ( UNICODE_BinaryClock ) getCustomComponentList ( ).get ( 7 );
            
            
            //establish lineVisibilityControlThread thread
            Thread aiSettingsThread0 = new Thread
            (
               new Runnable ( )
               {
                   public void run ( )
                   {
                        //reset glow index
                        buiField.setCoreGlowIndex ( 0 );
                        
                        //reset physical glow colours in cores per bui entity
                        for ( int i = 0; i < buiList.size ( ); i ++ )
                            buiList.get ( i ).resetGlow ( configurationManager );


                        //reflect changes by updating surfaces
                        binaryClockDock.resetColours ( buiField.getGlowCoreColour ( ) );
                        buiField.setContentLocationIndicatorColour ( buiField.getGlowCoreColour ( ) );
                        buiField.repaint ( );
                        guiPanel.repaint ( );
                        
                        //update current glow controller, and by extension, its setting in configuration file
                        coreCurrentGlowColourController.setValue ( buiField.getGlowCoreColour ( ) );
                        configurationManager.updateCoreCurrentGlowColourController ( coreCurrentGlowColourController );
                        configurationManager.updateConfigFile ( );
                        
                        //play appropriate notice message
                        audioPlayer.playAudio ( "alert mode.wav" );
                   }
               }
            );
            
            //establish antiAliasingControlThread thread
            Thread aiSettingsThread1 = new Thread
            (
               new Runnable ( )
               {
                   public void run ( )
                   {
                        //reset glow index
                        buiField.setCoreGlowIndex ( 1 );
                        
                        //reset physical glow colours in cores per bui entity
                        for ( int i = 0; i < buiList.size ( ); i ++ )
                            buiList.get ( i ).resetGlow ( configurationManager );
                            

                        //reflect changes by updating surfaces
                        binaryClockDock.resetColours ( buiField.getGlowCoreColour ( ) );
                        buiField.setContentLocationIndicatorColour ( buiField.getGlowCoreColour ( ) );
                        buiField.repaint ( );
                        guiPanel.repaint ( );
                        
                        //update current glow controller, and by extension, its setting in configuration file
                        coreCurrentGlowColourController.setValue ( buiField.getGlowCoreColour ( ) );
                        configurationManager.updateCoreCurrentGlowColourController ( coreCurrentGlowColourController );
                        configurationManager.updateConfigFile ( );
                        
                        //play appropriate notice message
                        audioPlayer.playAudio ( "infotainment mode.wav" );
                   }
               }
            );
            
            //establish applicationMinimizationThread 
            Thread aiSettingsThread2 = new Thread
            (
                new Runnable ( )
                {
                    public void run ( )
                    {
                        //reset glow index
                        buiField.setCoreGlowIndex ( 2 );
                        
                        //reset physical glow colours in cores per bui entity
                        for ( int i = 0; i < buiList.size ( ); i ++ )
                            buiList.get ( i ).resetGlow ( configurationManager );
                            

                        //reflect changes by updating surfaces
                        binaryClockDock.resetColours ( buiField.getGlowCoreColour ( ) );
                        buiField.setContentLocationIndicatorColour ( buiField.getGlowCoreColour ( ) );
                        buiField.repaint ( );
                        guiPanel.repaint ( );
                        
                        //update current glow controller, and by extension, its setting in configuration file
                        coreCurrentGlowColourController.setValue ( buiField.getGlowCoreColour ( ) );
                        configurationManager.updateCoreCurrentGlowColourController ( coreCurrentGlowColourController );
                        configurationManager.updateConfigFile ( );
                        
                        //play appropriate notice message
                        audioPlayer.playAudio ( "default mode.wav" );
                    }
                }
            );
            
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) ); 
            new UNICODE_MessageBoxWindow ( true, aiSettingsThread0, aiSettingsThread1, aiSettingsThread2, "ALERT ~ INFOTAINMENT ~ default", 0.9f,  new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/ai - settings/","rr", 20, 20, 10, 10, 0, 0 );
        }
        
        
        //enable/disable uni-cortex connect
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON - 4 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            UNICODE_ConfigurationManager configurationManager = ( UNICODE_ConfigurationManager ) getCustomComponentList ( ).get ( 1 );
            UNICODE_BlankFrame connectFrame = ( UNICODE_BlankFrame ) getCustomComponentList ( ).get ( 8 );
            UNICODE_ConveniencePack conveniencePack = new UNICODE_ConveniencePack ( );
     
            //connectFrame.changePaint (  configurationManager.getCurrentGlowCoreColourFromFile ( ), Color.black );
            connectFrame.toggleVisiblilty ( );
            
            audioPlayer.playAudio ( "connect.wav" ); 
        }
        
        //enable/disable cluster mode
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON - 3 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            new UNICODE_MessageBoxWindow ( true, "no additional machines discovered on network!", 0.9f, new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 20, 20, 10, 10, 0, 0 );
        
            audioPlayer.playAudio ( "cluster mode.wav" ); 
        }
        
        //generate time wave field
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON - 2 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) ); 
            final UNICODE_BUI_FIELD buiField = ( UNICODE_BUI_FIELD ) getCustomComponentList ( ).get ( 2 );
            buiField.initializeTimeWaveField ( );
            audioPlayer.playAudio ( "timeWaveActivatedVoice.wav" ); 
        }
        
        //generate size wave field
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON - 1 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) ); 
            final UNICODE_BUI_FIELD buiField = ( UNICODE_BUI_FIELD ) getCustomComponentList ( ).get ( 2 );
            buiField.initializeSizeWaveField ( );
            audioPlayer.playAudio ( "sizeWaveActivatedVoice.wav" ); 
        }
        
        //quit software
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            audioPlayer.playAudio ( "" + getAudioByAlias ( "t" ) ); 
            System.exit ( 0 );
        }
        
        repaint ( );
    }

    public void mouseMovedExtendedDefinition ( MouseEvent mEvent )
    {
    }
    
    public void mouseWheelRolledExtendedDefinition ( MouseWheelEvent mwEvent )
    {
    }
}  