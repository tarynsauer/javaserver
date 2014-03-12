package javaserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static java.util.Arrays.copyOfRange;
import static javaserver.HTTPStatusConstants.PARTIAL_RESPONSE;
import static javaserver.JavaserverConstants.DIRECTORY_PATH;
/**
 * Created by Taryn on 3/7/14.
 */
public class PartialResponse extends RootResponse {

    @Override
    public byte[] getResponseMessage(RequestParser parser) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getStatusLine(PARTIAL_RESPONSE));
        stringBuilder.append(getDateInfo());
        stringBuilder.append(getServerInfo());
        stringBuilder.append(getContentTypeInfo("text/plain"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            outputStream.write(stringBuilder.toString().getBytes());
            outputStream.write(getPartialResponse(parser));
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public byte[] getPartialResponse(RequestParser parser) throws IOException {
        String uri = parser.getRequestedFileName();
        byte[] contents = getFileContents(DIRECTORY_PATH + uri).getBytes();
        if (getRange(parser) == null) {
            return contents;
        } else {
            return copyOfRange(contents, getBeginRange(parser), getEndRange(parser));
        }
    }

    public static int getBeginRange(RequestParser parser) throws IOException {
        return Integer.parseInt(getRange(parser)[0]);
    }

    public static int getEndRange(RequestParser parser) throws IOException {
        return (Integer.parseInt(getRange(parser)[1]));
    }

    private static String[] getRange(RequestParser parser) throws IOException {
        String rangeParts = parser.getHeaderValue("Range");
        if (rangeParts == null) {
            return null;
        }

        String[] rangeList = rangeParts.split("=");
        if (rangeList.length > 1) {
            return rangeList[1].split("-");
        } else {
            return rangeList;
        }
    }
}
