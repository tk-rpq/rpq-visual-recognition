package codeaction.eden.virecg.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ibm.watson.developer_cloud.service.exception.RequestTooLargeException;
import com.ibm.watson.developer_cloud.service.exception.ServiceResponseException;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifier;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifiers;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.CreateClassifierOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.CreateClassifierOptions.Builder;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DeleteClassifierOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DeleteUserDataOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.GetClassifierOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.GetCoreMlModelOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ListClassifiersOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.UpdateClassifierOptions;

import codeaction.eden.virecg.config.WatsonVisualRecognitionConfig;

@Service
public class CustomClassifierService {

	/**
	 *  Create a classifier
	 * @param name				name of classifier
	 * @param positiveExamples	positive images
	 * @param negativeExamples	negative images
	 * @return
	 */
	public Classifier createClassifier(String name, Map<String, String> positiveExamples, List<String> negativeExamples) {
		try {
			IamOptions options = new IamOptions.Builder().apiKey(WatsonVisualRecognitionConfig.apikey).build();
			VisualRecognition service = new VisualRecognition(WatsonVisualRecognitionConfig.version, options);
			service.setEndPoint(WatsonVisualRecognitionConfig.endPoint);
			Builder builder = new CreateClassifierOptions.Builder();
			builder.name(name);
			positiveExamples.forEach((k,v)->{
				try {
					builder.addPositiveExamples(k, new File(v));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			});
			negativeExamples.forEach((k)->{
				try {
					builder.negativeExamples(new File(k));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			});
			CreateClassifierOptions createClassifierOptions = builder.build();			
			Classifier dogs = service.createClassifier(createClassifierOptions).execute();
			return dogs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Retrieve a list of classifiers
	public Classifiers retrieveClassifierList() {
		try {

			IamOptions options = new IamOptions.Builder().apiKey(WatsonVisualRecognitionConfig.apikey).build();
			VisualRecognition service = new VisualRecognition(WatsonVisualRecognitionConfig.version, options);
			service.setEndPoint(WatsonVisualRecognitionConfig.endPoint);

			ListClassifiersOptions listClassifiersOptions = new ListClassifiersOptions.Builder()
					.verbose(true)
					.build();
			Classifiers classifiers = service.listClassifiers(listClassifiersOptions).execute();
//			System.out.println(classifiers);
			return classifiers;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Retrieve classifier details
	public Classifier retrieveClassifierDetail(String classifierId) {
		try {

			IamOptions options = new IamOptions.Builder().apiKey(WatsonVisualRecognitionConfig.apikey).build();
			VisualRecognition service = new VisualRecognition(WatsonVisualRecognitionConfig.version, options);
			service.setEndPoint(WatsonVisualRecognitionConfig.endPoint);
			GetClassifierOptions getClassifierOptions = new GetClassifierOptions.Builder(classifierId).build();
			Classifier classifier = service.getClassifier(getClassifierOptions).execute();
//			System.out.println(classifier);
			return classifier;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Update a classifier
	@SuppressWarnings("deprecation")
	public Classifier updateClassifier(String classifierId) {
		try {

			IamOptions options = new IamOptions.Builder().apiKey(WatsonVisualRecognitionConfig.apikey).build();
			VisualRecognition service = new VisualRecognition(WatsonVisualRecognitionConfig.version, options);
			service.setEndPoint(WatsonVisualRecognitionConfig.endPoint);
//			String classifierId = "dogs_1477088859";

			InputStream negativeExamples = new FileInputStream("./more-cats.zip");
			UpdateClassifierOptions updateClassifierOptions = new UpdateClassifierOptions.Builder()
					.classifierId(classifierId)
					.addClass("dalmatian", new File("./dalmatian.zip"))
					.negativeExamples(negativeExamples)
					.negativeExamplesFilename("more-cats.zip")
					.build();

			Classifier updatedDogs = service.updateClassifier(updateClassifierOptions).execute();
//			System.out.println(updatedDogs);
			return updatedDogs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Delete a classifier
	public boolean deleteClassifier(String classifierId) {
		try {

			IamOptions options = new IamOptions.Builder().apiKey(WatsonVisualRecognitionConfig.apikey).build();
			VisualRecognition service = new VisualRecognition(WatsonVisualRecognitionConfig.version, options);
			service.setEndPoint(WatsonVisualRecognitionConfig.endPoint);
//			String classifierId = "dogs_147708889";

			DeleteClassifierOptions deleteClassifierOptions = 
					new DeleteClassifierOptions.Builder(classifierId).build();
			service.deleteClassifier(deleteClassifierOptions).execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// Retrieve a Core ML model of a classifier
	public InputStream retrieveCoreModel(String classifierId) {
		try {

			IamOptions options = new IamOptions.Builder().apiKey(WatsonVisualRecognitionConfig.apikey).build();
			VisualRecognition service = new VisualRecognition(WatsonVisualRecognitionConfig.version, options);
			service.setEndPoint(WatsonVisualRecognitionConfig.endPoint);
//			String classifierId = "dogs_1477088859";

			GetCoreMlModelOptions getCoreMlModelOptions = new GetCoreMlModelOptions.Builder()
					.classifierId(classifierId)
					.build();
			InputStream coreMlFile = service.getCoreMlModel(getCoreMlModelOptions).execute();
			return coreMlFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	// Delete labeled data
	public boolean deleteLabeledData(String customerId) {
		try {

			IamOptions options = new IamOptions.Builder().apiKey(WatsonVisualRecognitionConfig.apikey).build();
			VisualRecognition service = new VisualRecognition(WatsonVisualRecognitionConfig.version, options);
			service.setEndPoint(WatsonVisualRecognitionConfig.endPoint);
//			String customerId = "my_customer_ID";

			DeleteUserDataOptions deleteUserDataOptions = new DeleteUserDataOptions.Builder(customerId).build();
			service.deleteUserData(deleteUserDataOptions).execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public ClassifiedImages recognizeImg(String classifierId, String filePath, String fileName) {
		try {
//			String classifierId = "dogs_1477088859";
			IamOptions options = new IamOptions.Builder().apiKey(WatsonVisualRecognitionConfig.apikey).build();
			VisualRecognition service = new VisualRecognition(WatsonVisualRecognitionConfig.version, options);
			service.setEndPoint(WatsonVisualRecognitionConfig.endPoint);

//			InputStream imagesStream = new FileInputStream(filePath);
//			ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
//					.imagesFile(imagesStream)
//					.imagesFilename(fileName)
//					.classifierIds(Arrays.asList(classifierId))
//					.build();
//			ClassifiedImages result = service.classify(classifyOptions).execute();
			
			InputStream imagesStream = new FileInputStream(filePath);
			ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
			  .imagesFile(imagesStream)
			  .imagesFilename(fileName)
			  .threshold((float) 0.6)
			  .owners(Arrays.asList("me"))
			  .build();
			ClassifiedImages result = service.classify(classifyOptions).execute();
//			System.out.println(result);
			return result;
		} catch (FileNotFoundException e) {

			// Handle Not Found (404) exception
			System.out.println("Handle Not Found (404) exception");
			e.printStackTrace();
		} catch (RequestTooLargeException e) {

			// Handle Request Too Large (413) exception
			System.out.println("Handle Request Too Large (413) exception");
			e.printStackTrace();
		} catch (ServiceResponseException e) {

			// Base class for all exceptions caused by error responses from the service
			System.out.println("Service returned status code " + e.getStatusCode() + ": " + e.getMessage());
		}
		return null;
	}
}
