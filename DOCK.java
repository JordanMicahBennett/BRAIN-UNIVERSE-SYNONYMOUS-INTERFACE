import java.awt.Color;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import data.packages.UNICODE.*;
import data.packages.UNICORTEX.*;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class DOCK extends UNICODE_BlankPanel
{
    //establish console field
    private UNICODE_ConsoleField consoleField = null;
    private UNICORTEX_RssContentExtractor rssContentExtractor = null;
    private UNICODE_DateCreator date = null;

    public DOCK ( UNICODE_FadePaintDirections paintDirection, Color colouri, Color colourii, int targetWidth, int targetHeight, double leftColourSwingPercent )
    {  
        super ( paintDirection, colouri, colourii, targetWidth, targetHeight, leftColourSwingPercent ); //hard code to all white bitch.
        
        this.date = new UNICODE_DateCreator ( );
        
        consoleField = new UNICODE_ConsoleField ( 20, 20, 18, 10, "", "_", false, false, false, 8, "modern.tff", Color.black );

        consoleField.addLine ( "", new UNICODE_DateCreator ( ).getDate ( ) );
        consoleField.addLine ( "", "http://www.cnn.com" );
        rssContentExtractor = new UNICORTEX_RssContentExtractor ( "http://rss.cnn.com/rss/cnn_world.rss", "error!", "network/resource error!", "Please ensure you have a network connection." );
        consoleField.addLine ( "", "" );
        
        ArrayList observerContent = rssContentExtractor.getRssContent ( );
        int entryCardinality = observerContent.size ( );
        
        for ( int i = 0; i < entryCardinality; i ++ )
        {
            String title = ( ( String [ ] ) observerContent.get ( i ) ) [ 0 ];
            String link = ( ( String [ ] ) observerContent.get ( i ) ) [ 1 ];
            String description = ( ( String [ ] ) observerContent.get ( i ) ) [ 2 ];
            
            consoleField.addLine ( "", "" );
            consoleField.addLine ( title, "" );
            consoleField.addLine ( "", "" );
            consoleField.addLine ( "\"" + link + "\"", "" );
            consoleField.addLine ( "", "" );
            consoleField.addLine ( "", description );
            consoleField.addLine ( "", "" );
            consoleField.addLine ( "", "" );
        }
        
        //establish listeners
            //add mouse listeners
            addMouseMotionListener ( new mouseListening ( ) ) ;
            addMouseListener ( new mouseListening ( ) ) ;
            
            //add mouse wheel listener
            addMouseWheelListener ( new mouseWheelListenening ( ) );       
    }

   
    public void drawMore ( Graphics graphics, Graphics2D graphics2d )
    {
        consoleField.draw ( graphics, graphics2d );
    }
    
    //mouse listener
    private class mouseListening implements MouseMotionListener, MouseListener
    {
        public void mouseEntered ( MouseEvent mouseEvent )
        {
        }
        public void mouseExited ( MouseEvent mouseEvent )
        {
        }
        public void mousePressed ( MouseEvent mouseEvent )
        {
            consoleField.toggleScrollModeIndex ( );
        }
        public void mouseDragged ( MouseEvent mouseEvent )
        {       
        }
        public void mouseReleased ( MouseEvent mouseEvent )
        {
        }
        public void mouseMoved ( MouseEvent mouseEvent )
        {
        }
        public void mouseClicked ( MouseEvent mouseEvent )
        {
        }
    }
    
    private class mouseWheelListenening implements MouseWheelListener
    {
        public void mouseWheelMoved ( MouseWheelEvent mouseWheelEvent )
        {
            int rotationDirection = mouseWheelEvent.getWheelRotation ( );
            
            consoleField.scrollField ( rotationDirection, 20 );
            
            repaint ( );
        }
    }
}