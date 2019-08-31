package com.gbastos.bakery.reader;

import java.io.File;
import com.gbastos.bakery.exception.DatasetFileNotFoundException;
import com.gbastos.bakery.exception.DatasetFilePathIsEmptyException;
import com.gbastos.bakery.exception.DatasetFilePathIsNullException;
import com.gbastos.bakery.exception.NotSupportedDatasetTypeException;

public class DataReaderFactory {

  private static final String TXT = "txt";

  private DataReaderFactory() {
    super();
  }

  private static void validateInput(String filePath) {
    if (filePath == null) {
      throw new DatasetFilePathIsNullException();
    } else if (filePath == null || filePath.trim().length() == 0) {
      throw new DatasetFilePathIsEmptyException();
    }
  }

  private static File retrieveFile(String path) {
    try {
      ClassLoader dataFactory = DataReaderFactory.class.getClassLoader();
      return new File(dataFactory.getResource(path).getFile());
    } catch (NullPointerException e) {
      throw new DatasetFileNotFoundException(path, e);
    }
  }

  private static TextDatasetReader buildDatasetReader(File dataFile, String extension) {
    if (extension.equalsIgnoreCase(TXT)) {
      return new TextDatasetReader(dataFile);
    } else {
      throw new NotSupportedDatasetTypeException(extension);
    }
  }

  private static String retrieveFileExtension(String fileName) {
    int i = fileName.lastIndexOf('.');
    return i >= 0 ? fileName.substring(i + 1) : null;
  }

  public static TextDatasetReader retrieveDataReaderByFile(String datasetPath) {
    validateInput(datasetPath);
    File dataFile = retrieveFile(datasetPath);
    return buildDatasetReader(dataFile, retrieveFileExtension(datasetPath));
  }
}
