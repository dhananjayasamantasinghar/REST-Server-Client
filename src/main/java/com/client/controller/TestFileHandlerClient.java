package com.client.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.client.util.RestServiceInvoker;

@Controller
public class TestFileHandlerClient {

	@Lookup
	public RestServiceInvoker getRestServiceInvoker(){
		return null;
	}
	@GetMapping("/")
	public String index() {
		return "upload";
	}

	@PostMapping(value = "/upload")
	public String sendFile(@RequestParam("file") MultipartFile file,ModelMap model) {
		try {

		/*	
		 	MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
			parts.add("file", convertMultiPartToFileSystemResource(file));

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String,
			 Object>>(parts, headers);

			restTemplate.exchange("http://localhost:8080/file/uploadFile", HttpMethod.POST, requestEntity,
					String.class);
					
			*/
			
			String res = getRestServiceInvoker()
			.path("http://localhost:8080/file/uploadFile")
			.post()
			.contentType(MediaType.MULTIPART_FORM_DATA)
			.formParam("file",convertMultiPartToFileSystemResource(file))
			.invoke(String.class);
			
			model.put("message", res);
		} catch (Exception e) {
			model.put("message", "File Upload Failed!! :: "+e.getLocalizedMessage());
			return "upload-failed";
		}

		return "upload-success";
	}

	private FileSystemResource convertMultiPartToFileSystemResource(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return new FileSystemResource(convFile);
	}
}
