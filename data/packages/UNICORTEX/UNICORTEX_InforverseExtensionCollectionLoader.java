//Author(s): Jordan Micah Bennett
package data.packages.UNICORTEX; 
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import data.packages.UNICODE.*;

public class UNICORTEX_InforverseExtensionCollectionLoader
{
    //attributes
    private ArrayList <String> wordExtensions = null, audioExtensions = null, videoExtensions = null, executableExtensions = null;
    private UNICODE_ConveniencePack conveniencePack = null;
	
    //contructor
    public UNICORTEX_InforverseExtensionCollectionLoader ( )
    {
		conveniencePack = new UNICODE_ConveniencePack ( );
        wordExtensions = new ArrayList <String> ( );
        audioExtensions = new ArrayList <String> ( );
        videoExtensions = new ArrayList <String> ( );
        executableExtensions = new ArrayList <String> ( );
    }
    
    
    //methods
        //accessors
        public ArrayList <String> getWordExtensions ( )
        {
            try
            {
                Scanner scanner = new Scanner ( new File ( "data/config/INFOVERSE_WORD_EXTENSIONS.ini" ) );
                
                while ( scanner.hasNext ( ) )
                    wordExtensions.add ( scanner.nextLine ( ) );
                scanner.close ( );
            }
            catch ( Exception error )
            {
            }
            
            return wordExtensions;
        }
        public ArrayList <String> getAudioExtensions ( )
        {
            try
            {
                Scanner scanner = new Scanner ( new File ( "data/config/INFOVERSE_AUDIO_EXTENSIONS.ini" ) );
                
                while ( scanner.hasNext ( ) )
                    audioExtensions.add ( scanner.nextLine ( ) );
                scanner.close ( );
            }
            catch ( Exception error )
            {
            }
            
            return audioExtensions;
        }
        public ArrayList <String> getVideoExtensions ( )
        {
            try
            {
                Scanner scanner = new Scanner ( new File ( "data/config/INFOVERSE_VIDEO_EXTENSIONS.ini" ) );
                
                while ( scanner.hasNext ( ) )
                    videoExtensions.add ( scanner.nextLine ( ) );
                scanner.close ( );
            }
            catch ( Exception error )
            {
            }
            
            return videoExtensions;
        }
		
        public ArrayList <String> getExecutableExtensions ( )
        {
            try
            {
                Scanner scanner = new Scanner ( new File ( "data/config/INFOVERSE_EXECUTABLE_EXTENSIONS.ini" ) );
                
                while ( scanner.hasNext ( ) )
                    executableExtensions.add ( scanner.nextLine ( ) );
                scanner.close ( );
            }
            catch ( Exception error )
            {
            }
            return executableExtensions;
        }
        
        public String toString ( )
        {
            String returnValue = "";
            
            
            returnValue += "WORDS\n";
            for ( String ext : getWordExtensions ( ) )
                returnValue += ext + ",";
                
            returnValue += "\n\nAUDIOS\n";
            for ( String ext : getAudioExtensions ( ) )
                returnValue += ext + ",";
                
            returnValue += "\n\nVIDEOS\n";
            for ( String ext : getVideoExtensions ( ) )
                returnValue += ext + ",";
                
            returnValue += "\n\nEXECUTABLES\n";
            for ( String ext : getExecutableExtensions ( ) )
                returnValue += ext + ",";    
                
            return returnValue;
        }
		
		
    public String getUniCortexFileExtension ( String completeFileStream, ArrayList <String> wordExtensionList, ArrayList <String> audioExtensionList, ArrayList <String> videoExtensionList, ArrayList <String> executableExtensionList )
    {
        String returnValue = null;
        
        boolean extensionWordExistenceEnquiry = false, extensionAudioExistenceEnquiry = false, extensionVideoExistenceEnquiry = false, extensionExecutableExistenceEnquiry = false, extensionDirectoryExistenceEnquiry = false;
        boolean complexFileExistenceEnquiry = false;
        
        for ( String extension : wordExtensionList )
            if ( conveniencePack.getFileExtension ( completeFileStream ).toLowerCase ( ).equals ( extension.toLowerCase ( ) ) )
            {
                returnValue = "W";
                extensionWordExistenceEnquiry = true;
            }
                
        for ( String extension : audioExtensionList )
            if ( conveniencePack.getFileExtension ( completeFileStream ).toLowerCase ( ).equals ( extension.toLowerCase ( ) ) )
            {
                returnValue = "A";
                extensionAudioExistenceEnquiry = true;
            }
                
        for ( String extension : videoExtensionList )
            if ( conveniencePack.getFileExtension ( completeFileStream ).toLowerCase ( ).equals ( extension.toLowerCase ( ) ) )
            {
                returnValue = "V";
                extensionVideoExistenceEnquiry = true;
            }

        for ( String extension : executableExtensionList )
            if ( conveniencePack.getFileExtension ( completeFileStream ).toLowerCase ( ).equals ( extension.toLowerCase ( ) ) )
            {
                returnValue = "E";
                extensionExecutableExistenceEnquiry = true;
            }
                
        if ( new File ( completeFileStream ).isDirectory ( ) )
        {
            returnValue = "I"; //infoverse (foldeR) found.
            extensionDirectoryExistenceEnquiry = true;
        }
        
        //if all the above doesn't exist, then this is an infowave not yet added to configuration ini files.
        complexFileExistenceEnquiry = ( ( extensionDirectoryExistenceEnquiry == false ) && ( extensionWordExistenceEnquiry == false ) && ( extensionAudioExistenceEnquiry == false ) && ( extensionExecutableExistenceEnquiry == false ) ) ? true : false;
            
        if ( complexFileExistenceEnquiry )
            returnValue = "C";
        
        return returnValue;
    }
}