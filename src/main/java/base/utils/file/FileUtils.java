package base.utils.file;

import java.io.File;

import base.utils.InternalString;

/**
 * <p>FileUtils class.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public class FileUtils {

    /**
     * Verify that the folder exist, and can read and write
     * @param folder to verify
     * @return
     * boolean
     */
    public static boolean verifyFolder(String folder) {
        if( InternalString.isBlank(folder) ) {
            return false;
        }
        return verifyFolder(new File(folder));
    }

    public static boolean verifyFolder(File dir) {
        if( dir==null ) {
            return false;
        }
        return dir.isDirectory() && dir.canWrite() && dir.canRead();
    }


    /**
     * Verify that the file exist, and that the file can be read.
     * @param file to verify
     * @return
     * boolean
     */
    public static boolean verifyFile(String file) {
        if( InternalString.isBlank(file) ) {
            return false;
        }
        return verifyFile(new File(file));
    }

    /**
     * Verify that the file exist, and that the file can be read.
     * @param file to verify
     * @return
     * boolean
     */
    public static boolean verifyFile(File file) {
        if(file==null) {
            return false;
        }
        return file.isFile() && file.canRead();
    }
}
