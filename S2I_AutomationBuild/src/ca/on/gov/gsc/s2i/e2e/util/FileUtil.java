package ca.on.gov.gsc.s2i.e2e.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	private final static FileUtil instance = new FileUtil();

	public static FileUtil getInstance() {
		/*
		if (instance==null) {
			instance = new FileUtil();
		}*/

		return instance;
	}

	public List<String> listFolder(String path,String suffix) {
		List<String> result = new ArrayList<String>();
		
		File curDir = new File(path);
		
		File[] filesList = curDir.listFiles();
        for(File f : filesList){
            //if(f.isDirectory())
            if(f.isFile()){
            	if ( suffix==null || f.getName().toUpperCase().endsWith( suffix.toUpperCase() ) ) {
            		//System.out.println(f.getName());
                	result.add(path+f.getName());
            	}
            }
        }
        
        return result;
	}

	public boolean cleanBackUpFolder(String path,String backUpPath,String suffix) throws IOException {
		this.backUpFolder(path, backUpPath, suffix);
		this.cleanFolder(path, suffix);
        
        return false;
	}

	private boolean backUpFolder(String path,String backUpPath,String suffix) throws IOException {
		File curDir = new File(path);
		if ( suffix==null ) return false;
		
		File[] filesList = curDir.listFiles();
        for(File f : filesList){
            //if(f.isDirectory())
            if(f.isFile()){
            	if ( f.getName().toUpperCase().endsWith( suffix.toUpperCase() ) ) {
            		String fName = f.getName();
            		
            		File source = new File(path+fName);
            		File dest = new File(backUpPath+fName+"."+DateUtil.getInstance().getCurrentTimeFile());//

            		copyFileUsingFileChannels(source, dest);
            		
            		System.out.println( fName );
                	//f.delete();
            	}
            }
        }
        
        return false;
	}
	
	private static void copyFileUsingFileChannels(File source, File dest)
	        throws IOException {
	    FileChannel inputChannel = null;
	    FileChannel outputChannel = null;
	    try {
	        inputChannel = new FileInputStream(source).getChannel();
	        outputChannel = new FileOutputStream(dest).getChannel();
	        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
	    } catch ( Exception e ) {
	    	throw e;
	    }
	    finally {
	    	if ( inputChannel!=null ) {
		        inputChannel.close();
	    	}
	    	if ( outputChannel!=null ) {
	    		outputChannel.close();
	    	}

	    }
	}


	private boolean cleanFolder(String path,String suffix) {
		File curDir = new File(path);
		if ( suffix==null ) return false;
		
		File[] filesList = curDir.listFiles();
        for(File f : filesList){
            //if(f.isDirectory())
            if(f.isFile()){
            	if ( f.getName().toUpperCase().endsWith( suffix.toUpperCase() ) ) {
            		String fName = f.getName();
            		System.out.println( fName );
                	f.delete();
            	}
            }
        }
        
        return false;
	}

	public String readFile(String filePath) throws Exception {
		String result = null;

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filePath));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append("\r\n");
		        line = br.readLine();
		    }
		    result = sb.toString();
		} finally {
	    	if ( br!=null ) {
				br.close();
	    	}
		}

		return result;
	}

	public void writeFile(String filePath,String content) throws Exception {
		FileWriter fWriter = null;
		BufferedWriter writer = null;
		try {
		    fWriter = new FileWriter(filePath);
		    writer = new BufferedWriter(fWriter);
		    writer.write(content);
		    writer.close(); //make sure you close the writer object
		} catch (Exception e) {
			throw e;
		}
	}

}
