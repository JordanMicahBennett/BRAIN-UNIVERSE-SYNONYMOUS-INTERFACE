//Author(s): Jordan Micah Bennett
package data.packages.UNICORTEX; 

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.Collections;
import data.packages.UNICODE.*;

public class UNICORTEX_ObserverContentExtractor
{
    //attributes
	//establish dictionary to hold : count, and string array of category, title, and preview story.
	private Hashtable <Integer, String [ ] > websiteContentArchive = null;
	//establish url stream
	private String urlStream = "";
	private String errorCategory, errorTitle, errorContent;

	//constructor
    public UNICORTEX_ObserverContentExtractor ( String urlStream )
    {
		//establish url stream
		this.urlStream = urlStream;
		//establish content dictionary
        websiteContentArchive = new Hashtable <Integer, String [ ]> ( );
    }
    public UNICORTEX_ObserverContentExtractor ( String urlStream, String errorCategory, String errorTitle, String errorContent )
    {
		//establish error vars
			this.errorCategory = errorCategory;
			this.errorTitle = errorTitle;
			this.errorContent = errorContent;
		//establish url stream
		this.urlStream = urlStream;
		//establish content dictionary
        websiteContentArchive = new Hashtable <Integer, String [ ]> ( );
    }
    //methods
        //accessors
        public ArrayList getObserverContent ( )
        {
			UNICODE_ConveniencePack conveniencePack = new UNICODE_ConveniencePack ( );	
			//establish regular expression components
			String detectionPattern0String = "(.*)(\\.)(\\.)(\\.)(.*)</div>";
			String detectionPattern1String = "(.*)(\\\")(\\/)(\\S+)(\\/)(\\S+)(\\_)(.*)(\\\")(.*)";
			
				//establish pattern
				Pattern detectionPattern0 = Pattern.compile ( detectionPattern0String );
				Pattern detectionPattern1 = Pattern.compile ( detectionPattern1String );

				//estaablish variable to encompass web site titles to be included
				String [ ] categoryList = { "NEWS", "BUSINESS", "SPORT", "LIFESTYLE", "REGIONAL", "AUTO", "ENTERTAINMENT" };

			
			//derive preview stories
			try
			{
				URL url = new URL ( urlStream );
				BufferedReader in = new BufferedReader ( new InputStreamReader ( url.openStream ( ) ) );
				String inputLine;
				int entryCardinality = -1;
				
				while ( ( inputLine = in.readLine ( ) ) != null ) 
				{
				
					Matcher detectionMatcher0 = detectionPattern0.matcher ( inputLine );
					
					if ( ( detectionMatcher0.find ( ) ) && ( !conveniencePack.stringSubsetEnquiry ( inputLine, "<div" ) ) )
					{
					
						Matcher detectionMatcher1 = detectionPattern1.matcher ( in.readLine ( ) );  
						
						
						if ( detectionMatcher1.find ( ) )
						{
							String derivedCategory = detectionMatcher1.replaceAll ( "$4" ); //only build list based on predefined category elements available. Observer.com has many categories which we don't want.
							if 
							( 
								derivedCategory.toLowerCase ( ).equals ( categoryList [ 0 ].toLowerCase ( ) ) || derivedCategory.toLowerCase ( ).equals ( categoryList [ 1 ].toLowerCase ( ) ) ||
								derivedCategory.toLowerCase ( ).equals ( categoryList [ 2 ].toLowerCase ( ) ) || derivedCategory.toLowerCase ( ).equals ( categoryList [ 3 ].toLowerCase ( ) ) ||
								derivedCategory.toLowerCase ( ).equals ( categoryList [ 4 ].toLowerCase ( ) ) || derivedCategory.toLowerCase ( ).equals ( categoryList [ 5 ].toLowerCase ( ) )
							)
							{
								entryCardinality ++;
								String title = detectionMatcher1.replaceAll ( "$6" ).replace ( "-", " " );
								String category = derivedCategory.toUpperCase ( );
								String preview = detectionMatcher0.replaceAll ( "$1" );
								
								
								websiteContentArchive.put ( entryCardinality, new String [ ] { category, title, preview } );
							}
						}
					}
				}
			}
			catch ( Exception error )
			{
				websiteContentArchive.put ( 0, new String [ ] { errorCategory.toUpperCase ( ), errorTitle, errorContent } );
			}

			
			//compose an enumaration of elements so as to create a list.
			Enumeration enumeration = websiteContentArchive.elements ( );
			ArrayList websiteContentArchiveList = ( ArrayList ) Collections.list ( enumeration );
			
			// //then we need to type cast accordingly, to achieve each string array value per arraylist index.
			// for ( int i = 0; i < websiteContentArchiveList.size ( ); i ++ )
				// System.out.println ( ( ( String [ ] ) websiteContentArchiveList.get ( i ) ) [ 2 ] );
			return websiteContentArchiveList;
        }
}
