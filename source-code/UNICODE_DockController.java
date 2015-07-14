import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import javax.swing.JPanel;
import data.packages.UNICODE.*;

public class UNICODE_DockController
{
    //attributes
    private JPanel mainDock = null;
    private BUTTON_DOCK_0 buttonDock = null;
    private boolean dockHiddenEnquiry = false;
    private UNICODE_GuiPanel guiPanel = null;
    
    //forward animation
    private Timer dockForwardAnimationTimer = new Timer ( 1, new dockForwardAnimationListener ( ) ), dockBackwardAnimationTimer = new Timer ( 1, new dockBackwardAnimationListener ( ) );
    private int incrementationRate, docIncrementationRate, animationHeightLimit, forwardMutatingHeight, forwardMutatingDockY;
    private int decrementationRate, docDecrementationRate, backwardMutatingHeight, backwardMutatingDockY;
    private int initialAnimationHeight, intialDockY;
    private boolean dockVisibilityEnquiry = true;

    //constructor - takes ( object to be maximized, object to be hidden )
    public UNICODE_DockController ( JPanel _mainDock, BUTTON_DOCK_0 _buttonDock, UNICODE_GuiPanel _guiPanel, int _incrementationRate, int _decrementationRate, int _animationHeightLimit, int dock_height, int _docIncrementationRate, int _docDecrementationRate, int dock_Y )
    {
        mainDock = _mainDock;
        buttonDock = _buttonDock;
        guiPanel = _guiPanel;
        incrementationRate = _incrementationRate;
        decrementationRate = _decrementationRate;
        docIncrementationRate = _docIncrementationRate;
        docDecrementationRate = _docDecrementationRate;
        animationHeightLimit = _animationHeightLimit;
        initialAnimationHeight = dock_height;
        intialDockY = dock_Y;
    }

    //misc
    public void toggleButtonDockAnimation ( )
    {
        if ( dockVisibilityEnquiry ) 
        {
            forwardMutatingHeight = initialAnimationHeight;
            forwardMutatingDockY = intialDockY;
            dockForwardAnimationTimer.start ( );
            dockVisibilityEnquiry = false;
        }
        else
        {
            backwardMutatingDockY = buttonDock.getY ( );
            backwardMutatingHeight = forwardMutatingHeight;
            dockBackwardAnimationTimer.start ( );
            dockVisibilityEnquiry = true;
        }
    }
    
    public class dockForwardAnimationListener implements ActionListener
    {
        public void actionPerformed ( ActionEvent aEvent ) 
        {
            if ( forwardMutatingHeight <= animationHeightLimit )
            {
                forwardMutatingHeight += incrementationRate;
                forwardMutatingDockY += docDecrementationRate;
                mainDock.setBounds ( mainDock.getX ( ), mainDock.getY ( ), mainDock.getWidth ( ), forwardMutatingHeight );
                buttonDock.setBounds ( buttonDock.getX ( ), forwardMutatingDockY, buttonDock.getWidth ( ), buttonDock.getHeight ( ) );
                mainDock.repaint ( );
                guiPanel.repaint ( );
            }
        }
    }
    
    public class dockBackwardAnimationListener implements ActionListener
    {
        public void actionPerformed ( ActionEvent aEvent ) 
        {
            if ( backwardMutatingHeight >= initialAnimationHeight )
            {
                backwardMutatingHeight -= decrementationRate;
                backwardMutatingDockY -= docIncrementationRate;
                mainDock.setBounds ( mainDock.getX ( ), mainDock.getY ( ), mainDock.getWidth ( ), backwardMutatingHeight );
                buttonDock.setBounds ( buttonDock.getX ( ), backwardMutatingDockY, buttonDock.getWidth ( ), buttonDock.getHeight ( ) );
                mainDock.repaint ( );
                guiPanel.repaint ( );
            }
        }
    }
}