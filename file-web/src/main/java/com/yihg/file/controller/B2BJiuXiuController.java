package com.yihg.file.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.yihg.file.biz.FileUploadManager;
import com.yihg.file.util.ObjectUtil;

@Controller
@RequestMapping("/b2b")
public class B2BJiuXiuController extends BaseController {

	@Autowired
	private FileUploadManager fileManager;
	
	@RequestMapping("/importScenicImg")
	public void importScenicImg(HttpServletRequest request,HttpServletResponse response) throws IOException{
		long start = System.currentTimeMillis();
		Map<String,Object> params = getReqParams(request);
		if("jiuxiu".equals(ObjectUtil.toString(params.get("key")))){
			String path = ObjectUtil.toString(params.get("path"));
			importScenic(path);
		}
		long end = System.currentTimeMillis();
		sendJson(response, ((end-start)/1000)+"秒");
	}
	
	/**
	 * 导入图片到文件服务器
	 * @param path
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void importScenic(String path) throws FileNotFoundException, IOException{
		File hotelFile = new File(path+"/imgs");
		Map<String,Object> m = new HashMap<String,Object>();
		File[] files = hotelFile.listFiles();
		int i = 0;
		for(File file :files){
			i++;
			String name = file.getName();
			String extName = FilenameUtils.getExtension(name);
			String fdfsName = fileManager.uploadOneFile(IOUtils.toByteArray(new FileInputStream(file)), extName);
			m.put(name, fdfsName);
			System.out.println("import "+i+" img");
			//break;
		}
		System.out.println("is over");
		IOUtils.write(JSON.toJSONString(m),new FileOutputStream(new File(path+"/mappng.json")));
	}
}
