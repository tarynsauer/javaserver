package tddserver;

import java.io.*;

import static java.util.Arrays.copyOfRange;
import static javaserver.JavaserverConstants.DIRECTORY_PATH;

/**
 * Created by Taryn on 3/12/14.
 */
public class BodyGenerator {
    private RequestParser parser;

    public BodyGenerator(RequestParser parser) {
        this.parser = parser;
    }

    public byte[] addBodyToResponse(StringBuilder builder, String bodyContents) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if (parser.getFileExtension().equals("text/plain")) {
            getFileContents(builder);
        } else if (parser.getFileExtension().startsWith("image/")) {
            writeImageToResponse(outputStream, builder);
        } else if (parser.containsHeader("Range")) {
            getPartialResponse(builder);
        } else {
            builder.append(displayBody(bodyContents));
        }
        return outputStream.toByteArray();
    }

    private OutputStream writeImageToResponse(OutputStream outputStream, StringBuilder builder) {
        try {
            outputStream.write(builder.toString().getBytes());
            outputStream.write(getImageContents());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }

    private byte[] getImageContents() throws IOException {
        File filePath = new File(DIRECTORY_PATH + parser.getFileName());
        byte[] fileData = new byte[(int)filePath.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            fileInputStream.read(fileData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileData;
    }

    private String getFileContents(StringBuilder builder) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(DIRECTORY_PATH + parser.getFileName());
            int content;
            while ((content = fis.read()) != -1) {
                builder.append((char)content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return builder.toString();
    }

    private String displayBody(String bodyContents) {
        return "<html><title>Taryn's Website</title><body>" + bodyContents + "</body></html>";
    }

    private byte[] getPartialResponse(StringBuilder builder) throws IOException {
        byte[] contents = getFileContents(builder).getBytes();
        if (parser.getRange() == null) {
            return contents;
        } else {
            return copyOfRange(contents, parser.getBeginRange(), parser.getEndRange());
        }
    }

}