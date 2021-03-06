package javaserver;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static javaserver.JavaserverConstants.DIRECTORY_PATH;
import static javaserver.HTTPStatusConstants.OK;
/**
 * Created by Taryn on 3/6/14.
 */
public class ImageResponse extends AbstractResponse {

    @Override
    public byte[] getResponseMessage(RequestParser parser) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getStatusLine(OK));
        stringBuilder.append(getDateInfo());
        stringBuilder.append(getServerInfo());
        stringBuilder.append(getContentTypeInfo(parser));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            outputStream.write(stringBuilder.toString().getBytes());
            outputStream.write(getImage(parser.getRequestedFileName()));
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    private byte[] getImage(String fileName) throws IOException {
        File filePath = new File(DIRECTORY_PATH + fileName);
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