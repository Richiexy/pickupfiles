package com.xy.tools;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

/**
 * 
 * @author Administrator
 *
 */
public class PickupFilesByDate {

	private String s_filePath;
	
	private String s_toDir;
	
	private String s_date;
	
	private int s_index;
	
	public  String getS_filePath() {
		return s_filePath;
	}

	public void setS_filePath(String path) {
		s_filePath = path;
	}

	public String getS_toDir() {
		return s_toDir;
	}

	public void setS_toDir(String dir) {
		s_toDir = dir;
	}

	public String getS_date() {
		return s_date;
	}

	public void setS_date(String date) {
		s_date = date;
	}

	public int getS_index() {
		return s_index;
	}

	public void setS_index(int index) {
		this.s_index = index;
	}

	public void run() throws IOException{
		//处理
		File toDirPath = new File(s_toDir);
		if(toDirPath.exists()){
			FileUtils.deleteDirectory(toDirPath);
			System.out.println("清空文件夹！再新建该文件夹");
			toDirPath.mkdirs();
		}else{
			toDirPath.mkdirs();
		}
		File dir = new File(s_filePath);
		fuc01(dir);
	}
	
	public void fuc01(File dir) throws IOException{
		
		File[] srcFiles = dir.listFiles();
		for(File srcFile :srcFiles){
			if(srcFile.isDirectory()){
				fuc01(srcFile);
			}else{
				long lastModified = srcFile.lastModified();
				Date date = new Date(lastModified);
				Date sDate =null;
				SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					sDate = sdf.parse(s_date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if( sdf.format(date).equals(sdf.format(sDate)) ){
					String destFilePath = s_toDir + srcFile.getParent().substring(s_index)+File.separator;
					File destFile = new File(destFilePath+ File.separator +srcFile.getName());
					FileUtils.copyFile(srcFile, destFile);
					System.out.println(srcFile.getParent()+File.separator +srcFile.getName());
				}
			}
		}
		
		
	}

}
