package cn.dunn.im.io;

import java.io.File;
import java.nio.file.Paths;

import org.hyperic.sigar.Sigar;  
  

  
public class SigarUtils {  
    public final static Sigar sigar = initSigar();  
  
    public static Sigar initSigar() {  
        try {  
        	String file = Paths.get(PathKit.getWebRootPath(),  "IMClient", "lib", "sigar/",".sigar_shellrc").toString();
            File classPath = new File(file).getParentFile();
            String path = System.getProperty("java.library.path");  
            String sigarLibPath = classPath.getCanonicalPath();
            // 为防止java.library.path重复加，此处判断了一下  
            if (!path.contains(sigarLibPath)) {  
                if (isOSWin()) {  
                    path += ";" + sigarLibPath;  
                } else {  
                    path += ":" + sigarLibPath;  
                }
                System.setProperty("java.library.path", path);  
            }  
            return new Sigar();  
        } catch (Exception e) {  
            return null;  
        }  
    }  
  
    public static boolean isOSWin() {// OS 版本判断  
        String OS = System.getProperty("os.name").toLowerCase();  
        if (OS.indexOf("win") >= 0) {  
            return true;  
        } else  
            return false;  
    }  
    

    
} 