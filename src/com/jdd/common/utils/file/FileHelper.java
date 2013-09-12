package com.jdd.common.utils.file;

import java.io.File;
import java.io.FilenameFilter;

public class FileHelper 
{
	public static boolean isExist(String path)
	{
		if( null == path )
		{
			return false;
		}
		
		File f = new File(path);
		
		return f.exists();
	}
	
	public static File[] listFiles(String path,FilenameFilter ft)
	{
		if( !isExist(path) )
		{
			return null;
		}
		
		File d = new File(path);
		
		return d.listFiles(ft);
	}
	
	public static void delFileonExist(String path)
	{
		if( !isExist(path) )
		{
			return;
		}
		
		File f = new File(path);
		
		f.delete();
	}
 }
