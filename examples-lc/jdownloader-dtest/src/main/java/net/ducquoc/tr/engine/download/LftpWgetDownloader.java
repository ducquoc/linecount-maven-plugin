package net.ducquoc.tr.engine.download;

import java.io.*;
import java.net.*;

/**
 * Downloader.
 *
 * @since 2.0
 */
public class LftpWgetDownloader {

    public static long download(String downloadUrl, String saveAsFileName) throws IOException, URISyntaxException {

        File outputFile = new File(saveAsFileName);
        URLConnection downloadFileConnection = new URI(downloadUrl).toURL()
                .openConnection();
        return transferDataAndCountBytes(downloadFileConnection, outputFile);
    }

    private static long transferDataAndCountBytes(URLConnection downloadFileConnection, File outputFile) throws IOException {

        long bytesDownloaded = 0;
        try (InputStream is = downloadFileConnection.getInputStream(); OutputStream os = new FileOutputStream(outputFile, true)) {

            byte[] buffer = new byte[1024];

            int bytesCount;
            while ((bytesCount = is.read(buffer)) > 0) {
                os.write(buffer, 0, bytesCount);
                bytesDownloaded += bytesCount;
            }
        }
        return bytesDownloaded;
    }

    public static long downloadWithResume(String downloadUrl, String saveAsFileName) throws IOException, URISyntaxException {
        File outputFile = new File(saveAsFileName);

        URLConnection downloadFileConnection = addResumableDownload(downloadUrl, outputFile);
        return transferDataAndCountBytes(downloadFileConnection, outputFile);
    }

    private static URLConnection addResumableDownload(String downloadUrl, File outputFile) throws IOException, URISyntaxException, ProtocolException, ProtocolException {
        long existingFileSize = 0L;
        URLConnection downloadFileConnection = new URI(downloadUrl).toURL()
                .openConnection();

        if (outputFile.exists() && downloadFileConnection instanceof HttpURLConnection) {
            HttpURLConnection httpFileConnection = (HttpURLConnection) downloadFileConnection;

            HttpURLConnection tmpFileConn = (HttpURLConnection) new URI(downloadUrl).toURL()
                    .openConnection();
            tmpFileConn.setRequestMethod("HEAD");
            long fileLength = tmpFileConn.getContentLengthLong();
            existingFileSize = outputFile.length();

            if (existingFileSize < fileLength) {
                httpFileConnection.setRequestProperty("Range", "bytes=" + existingFileSize + "-" + fileLength);
            } else {
                throw new IOException("File Download already completed.");
            }
        }
        return downloadFileConnection;
    }

    public static Long downAnimation(String webLink, String... saveAsFileNames) {
        long downloadedBytes = 0;
        try {
            String fileNameCurrentDir = saveAsFileNames != null && saveAsFileNames.length > 0 ? saveAsFileNames[0]
                    : webLink.lastIndexOf("/") >= 0 ? webLink.substring(webLink.lastIndexOf("/") + 1)
                    : "renameMe.txt";
            downloadedBytes = //LftpWgetDownloader.downloadWithResume(webLink, fileNameCurrentDir);
                    LftpWgetDownloader.download(webLink, fileNameCurrentDir);
        } catch (Exception ex) {
            System.out.println("Failed to download: " + ex.getMessage());
        }
        return downloadedBytes;
    }

//    public static void main(String[] args) throws Exception {
//
//        long sizeInBytes = downAnimation("https://ducquoc.github.io/elsa/images/pic03.jpg");
//        System.out.println("downloaded bytes: " + sizeInBytes);
//    }

}
