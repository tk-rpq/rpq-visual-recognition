package codeaction.eden.virecg.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DetectedFaces;
import codeaction.eden.virecg.common.SpringJUnit4Base;

public class FaceDetectServiceTest extends SpringJUnit4Base {

	@Autowired private FaceDetectService faceDetectService;
	
	@Test
	public void testDetectedFace() {
		String filePath = "E:\\ibm\\Ginni_Rometty.jpg";
		DetectedFaces result = faceDetectService.detectedFace(filePath);
		assertNotNull(result);
		System.out.println(result);
	}

	@Test
	public void testDetectedFace1() {
		String filePath = "E:\\ibm\\faces\\11.jpg";
		DetectedFaces result = faceDetectService.detectedFace(filePath);
		assertNotNull(result);
		System.out.println(result);
	}
	
}
