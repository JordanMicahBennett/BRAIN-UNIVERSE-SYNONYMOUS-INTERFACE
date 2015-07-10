//Author(s): Jordan Micah Bennett
package data.packages.UNICORTEX; 

import java.net.URL;
import java.io.InputStreamReader;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.ArrayList;
import data.packages.UNICODE.*;

/**
 * It Reads and prints any RSS/Atom feed type.
 * <p>
 * @author Alejandro Abdelnur
 *
 */

public class UNICORTEX_RssContentExtractor 
{
    //attributes
	//establish dictionary to hold : count, and string array of category, title, and preview story.
	private Hashtable <Integer, String [ ] > websiteContentArchive = null;
	//establish url stream
	private String urlStream = "";
	private String errorTitle, errorLink, errorDescription;

	//constructor
    public UNICORTEX_RssContentExtractor ( String urlStream, String errorTitle, String errorLink, String errorDescription )
    {
		//establish error vars
			this.errorTitle = errorTitle;
			this.errorLink = errorLink;
			this.errorDescription = errorDescription;
		//establish url stream
		this.urlStream = urlStream;
		//establish content dictionary
        websiteContentArchive = new Hashtable <Integer, String [ ]> ( );
    }
    
    public ArrayList getRssContent ( )
    {
        Dictionary websiteContentArchive = new Hashtable <Integer, String [ ]> ( );
        
        int entryDiscoveryIndex = 0;
        
        boolean ok = false;
        try 
        {
                URL feedUrl = new URL(urlStream);
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(feedUrl));

         
                for (Iterator i = feed.getEntries().iterator(); i.hasNext();)
                {
                    SyndEntry entry = (SyndEntry) i.next();
                    
                    /*
                    //Jordan Bennett
                    //code used to derive human-readble content via feed entry collection (feed.getEntries())
                    //1) Imported SyndContentImpl
                    //2) casted entry.getDescription() as SyndContentImpl, then assumed getValue ( ) attribute to get SyndContentImpl content.
                    System.out.println(entry.getTitle());
                    System.out.println(entry.getLink());
                    System.out.println(((SyndContentImpl)entry.getDescription()).getValue());
                    System.out.println("\n\n");
                    */
                   
                    entryDiscoveryIndex ++;
                    websiteContentArchive.put ( entryDiscoveryIndex, new String [ ] { entry.getTitle(), entry.getLink(), ((SyndContentImpl)entry.getDescription()).getValue() } );
                }
 
                ok = true;
            }
            catch (Exception ex) 
            {
                ex.printStackTrace();
                System.out.println("ERROR: "+ex.getMessage());
            }
 
        if (!ok) 
        {
           websiteContentArchive.put ( 0, new String [ ] { errorTitle.toUpperCase ( ), errorLink, errorDescription } );
        }
        
	    Enumeration enumeration = websiteContentArchive.elements ( );
	    return ( ArrayList ) Collections.list ( enumeration );
    }
}