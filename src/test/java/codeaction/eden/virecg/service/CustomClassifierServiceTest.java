package codeaction.eden.virecg.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifier;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifiers;
import codeaction.eden.virecg.common.SpringJUnit4Base;

public class CustomClassifierServiceTest extends SpringJUnit4Base {

	@Autowired private CustomClassifierService customClassifierService;
	
	@Test
	/**
	 * Test the classifier
	 */
	public void testCreateClassifier() {
		String name = "dogs";
		Map<String, String> positiveExamples = new HashMap<String, String>();
		positiveExamples.put("beagle", "E:\\ibm\\dogs\\beagle.zip");
		positiveExamples.put("goldenretriever", "E:\\ibm\\dogs\\golden-retriever.zip");
		positiveExamples.put("husky", "E:\\ibm\\dogs\\husky.zip");
		
		List<String> negativeExamples = new ArrayList<String>();
		negativeExamples.add("E:\\ibm\\dogs\\cats.zip");
		// call the classifier method
		Classifier classifier = customClassifierService.createClassifier(name, positiveExamples, negativeExamples);
		assertNotNull(classifier);
		System.out.println(classifier);	
	}

	@Test
	public void testRetrieveClassifierList() {
		Classifiers classifiers = customClassifierService.retrieveClassifierList();
		assertNotNull(classifiers);
		System.out.println(classifiers);
	}

	@Test
	public void testRetrieveClassifierDetail() {
		String classifierId = "dogs_2106064385";
		Classifier classifier = customClassifierService.retrieveClassifierDetail(classifierId);
		assertNotNull(classifier);
		System.out.println(classifier);
	}

	@Test
	public void testDeleteClassifier() {
		String classifierId = "DefaultCustomModel_224598167";
		boolean result = customClassifierService.deleteClassifier(classifierId);
		assertTrue(result);
	}
	
	@Test
	public void testRecognizeDog() {
		String classifierId = "dogs_2106064385";
		String filePath = "E:\\ibm\\dogs\\Beagle\\1024px-Beagle_1.jpg";
		String fileName = "1024px-Beagle_1.jpg";
		
		ClassifiedImages result = customClassifierService.recognizeImg(classifierId, filePath, fileName);
		assertNotNull(result);
		System.out.println(result);	
	}
	
	@Test
	public void testRecognizeDog1() {
		String classifierId = "dogs_2106064385";
		String filePath = "E:\\ibm\\dogs\\Cats\\407327817_81e0d88ee9_z.jpg";
		String fileName = "407327817_81e0d88ee9_z.jpg";
		
		ClassifiedImages result = customClassifierService.recognizeImg(classifierId, filePath, fileName);
		assertNotNull(result);
		System.out.println(result);	
	}
	
	@Test
	public void testDeleteClassifier1() {
		String classifierId = "stars_1607724279";
		boolean result = customClassifierService.deleteClassifier(classifierId);
		assertTrue(result);
	}
	
	@Test
	public void testDeleteClassifier2() {
		String classifierId = "dogs_2106064385";
		boolean result = customClassifierService.deleteClassifier(classifierId);
		assertTrue(result);
	}
	
	/**
	 * 训练明星图片
	 */
	@Test
	public void testCreateClassifier_Star() {
		String name = "stars";
		Map<String, String> positiveExamples = new HashMap<String, String>();
		positiveExamples.put("jiajingwen", "E:\\ibm\\start\\jiajingwen.zip");
		positiveExamples.put("zhaoliying", "E:\\ibm\\start\\zhaoliying.zip");
		
		List<String> negativeExamples = new ArrayList<String>();
		negativeExamples.add("E:\\ibm\\dogs\\cats.zip");
		negativeExamples.add("E:\\ibm\\dogs\\beagle.zip");
		negativeExamples.add("E:\\ibm\\dogs\\golden-retriever.zip");
		negativeExamples.add("E:\\ibm\\dogs\\husky.zip");
		Classifier classifier = customClassifierService.createClassifier(name, positiveExamples, negativeExamples);
		assertNotNull(classifier);
		System.out.println(classifier);	
	}
	
	@Test
	public void testRecognizeStar1() {
		String classifierId = "stars_1607724279";
		String filePath = "E:\\ibm\\start\\jiajingwen\\1.jpg";
		String fileName = "jiajingwen_1.jpg";
		
		ClassifiedImages result = customClassifierService.recognizeImg(classifierId, filePath, fileName);
		assertNotNull(result);
		System.out.println(result);	
	}
	
	@Test
	public void testRecognizeStar2() {
		String classifierId = "stars_1607724279";
		String filePath = "E:\\ibm\\start\\jiajingwen\\41.jpg";
		String fileName = "jiajingwen_41.jpg";
		
		ClassifiedImages result = customClassifierService.recognizeImg(classifierId, filePath, fileName);
		assertNotNull(result);
		System.out.println(result);	
	}
	
	@Test
	public void testRecognizeStar3() {
		String classifierId = "stars_1607724279";
		String filePath = "E:\\ibm\\start\\zhaoliying\\1.jpg";
		String fileName = "zhaoliying_1.jpg";
		
		ClassifiedImages result = customClassifierService.recognizeImg(classifierId, filePath, fileName);
		assertNotNull(result);
		System.out.println(result);	
	}
}
