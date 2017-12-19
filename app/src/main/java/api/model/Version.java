package api.model;

public class Version {

	// {file_url=http://192.168.0.105:805/Uploads/Download/2017-08-30/59a655d03e970.apk,
	// versionNam=null, versionCode=null, description=<div>
	//  版本描述\n1:修改内容；\n2：修复程序错误<br />
	// </div>, fileSize=45, publicTime=1504075818}

	private String file_url;

	private String fileSize;

	private String versionName;

	public String versionCode;

	public String description;

	public String getFile_url() {
		return file_url;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
