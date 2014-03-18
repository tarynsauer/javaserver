package tddserver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.copyOfRange;
import static javaserver.JavaserverConstants.DIRECTORY_PATH;

/**
 * Created by Taryn on 3/12/14.
 */
public class BodyGenerator {
    private RequestParser parser;
    private static List<Page> pages = new ArrayList<Page>();

    public BodyGenerator(RequestParser parser) {
        generateSitePages();
        this.parser = parser;
    }

    public List<Page> getPages() {
        return pages;
    }

    public byte[] addBodyToResponse(StringBuilder builder) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if ((parser.containsHeader("Range"))) {
            byte[] partialResponse = getPartialResponse(builder);
            outputStream.write(partialResponse);
        } else if (getContentType().equals("text/plain") || isFile()) {
            builder = getFileContents(builder);
        } else if (getContentType().startsWith("image/")) {
            writeImageToResponse(outputStream, builder);
        } else {
            builder.append(displayBody());
        }
        outputStream.write(builder.toString().getBytes());
        outputStream.close();
        return outputStream.toByteArray();
    }

    public boolean isFile() {
        String name = parser.getFileName();
        return (name.equals("file1") || name.equals("file2"));
    }

    public String getContentType() {
        String ext = parser.getFileExtension();
        if (ext.equals(".txt")) {
            return "text/plain";
        } else if (ext.equals(".jpg") || ext.equals(".jpeg")) {
            return "image/jpeg";
        } else if (ext.equals(".gif")) {
            return "image/gif";
        } else if (ext.equals(".png")) {
            return "image/png";
        } else {
            return "text/html";
        }
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

    private StringBuilder getFileContents(StringBuilder builder) {
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
        return builder;
    }

    private String displayBody() throws UnsupportedEncodingException {
        return "<html><title>Taryn's Website</title><body>" + getPageBody() + "</body></html>";
    }

    private byte[] getPartialResponse(StringBuilder builder) throws IOException {
        byte[] contents = getFileContents(builder).toString().getBytes();
        if (parser.getRange() == null) {
            return contents;
        } else {
            return copyOfRange(contents, parser.getBeginRange(), parser.getEndRange());
        }
    }

    private String getPageBody() throws UnsupportedEncodingException {
        List<Page> pagesList = getPages();
        for(Page page : pagesList) {
            String uri = page.getUri();
            if (uri.equals(parser.getUri())) {
                return page.getContent(parser);
            }
        }
        return "";
    }

    private void generateSitePages() {
        RootPage root = new RootPage("/");
        pages.add(root);
        LogsPage logs = new LogsPage("/logs");
        pages.add(logs);
        addPage("/method_options", "<h1>These are your options</h1>");
        addPage("/log", "<h1>Log</h1><p>Logging...</p>");
        addPage("/these", "<h1>These</h1><p>Logging...</p>");
        addPage("/requests", "<h1>Requests</h1><p>Logging...</p>");
        addPage("/foobar", "<h1>404 Not Found</h1><p>We don't know what you're looking for.</p>");
        ParametersPage paramsPage = new ParametersPage("/parameters");
        pages.add(paramsPage);
        FormPage form = new FormPage("/form");
        pages.add(form);
    }

    private void addPage(String uri, String content) {
        Page page = new Page(uri);
        page.setContent(content);
        pages.add(page);
    }

}