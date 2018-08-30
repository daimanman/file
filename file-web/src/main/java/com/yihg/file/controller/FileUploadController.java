package com.yihg.file.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yihg.file.biz.FileUploadManager;
import com.yihg.file.client.UploadFileResult;
import com.yihg.file.util.ObjectUtil;

@Controller
@RequestMapping("/fileupload")
public class FileUploadController extends BaseController{

	@Autowired
	private FileUploadManager fileManager;
	
	@RequestMapping("/test")
	public void test01(HttpServletRequest request,HttpServletResponse response) throws IOException{
		sendJson(response, getReqParams(request));
	}
	
	@RequestMapping("/upload")
	public void upload(HttpServletRequest request,HttpServletResponse response)  throws IOException{
		System.out.println(getCookieMap(request));
		UploadFileResult result = new UploadFileResult();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    	MultiValueMap<String, MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();
    	List<String> datas = new ArrayList<String>();
    	Set<String> multiFileMapKeys = multiFileMap.keySet();
    	Map<String, String> map = new HashMap<String, String>();
    	for(String multiFileMapKey:multiFileMapKeys){
    		List<MultipartFile> multipartFiles = multiFileMap.get(multiFileMapKey);
    		if(CollectionUtils.isEmpty(multiFileMapKeys)){
    			continue;
    		}
    		for(MultipartFile file:multipartFiles){
    			String  filename = file.getOriginalFilename();
    			String  extName = FilenameUtils.getExtension(filename);
    			byte[] bytes = file.getBytes();
    			if(bytes == null || bytes.length == 0){
    				continue;
    			}
    			String fdfsUrl = fileManager.uploadOneFile(file.getBytes(),extName);
    			datas.add(fdfsUrl);
    			map.put(multiFileMapKey,fdfsUrl);
    		}
    	}
    	result.fileDatas = map;
    	result.data  = datas;
    	
    	sendJson(response, result);
	}
	
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response) throws IOException{
		UploadFileResult result = new UploadFileResult();
		Map<String,Object> params = getReqParams(request);
		String delfilename = ObjectUtil.toString(params.get("delfilename"));
		result.code = fileManager.deleteFile(delfilename);
		sendJson(response, result);
	}
	
	
}
