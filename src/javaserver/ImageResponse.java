package javaserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

/**
 * Created by Taryn on 3/6/14.
 */
public class ImageResponse extends AbstractResponse {

    @Override
    String getResponseMessage(String status, String fileName) throws FileNotFoundException {
        String contentType = getContentTypeString(fileName);
        String response = getStatusLine(status) + getDateInfo() + getServerInfo() + getContentTypeInfo(contentType) + getContentTypeInfo(fileName) + getImage(fileName);
        return response;
    }

    String getContentTypeString(String fileName) {
        if (fileName.equals("image.gif")) {
            return "image/gif";
        } else if (fileName.equals("image.jpeg")) {
            return "image/jpeg";
        } else {
            return "image/png";
        }
    }

    @Override
    String getBody(String condition) {
        return null;
    }

    private String getImage(String fileName) throws FileNotFoundException {
        File filePath = new File("/Users/Taryn/8thLight/cob_spec/public/" + fileName);
        byte[] fileContents = new byte[(int)filePath.length()];
        FileInputStream fis = new FileInputStream(filePath);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(fileContents);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toString();
    }

}
