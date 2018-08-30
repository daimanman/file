package com.yihg.file.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UploadFileResult implements java.io.Serializable {
	private static final long serialVersionUID = 1915489686463046866L;
	
	public int code;
	
	public String msg;
	
	public Map<String, String> fileDatas;
	
	public List<String> data = new ArrayList<String>();
	
	public boolean success;



	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return code == 0;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, String> getFileDatas() {
		return fileDatas;
	}

	public void setFileDatas(Map<String, String> fileDatas) {
		this.fileDatas = fileDatas;
	}

}
