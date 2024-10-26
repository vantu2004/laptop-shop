package vn.hoidanit.laptopshop.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@Service
public class UploadImageService {
	
	private final ServletContext servletContext;
	
	public UploadImageService(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	// return string để lấy tên file ảnh vừa đc tạo
	public String handleSaveUploadFile(MultipartFile file, String targetFolder) throws IOException
	{
		String fileName ="";
        try {
	        // Chuyển ảnh sang dạng binary
        	byte[] bytes = file.getBytes();
        	
        	// getRealPath chỉ trỏ đến thư mục webapp, /resources/images giúp chỉ cụ thể đến thư mục images
	        String rootPath = this.servletContext.getRealPath("/resources/images");

	        // Dấu "/" giúp tự điều chỉnh để tương thích vs từng HĐH, ví dụ window là "\" nên việc thêm File.separator ("\") giúp tự tương thích vs rootPath
        	File dir = new File(rootPath + File.separator + targetFolder);
        	 // Trường hợp nếu chưa có thư mục avatar để chứa ảnh thì phải tạo, có rồi thì thôi
	        if (!dir.exists())
	            dir.mkdirs();
	
	        // Tạo 1 file mới dựa vào dir.getAbsolutePath + sửa tên file để bảo bảo khi upload cùng tên thì file ko bị ghi đè
	        fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
	        File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
	
	        // new FileOutputStream(serverFile) mở luồng dữ liệu để ghi dữ liệu vào file có đường đã đc cung cấp
	        // việc dùng BufferedOutputStream để đảm bảo hiệu suất
	        BufferedOutputStream stream;
			stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			// ghi dữ liệu trong mảng bytes vào BuferedOutputStream
	        stream.write(bytes);
	        stream.close();
		} 
        catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return fileName;
	}
}
