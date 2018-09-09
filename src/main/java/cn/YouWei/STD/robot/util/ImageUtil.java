package cn.YouWei.STD.robot.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Image工具类
 * 图片存取
 * @author cxf(june 4, 2015)
 * 
 */
public class ImageUtil {
	private static final Logger logger=LoggerFactory.getLogger(ImageUtil.class);
	/**
	 * 根据url保存图片
	 * @param request
	 * @param destUrl
	 */
	public static void saveToFile(HttpServletRequest request, String destUrl) {
		FileOutputStream fileOutputStream = null;
		BufferedInputStream bufferedInputStream = null;
		HttpURLConnection httpURLConnection = null;
		URL url = null;
		int BUFFER_SIZE = 1024;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		try {
			url = new URL(destUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.connect();
			bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
			fileOutputStream = new FileOutputStream(request.getContextPath()+"\\media\\wechatImage"+System.currentTimeMillis()+".png");
			while ((size = bufferedInputStream.read(buf)) != -1) {
				fileOutputStream.write(buf, 0, size);
			}
			fileOutputStream.flush();
		} catch (IOException e) {
		} catch (ClassCastException e) {
		} finally {
			try {
				fileOutputStream.close();
				bufferedInputStream.close();
				httpURLConnection.disconnect();
			} catch (IOException e) {
			} catch (NullPointerException e) {
			}
		}
	}
	/**图片上传
	 * @param request
	 * @param response
	 * @param path
	 * @return 返回文件名
	 */
	public static Map<String,String> fileUpload(HttpServletRequest request, HttpServletResponse response, String path) {
		  DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		  ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		  HashMap<String, String> resultMap = new HashMap<String,String>();
		  try {
		   List items = servletFileUpload.parseRequest(request);
		   Iterator fileIterator = items.iterator();
		   while (fileIterator.hasNext()) {
		    FileItem item = (FileItem) fileIterator.next();
		    if (item.isFormField()) {
		    	logger.info("表单参数名:" + item.getFieldName() + ",表单参数值:" + item.getString("UTF-8"));
		    	resultMap.put(""+item.getFieldName(), item.getString("UTF-8"));
		    	
		    } else {
		     if (item.getName() != null && !item.getName().equals("")) {
		    	 logger.info("上传文件的大小:" + item.getSize());
		    	 logger.info("上传文件的类型:" + item.getContentType());
		    	 // item.getName()返回上传文件在客户端的完整路径名称
		    	 logger.info("上传文件的名称:" + item.getName());
		    	 
		    	 //修改上传图片的名字
		    	 String pictureName = System.currentTimeMillis()+ item.getName().substring(item.getName().lastIndexOf("."));
		    	 			//  上传文件的保存路径
		      File file = new File(request.getServletContext().getRealPath("/")+File.separator+"media"+File.separator+"image"+File.separator+path, pictureName);
		      if(file.exists())file.delete();
		      item.write(file);
		      request.setAttribute("upload.message", "上传文件成功！");
		      resultMap.put(""+item.getFieldName(), pictureName);
		     }else{
		      request.setAttribute("upload.message", "没有选择上传文件！");
		     }
		    }
		   }
		  }catch(FileUploadException e){
		   e.printStackTrace();
		  } catch (Exception e) {
		   e.printStackTrace();
		   request.setAttribute("upload.message", "上传文件失败！");
		  }
		  return resultMap;
		 }

	public static void main(String[] args) {
//		WechatImageUtil.saveToFile(new HttpServletRequest(),"http://wx.qlogo.cn/mmopen/6J8FwIsKv9MWwDbF9J7xYzHDgxhicyibDJHzqTmjbpjTINo144DbticXmPonyzGBQe2BlgUsnNgbZnOSdbZWjUIV6XUMZnyhke2/96");
	}
}
