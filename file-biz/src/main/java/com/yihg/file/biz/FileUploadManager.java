package com.yihg.file.biz;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FileUploadManager {

	/**
	 * 
	 * @param fileBytes
	 *            字节流
	 * @param extName
	 *            文件扩展名
	 * @return
	 */
	static{
		// 初始化文件资源
					try {
						ClientGlobal.init("init-fdfs.conf");
					} catch (IOException e) {
						e.printStackTrace();
					} catch (MyException e) {
						e.printStackTrace();
					}
	}
	public String uploadOneFile(byte[] fileBytes, String extName) {

		// 利用字节流上传文件
		String[] strings;
		TrackerServer trackerServer = null;
		StorageServer storageServer = null;
		try {
			// 链接FastDFS服务器，创建tracker和Stroage
			TrackerClient trackerClient = new TrackerClient();
			trackerServer = trackerClient.getConnection();
			
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			strings = storageClient.upload_file(fileBytes, extName, null);
		
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (MyException e) {
			e.printStackTrace();
			return null;
		}finally{
			if(null != trackerServer){
				try {
					trackerServer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(null != storageServer ){
				try {
					storageServer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}

		StringBuffer sbPath = new StringBuffer();
		for (String string : strings) {
			sbPath.append("/" + string);
		}
		return sbPath.toString();
	}
	
	public int deleteFile(String filename){
		 TrackerServer trackerServer = null;
		if(null != filename && !"".equals(filename)){
			try { 
			Map<String,String> map = parseName(filename);	
			String groupName = map.get("groupName");
			String path = map.get("path");
			TrackerClient tracker = new TrackerClient(); 
			trackerServer  = tracker.getConnection(); 
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, 
                    storageServer); 
            int i = storageClient.delete_file(groupName,path);
            return i;
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(null != trackerServer){
					try {
						trackerServer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return 0;
	}
	
	public Map<String,String> parseName(String filename){
		String[] names =  filename.split("/");
	    String groupName = "";
	    StringBuffer sb = new StringBuffer();
		for(int i = 0;i<names.length;i++){
			if(names[i].trim().equals("")){
				continue;
			}
			if("".equals(groupName)){
				groupName = names[i];
			}else{
				sb.append("/").append(names[i]);
			}
		}
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("groupName",groupName);
		String path = sb.toString();
		if(path.startsWith("/")){
			path = sb.toString().replaceFirst("/","");
		}
		map.put("path",path);
		return map;
	}
	
	
}
