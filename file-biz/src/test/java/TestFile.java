import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.yihg.file.biz.FileUploadManager;

public class TestFile {

	@Test
	public void testFile() throws FileNotFoundException, IOException{
		FileUploadManager fileUploadManager = new FileUploadManager();
		File file5 = new File("G:\\imgs\\05.png");
		File file6 = new File("G:\\imgs\\06.png");
		String name1 = fileUploadManager.uploadOneFile(IOUtils.toByteArray(new FileInputStream(file5)), "png");
		String name2 = fileUploadManager.uploadOneFile(IOUtils.toByteArray(new FileInputStream(file6)), "png");
		System.out.println(name1);
		System.out.println(name2);
	}
	
	public void testImportHotelFile() throws Exception{
		FileUploadManager fileUploadManager = new FileUploadManager();
		File hotelFile = new File("F:\\jiuxiu\\hotel\\imgs");
		Map<String,Object> m = new HashMap<String,Object>();
		File[] files = hotelFile.listFiles();
		int i = 0;
		for(File file :files){
			i++;
			String name = file.getName();
			String extName = FilenameUtils.getExtension(name);
			String fdfsName = fileUploadManager.uploadOneFile(IOUtils.toByteArray(new FileInputStream(file)), extName);
			m.put(name, fdfsName);
			System.out.println("import "+i+" img");
			//break;
		}
		System.out.println("is over");
		IOUtils.write(JSON.toJSONString(m),new FileOutputStream(new File("F:\\jiuxiu\\m.txt")));
	}
	public void testImportScienceFile() throws Exception{
		FileUploadManager fileUploadManager = new FileUploadManager();
		File hotelFile = new File("F:\\jiuxiu\\science\\imgs");
		Map<String,Object> m = new HashMap<String,Object>();
		File[] files = hotelFile.listFiles();
		int i = 0;
		for(File file :files){
			i++;
			String name = file.getName();
			String extName = FilenameUtils.getExtension(name);
			String fdfsName = fileUploadManager.uploadOneFile(IOUtils.toByteArray(new FileInputStream(file)), extName);
			m.put(name, fdfsName);
			System.out.println("import "+i+" img");
			//break;
		}
		System.out.println("is over");
		IOUtils.write(JSON.toJSONString(m),new FileOutputStream(new File("F:\\jiuxiu\\s.txt")));
	}
	
	
	@Test
	public void testImportPicturesFile() throws Exception{
		FileUploadManager fileUploadManager = new FileUploadManager();
		File hotelFile = new File("F:\\jiuxiu\\pictures\\imgs");
		Map<String,Object> m = new HashMap<String,Object>();
		File[] files = hotelFile.listFiles();
		int i = 0;
		for(File file :files){
			i++;
			String name = file.getName();
			String extName = FilenameUtils.getExtension(name);
			String fdfsName = fileUploadManager.uploadOneFile(IOUtils.toByteArray(new FileInputStream(file)), extName);
			m.put(name, fdfsName);
			System.out.println("import "+i+" img");
			//break;
		}
		System.out.println("is over");
		IOUtils.write(JSON.toJSONString(m),new FileOutputStream(new File("F:\\jiuxiu\\pictures\\pmap.txt")));
	}
	
	
	@Test
	public void testImportRoomFile() throws Exception{
		long start = System.currentTimeMillis();
		FileUploadManager fileUploadManager = new FileUploadManager();
		File hotelFile = new File("F:\\jiuxiu\\room\\imgs");
		Map<String,Object> m = new HashMap<String,Object>();
		File[] files = hotelFile.listFiles();
		int i = 0;
		for(File file :files){
			i++;
			String name = file.getName();
			String extName = FilenameUtils.getExtension(name);
			String fdfsName = fileUploadManager.uploadOneFile(IOUtils.toByteArray(new FileInputStream(file)), extName);
			m.put(name, fdfsName);
			System.out.println("import "+i+" img");
			//break;
		}
		long end = System.currentTimeMillis();
		System.out.println("is over"+(end-start)/1000+"秒");
		IOUtils.write(JSON.toJSONString(m),new FileOutputStream(new File("F:\\jiuxiu\\room\\pmap.txt")));
	}
	
	
	
	@Test
	public void testDeteFile() throws FileNotFoundException, IOException{
		FileUploadManager fileUploadManager = new FileUploadManager();
		int flag = fileUploadManager.deleteFile("/group1/M00/00/00/rBERoFmwIWqAXaY5ABm5rVnrzG0566.png");
		System.out.println(flag);
	}
	
	@Test
	public void testParse(){
		String name = "group1/M00/00/00/rBERoFmwCJaAS95FABm5rVnrzG0284.png";
		String[] names =  name.split("/");
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
		
		System.out.println(sb.toString());
		System.out.println(groupName+"-------");
	}
	
	@Test
	public void testP(){
		String name = "赠两大豪华餐+小鱼温泉 &lt;180度海景房+豪华园景房&gt;二选一，2天自由行";
		System.out.println(name.replaceAll("&lt;", "<").replaceAll("&gt;", ">"));
		
	}
	
	@Test
	public void testReplace(){
		String name = "D\"xm\"";
		System.out.println(name.replaceAll("\"",""));
	}
}
