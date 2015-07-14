import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.BoxLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

import data.packages.UNICODE.*;
import data.packages.UNICORTEX.*;
import data.packages.JHLABS.*;

public class UNICODE_GuiPanel extends JPanel
{

    //attributes
        //establish parent frame
        private JFrame applicationFrame = null;
        
        //establish uni-code (tm) managers
            //establish file manager
            private UNICODE_ConfigurationManager configurationManager = new UNICODE_ConfigurationManager ( "data/config/CONFIG.ini" );
            
    //establish uni-code (tm) controllers
            //establish opacity manager
            private UNICODE_OpacityController opacityController = new UNICODE_OpacityController ( ); 
            //establish rendering hint to install anti-aliasing
            private UNICODE_AntiAliasingController antiAliasController = new UNICODE_AntiAliasingController ( configurationManager.getAntiAliasingStateFromFile ( ) );
            //establish Buffer Dimension Controller
            private UNICODE_BufferDimensionController bufferDimensionController = new UNICODE_BufferDimensionController ( );
            //establish Description View Controller
            private UNICODE_DescriptionViewController descriptionViewController = new UNICODE_DescriptionViewController ( configurationManager.getDescriptionViewAnswerFromFile ( ) );
            //current glow colour controller
            private UNICODE_CoreCurrentGlowColourController coreCurrentGlowColourController = new UNICODE_CoreCurrentGlowColourController ( configurationManager.getCurrentGlowCoreColourFromFile ( ) );
            
    //establish menus and related requirments  
        //dock controller
        private UNICODE_DockController dockController = null;    
        //docks
            //dock container
            private JPanel dockContainer = new JPanel ( );
            //main dock
            private UNICODE_BUI_FIELD buiField = null;
            //button docks and associated components
            private BUTTON_DOCK_0 buttonDock0 = null;
            private BUTTON_DOCK_1 buttonVerseDock = null;
            private BUTTON_DOCK_2 buttonWaveDock = null;
            
            private UNICODE_DonutMenuWindow donutMenuWindow = null;
            private UNICODE_MessageBoxWindow searchBox = null;
            private UNICODE_BinaryClock binaryClock = null;
            private UNICODE_LunosClockFrame lunosClock = null;
   
    //establish audio player
    private UNICODE_AudioPlayer audioPlayer = new UNICODE_AudioPlayer ( );

    //establish blank frame which will pass our customized abstract blank panel to.
    private UNICODE_BlankFrame connectFrame = null;
    //constructor
    public UNICODE_GuiPanel ( JFrame _applicationFrame, int directoryDisplayerVisualDisplayLimit )
    {
        //initialise seacrh box
        searchBox = new UNICODE_MessageBoxWindow ( false, 0.8f, Color.white, Color.white, Color.white, Color.black, 200, 30, true );

        
        //establish parent frame
        applicationFrame = _applicationFrame;
        
        //establish background colour of this panel
        setBackground ( configurationManager.getColourFromFile ( ) );
        
        
        //ADJUST BUFFER DIMENSION CONFIG WRT USER SCREEN
            //get user screen size
            int userScreenWidth = ( int ) java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( ).getWidth ( );
            int userScreenHeight = ( int ) java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( ).getHeight ( ); 
            
            //update controller value wrt user specifications
            bufferDimensionController.setBufferDimensionString ( "" + userScreenWidth + "," + userScreenHeight );    
            
            //updateconfigurationManager's buffer controller value
            configurationManager.updateBufferDimensionController ( bufferDimensionController );
            
            //update config file wrt buffer controller value
            configurationManager.updateConfigFile ( );
        
        
        //establish docks
            //esetablish colours that will influence Bui field component colours
            Color BuiFieldComponentColours [ ] =
            {
                 configurationManager.getConnectorColour ( ), //0
                 configurationManager.getConnectorGlowColour ( ), //1
                 configurationManager.getDefaultModeBuiFolderColour ( ), //2
                 configurationManager.getDefaultModeBuiFileColour ( ), //3
                 configurationManager.getDescriptionModeBuiFolderColour ( ), //4
                 configurationManager.getDescriptionModeBuiFileColour ( ), //5
                 configurationManager.getDescriptionModeFolderFontColour ( ), //6
                 configurationManager.getDescriptionModeFileFontColour ( ), //7
                 configurationManager.getContentLocationIndicatorColourFromFile ( ), //8
                 configurationManager.getDirectoryIndicatorBackgroundColourFromFile ( ), //9
                 configurationManager.getDirectoryIndicatorForegroundColourFromFile ( ), //10
                 configurationManager.getBinaryClockPanelColour ( ), //11
                 configurationManager.getBinaryClockBaseColour ( ), //12
                 configurationManager.getBinaryClockActiveColour ( ), //13
                 configurationManager.getBinaryClockInactiveColour ( ), //14
                 configurationManager.getCurrentGlowCoreColourFromFile  ( ) //15
            };

            //establish blank frame, which will hold a customized blank panel, with established un-cortex connect data settings.

            connectFrame = new UNICODE_BlankFrame ( new DOCK ( UNICODE_FadePaintDirections.UPWARDANDRIGHTWARD, new Color ( 245, 245, 245, 0 ), new Color ( 255, 255, 255, 0 ), ( int ) configurationManager.getBufferDimensionFromFile ( ).getWidth ( ) / 2 - 100, ( int ) configurationManager.getBufferDimensionFromFile ( ).getHeight ( ) - 46, 30 ), .5f, 46, 46, ( int ) configurationManager.getBufferDimensionFromFile ( ).getWidth ( ) / 2 - 100, ( int ) configurationManager.getBufferDimensionFromFile ( ).getHeight ( ) - 46, false );
            //hide connect frame, as we don't need it now...
            connectFrame.hide ( );
            
            //establish main dock
            buiField = new UNICODE_BUI_FIELD ( searchBox, configurationManager, configurationManager.getFontNameFromFile ( ), 20.0, 0.1, configurationManager.getDescriptionViewAnswerFromFile ( ), 8, 8, configurationManager.getNodeSpatialMuliplierFromFile ( ), configurationManager.getScrollRateFromFile ( ), configurationManager.getMaximumScrollDurationFromFile ( ), configurationManager.getHomeFolderStringFromFile ( ), false, configurationManager.getBufferDimensionFromFile ( ), configurationManager.getFolderIndicatorMultiplier ( ), descriptionViewController, BuiFieldComponentColours [ 0 ], BuiFieldComponentColours [ 1 ], BuiFieldComponentColours [ 2 ], BuiFieldComponentColours [ 3 ], BuiFieldComponentColours [ 4 ], BuiFieldComponentColours [ 5 ], BuiFieldComponentColours [ 6 ], BuiFieldComponentColours [ 7 ], 45, 45, configurationManager.getBuiDescriptorOpacityFromFile ( ), configurationManager.getConnectorThicknessFromFile ( ), configurationManager.getConnectorGlowThicknessFromFile ( ), configurationManager.getConnectorGlowQualityMultipler ( ), configurationManager.getConnectorGlowBrightness ( ), BuiFieldComponentColours [ 8 ], BuiFieldComponentColours [ 9 ], BuiFieldComponentColours [ 10 ], 40, 6, 26, directoryDisplayerVisualDisplayLimit, configurationManager.getLineRendererEntityLength ( ), configurationManager.getLineRendererEntitySpatialDistance ( ), configurationManager.getCameraDisplayAnswerFromFile ( ), configurationManager.getParameterBubbleHighlighterColourFromFile ( ), configurationManager.getParameterBubbleColourFromFile ( ), configurationManager.getFileAgeReflectionAnswerFromFile ( ), configurationManager.getGlowSpanPercentileFromFile ( ), configurationManager.getGlowIntensityLevelFromFile ( ), configurationManager.getGlowMaximumIntensityPercentileFromFile ( ), BuiFieldComponentColours [ 15 ], configurationManager.getMaximalInfoverseBufferDivisorUsageAnswerFromFile ( ), configurationManager.getMaximalInfoverseBufferDivisorFromFile ( ), 20, 20, 10, 10, 0, 8, new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 245, 245, 245 ), new Color ( 170, 170, 170 ) );
           
            //establish binary clock
            binaryClock = new UNICODE_BinaryClock ( configurationManager, directoryDisplayerVisualDisplayLimit, 56, BuiFieldComponentColours [ 11 ], BuiFieldComponentColours [ 12 ], buiField.getGlowCoreColour ( ), BuiFieldComponentColours [ 14 ], 1, configurationManager.getBinaryClockElementSizeFromFile ( ), Color.white, configurationManager.getFontNameFromFile ( ), 200, 50, 26.0f, "on" );
            
            //establish button dock
            buttonDock0 = new BUTTON_DOCK_0 ( generatedCustomComponentListbuttonDock0 ( ), true, ( int ) configurationManager.getBufferDimensionFromFile ( ).getWidth ( ) - ( 20 * 12 + 10 * 12 ), 20, 12, 90, 1220, "clockwise", "horizontal", "data/images/main menu/", 20, 20, configurationManager.getColourFromFile ( ), new Color ( 245, 245, 245 ), "rr", 10, 10, 0 );
              
            //buttonDock1 = new BUTTON_DOCK_1 ( generatedCustomComponentListbuttonDock1 ( ), true, 120, 200, 7, 8, 40, "clockwise", "circular", "data/images/Bui menu/", 20, 20, configurationManager.getColourFromFile ( ), "e", 0, 0, 4 );
            buttonVerseDock = new BUTTON_DOCK_1 ( generatedCustomComponentListbuttonVerseDock ( ), true, 90, 140, 7, 8, 40, "clockwise", "circular", "data/images/verse menu/", 20, 20, new Color ( 245, 245, 245 ),  new Color ( 245, 245, 245 ), "e", 0, 0, 4 );
            buttonWaveDock = new BUTTON_DOCK_2 ( generatedCustomComponentListbuttonWaveDock ( ), true, 90, 140, 7, 8, 40, "clockwise", "circular", "data/images/wave menu/", 20, 20, new Color ( 0, 0, 0 ),  new Color ( 0, 0, 0 ), "e", 0, 0, 4 );
            
            
            donutMenuWindow = new UNICODE_DonutMenuWindow ( buttonVerseDock, buttonWaveDock, 300, 200, 110, 110, 50, 50, configurationManager.getBuiDescriptorOpacityFromFile ( ) );
            donutMenuWindow.setBackground ( new Color ( 245, 245, 245 ) );
            //establish Bui field for Bui action dock
            buiField.setBuiVerseActionDock ( buttonVerseDock );
            buiField.setBuiWaveActionDock ( buttonWaveDock );
            
            buiField.setBuiActionWindow ( donutMenuWindow );
            //diable donut window - we only want to see this window when buttonDock is toggled. This window is what makes the axis ring menu around node upon cliking possible.
            buiField.disableBuiActionDonutWindow ( );
            
            //establish dock controller
            dockController = new UNICODE_DockController ( buiField, buttonDock0, this, 8, 8, 702, 584, 8, 8, 590 ); 

            //establish binary clock dock
            //lunosClock = new UNICODE_LunosClockFrame ( 2000, 80, Color.white, Color.lightGray, new Color ( 87, 80, 80 ), Color.black, Color.red, Color.lightGray, new Color ( 87, 80, 80 ), Color.black, configurationManager.getColourFromFile ( ), userScreenWidth / 2 - 200/2, 80, directoryDisplayerVisualDisplayLimit );
            
            
        //add docks to this panel
        dockContainer.add ( buttonDock0 );
        dockContainer.add ( buiField );
       
        
        dockContainer.setLayout ( new BoxLayout ( dockContainer, BoxLayout.Y_AXIS ) );
        dockContainer.setBackground ( configurationManager.getColourFromFile ( ) );
        add ( dockContainer );
        
        //add mouse listening
        addMouseListener ( new mouseListening ( ) );
        addMouseMotionListener ( new mouseListening ( ) );
                
        //set focus to this panel
        setFocusable ( true );
    }
    
    //return current opacity level
    public float getStartupOpacityLevel ( )
    {
        opacityController.setOpacLevel ( configurationManager.getOpacityFromFile ( ) );
        return opacityController.getOpacLevel ( );
    }
    
    //paint component
    public void paintComponent ( Graphics graphics )
    {
        super.paintComponent ( graphics );
        Graphics2D graphics2d = ( Graphics2D ) graphics;

        //establish anti aliasing
        antiAliasController.setupAntiAliasing ( graphics2d );     
        
        //establish docks' dimensions
        buiField.setBackground ( configurationManager.getColourFromFile ( ) );
        buttonDock0.setBackground ( configurationManager.getColourFromFile ( ) );
        buiField.setBounds ( 0, 0, applicationFrame.getWidth ( ), applicationFrame.getHeight ( ) );
        buttonDock0.setBounds ( 0, 0, applicationFrame.getWidth ( ), 80 );
        dockContainer.setBounds ( 0, 0, applicationFrame.getWidth ( ), applicationFrame.getHeight ( ) );

    }
    
    //mouse listening
    private class mouseListening implements MouseListener, MouseMotionListener
    { 
        public void mouseClicked ( MouseEvent mEvent )
        {
            //dockController.togglebuttonDock0Animation ( );
            //repaint ( );
        }

        public void mouseReleased ( MouseEvent mEvent  )
        {
        }
        
        public void mouseEntered ( MouseEvent mEvent )
        {
        }  
        
        public void mouseExited ( MouseEvent mEvent )
        {
        }
     
        public void mousePressed ( MouseEvent mEvent )
        {
        }
        
        public void mouseDragged ( MouseEvent mEvent )
        {         
        }
        public void mouseMoved ( MouseEvent mEvent )
        {
        }
    }
    
    
    //add custom components which dont exist in UNICODE_MenuPanel by default,
    //where button click response is conceerned.
    public ArrayList <Object> generatedCustomComponentListbuttonDock0 ( )
    {
        ArrayList <Object> value = new ArrayList <Object> ( );
        
        value.add ( applicationFrame ); //0
        value.add ( configurationManager ); //1
        value.add ( buiField ); //2
        value.add ( antiAliasController ); //3
        value.add ( this ); //4
        value.add ( searchBox ); //5
        value.add ( coreCurrentGlowColourController ); //6
        value.add ( binaryClock ); //7
        value.add ( connectFrame ); //8
        
        return value;
    }
    
    //add custom components which dont exist in UNICODE_MenuPanel by default,
    //where button click response is conceerned.
    public ArrayList <Object> generatedCustomComponentListbuttonVerseDock ( )
    {
        ArrayList <Object> value = new ArrayList <Object> ( );
        
        value.add ( applicationFrame );
        value.add ( configurationManager );
        value.add ( buiField );
        value.add ( configurationManager.getBufferDimensionFromFile ( ) ); 
        value.add ( this ); 
        value.add ( binaryClock ); //7
        
        return value;
    }
    //add custom components which dont exist in UNICODE_MenuPanel by default,
    //where button click response is conceerned.
    public ArrayList <Object> generatedCustomComponentListbuttonWaveDock ( )
    {
        ArrayList <Object> value = new ArrayList <Object> ( );
        
        value.add ( applicationFrame );
        value.add ( configurationManager );
        value.add ( buiField );
        value.add ( configurationManager.getBufferDimensionFromFile ( ) ); 
        value.add ( this ); 
        value.add ( binaryClock ); //7
        
        return value;
    }
}