package javaserver;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
/**
 * Created by Taryn on 3/6/14.
 */
public class ImageResponse extends AbstractResponse {

    @Override
    byte[] getResponseMessage(String status, String fileName) throws IOException {
        String contentType = getContentTypeString(fileName);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getStatusLine(status));
        stringBuilder.append(getDateInfo());
        stringBuilder.append(getServerInfo());
        stringBuilder.append(getContentTypeInfo(contentType));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(stringBuilder.toString().getBytes());
            outputStream.write(getImage(fileName));
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
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

    private byte[] getImage(String fileName) throws IOException {
        File filePath = new File("/Users/Taryn/8thLight/cob_spec/public/" + fileName);
        byte[] fileData = new byte[(int)filePath.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            fileInputStream.read(fileData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileData;

    }

}
