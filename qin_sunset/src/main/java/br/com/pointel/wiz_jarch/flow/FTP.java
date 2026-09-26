package br.com.pointel.wiz_jarch.flow;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.LoggerFactory;

/**
 * A robust File Transfer Protocol (FTP) client abstraction that utilizes Apache Commons Net.
 * Simplifies connecting, logging in, uploading, downloading, and managing directories
 * over an FTP connection. Automatically supports Pace logging and execution halting.
 */
public class FTP implements AutoCloseable {

    private final String host;
    private final int port;
    private final String user;
    private final String pass;
    private final Pace pace;
    private final FTPClient client;

    /**
     * Constructs an FTP client for the default port 21.
     *
     * @param host the FTP server hostname or IP
     * @param user the login username
     * @param pass the login password
     */
    public FTP(String host, String user, String pass) {
        this(host, 21, user, pass, null);
    }

    /**
     * Constructs an FTP client for a specific port.
     *
     * @param host the FTP server hostname or IP
     * @param port the FTP server port
     * @param user the login username
     * @param pass the login password
     */
    public FTP(String host, int port, String user, String pass) {
        this(host, port, user, pass, null);
    }

    /**
     * Constructs an FTP client with a custom Pace logger.
     *
     * @param host the FTP server hostname or IP
     * @param port the FTP server port
     * @param user the login username
     * @param pass the login password
     * @param pace the Pace progress and lifecycle tracker
     */
    public FTP(String host, int port, String user, String pass, Pace pace) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.pace = pace != null ? pace : new Pace(LoggerFactory.getLogger(FTP.class));
        this.client = new FTPClient();
    }

    /**
     * @return true if the underlying Apache FTPClient is actively connected
     */
    public boolean isConnected() {
        return client.isConnected();
    }

    /**
     * Initiates the connection, logs in, sets Passive Mode, and configures BINARY transfer mode.
     *
     * @return this FTP instance for method chaining
     * @throws Exception if the connection is refused or login fails
     */
    public FTP connect() throws Exception {
        pace.info("Connecting to " + host + ":" + port);
        client.connect(host, port);
        int reply = client.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            client.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        pace.info("Logging in as " + user);
        if (!client.login(user, pass)) {
            throw new Exception("Exception in logging in to FTP Server");
        }
        client.enterLocalPassiveMode();
        client.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
        return this;
    }

    /**
     * Gracefully logs out and disconnects the active session.
     */
    public void disconnect() {
        if (client.isConnected()) {
            try {
                client.logout();
                client.disconnect();
            } catch (IOException e) {
                pace.error("Error disconnecting", e);
            }
        }
    }

    /**
     * Allows this class to be used in try-with-resources blocks.
     */
    @Override
    public void close() {
        disconnect();
    }

    /**
     * Lists all file and folder names at the given remote path.
     *
     * @param path the remote directory path
     * @return a list of names
     * @throws Exception if an I/O error occurs
     */
    public List<String> listNames(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Listing names in " + path);
        var names = client.listNames(path);
        return names != null ? Arrays.asList(names) : List.of();
    }

    /**
     * Lists detailed file objects (metadata) at the given remote path.
     *
     * @param path the remote directory path
     * @return a list of FTPFiles
     * @throws Exception if an I/O error occurs
     */
    public List<FTPFile> listFiles(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Listing files in " + path);
        var files = client.listFiles(path);
        return files != null ? Arrays.asList(files) : List.of();
    }

    /**
     * Streams a local file up to a remote path.
     *
     * @param local  the physical file to upload
     * @param remote the target filename on the FTP server
     * @throws Exception if an I/O error occurs
     */
    public void upload(File local, String remote) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Uploading " + local.getName() + " to " + remote);
        try (InputStream input = new FileInputStream(local)) {
            if (!client.storeFile(remote, input)) {
                throw new Exception("Could not upload file.");
            }
        }
    }

    /**
     * Downloads a remote file down to a local physical file.
     *
     * @param remote the source filename on the FTP server
     * @param local  the target physical file to save into
     * @throws Exception if an I/O error occurs
     */
    public void download(String remote, File local) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Downloading " + remote + " to " + local.getName());
        try (OutputStream output = new FileOutputStream(local)) {
            if (!client.retrieveFile(remote, output)) {
                throw new Exception("Could not download file.");
            }
        }
    }

    /**
     * Safely changes the working directory to a given path, creating any missing 
     * parent directories sequentially along the way.
     *
     * @param path the target directory path
     * @throws Exception if folder creation or traversal fails
     */
    public void openWithMakeDir(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Checking/Creating directory " + path);
        if (client.changeWorkingDirectory(path)) {
            return;
        }
        if (path.startsWith("/")) {
            client.changeWorkingDirectory("/");
        }
        for (String part : path.split("/")) {
            if (part.isEmpty()) {
                continue;
            }
            if (!client.changeWorkingDirectory(part)) {
                if (!client.makeDirectory(part)) {
                    throw new Exception("Could not create directory: " + part);
                }
                if (!client.changeWorkingDirectory(part)) {
                    throw new Exception("Could not open directory: " + part);
                }
            }
        }
    }

    /**
     * Creates a new directory at the specified path.
     *
     * @param path the directory name
     * @throws Exception if an I/O error occurs
     */
    public void makeDir(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Making directory " + path);
        if (!client.makeDirectory(path)) {
            throw new Exception("Could not create directory.");
        }
    }

    /**
     * Removes an empty directory at the specified path.
     *
     * @param path the directory to remove
     * @throws Exception if the directory does not exist or isn't empty
     */
    public void removeDir(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Removing directory " + path);
        if (!client.removeDirectory(path)) {
            throw new Exception("Could not remove directory.");
        }
    }

    /**
     * Deletes a remote file.
     *
     * @param path the file to delete
     * @throws Exception if an I/O error occurs
     */
    public void removeFile(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Removing file " + path);
        if (!client.deleteFile(path)) {
            throw new Exception("Could not remove file.");
        }
    }

    /**
     * Renames a remote file or folder.
     *
     * @param from the existing name
     * @param to   the new name
     * @throws Exception if an I/O error occurs
     */
    public void rename(String from, String to) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Renaming " + from + " to " + to);
        if (!client.rename(from, to)) {
            throw new Exception("Could not rename file.");
        }
    }

    /**
     * Changes the current working directory to the specified path.
     *
     * @param path the new working directory
     * @throws Exception if the directory doesn't exist
     */
    public void changeDir(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Changing directory to " + path);
        if (!client.changeWorkingDirectory(path)) {
            throw new Exception("Could not change directory.");
        }
    }

    /**
     * Moves the working directory up one level to its parent.
     *
     * @throws Exception if the operation fails
     */
    public void changeToParentDir() throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Changing to parent directory");
        if (!client.changeToParentDirectory()) {
            throw new Exception("Could not change to parent directory.");
        }
    }

    /**
     * @return the absolute path of the current working directory on the server
     * @throws Exception if an I/O error occurs
     */
    public String printWorkingDirectory() throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        return client.printWorkingDirectory();
    }

    /**
     * Checks if a file or directory exists on the server.
     *
     * @param path the path to check
     * @return true if the resource exists
     * @throws Exception if an I/O error occurs
     */
    public boolean exists(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        var names = client.listNames(path);
        if (names != null && names.length > 0) {
            return true;
        }
        try {
            var current = client.printWorkingDirectory();
            if (client.changeWorkingDirectory(path)) {
                client.changeWorkingDirectory(current);
                return true;
            }
        } catch (Exception e) {
            // Ignore
        }
        return false;
    }

    /**
     * Streams data from an input stream up to a remote path.
     *
     * @param input  the source input stream to read from
     * @param remote the target filename on the FTP server
     * @throws Exception if an I/O error occurs
     */
    public void upload(InputStream input, String remote) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Uploading stream to " + remote);
        if (!client.storeFile(remote, input)) {
            throw new Exception("Could not upload stream.");
        }
    }

    /**
     * Streams a remote file down to an output stream.
     *
     * @param remote the source filename on the FTP server
     * @param output the target output stream to write into
     * @throws Exception if an I/O error occurs
     */
    public void download(String remote, OutputStream output) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Downloading " + remote + " to stream");
        if (!client.retrieveFile(remote, output)) {
            throw new Exception("Could not download file.");
        }
    }

    /**
     * Uploads a String to a remote path.
     *
     * @param content the String content to upload
     * @param remote  the target filename on the FTP server
     * @throws Exception if an I/O error occurs
     */
    public void uploadString(String content, String remote) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Uploading String to " + remote);
        try (InputStream input = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))) {
            if (!client.storeFile(remote, input)) {
                throw new Exception("Could not upload String.");
            }
        }
    }

    /**
     * Downloads a remote file as a String.
     *
     * @param remote the source filename on the FTP server
     * @return the content of the remote file as a String
     * @throws Exception if an I/O error occurs
     */
    public String downloadString(String remote) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Downloading " + remote + " as String");
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            if (!client.retrieveFile(remote, output)) {
                throw new Exception("Could not download file to String.");
            }
            return output.toString(StandardCharsets.UTF_8);
        }
    }

    /**
     * Appends a local file to a remote file.
     *
     * @param local  the physical file to append
     * @param remote the target filename on the FTP server
     * @throws Exception if an I/O error occurs
     */
    public void append(File local, String remote) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Appending " + local.getName() + " to " + remote);
        try (InputStream input = new FileInputStream(local)) {
            if (!client.appendFile(remote, input)) {
                throw new Exception("Could not append file.");
            }
        }
    }

    /**
     * Appends data from an input stream to a remote file.
     *
     * @param input  the source input stream to read from
     * @param remote the target filename on the FTP server
     * @throws Exception if an I/O error occurs
     */
    public void append(InputStream input, String remote) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Appending stream to " + remote);
        if (!client.appendFile(remote, input)) {
            throw new Exception("Could not append stream.");
        }
    }

    /**
     * Appends a String to a remote file.
     *
     * @param content the String content to append
     * @param remote  the target filename on the FTP server
     * @throws Exception if an I/O error occurs
     */
    public void appendString(String content, String remote) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Appending String to " + remote);
        try (InputStream input = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))) {
            if (!client.appendFile(remote, input)) {
                throw new Exception("Could not append String.");
            }
        }
    }

    /**
     * Retrieves the size of a remote file.
     *
     * @param remote the filename on the FTP server
     * @return the size of the file in bytes, or -1 if the file does not exist
     * @throws Exception if an I/O error occurs
     */
    public long getSize(String remote) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Getting size of " + remote);
        FTPFile[] files = client.listFiles(remote);
        if (files != null && files.length > 0) {
            return files[0].getSize();
        }
        return -1;
    }

    /**
     * Retrieves the modification time of a remote file.
     *
     * @param remote the filename on the FTP server
     * @return the modification time as a Calendar object, or null if it cannot be retrieved
     * @throws Exception if an I/O error occurs
     */
    public Calendar getModificationTime(String remote) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Getting modification time of " + remote);
        FTPFile[] files = client.listFiles(remote);
        if (files != null && files.length > 0) {
            return files[0].getTimestamp();
        }
        return null;
    }

    /**
     * Sends a NOOP command to the FTP server. This is useful for keeping the connection alive.
     *
     * @throws Exception if an I/O error occurs
     */
    public void sendNoOp() throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Sending NOOP");
        if (!client.sendNoOp()) {
            throw new Exception("Could not send NOOP.");
        }
    }

    /**
     * Recursively deletes a remote directory and all its contents.
     *
     * @param path the remote directory path
     * @throws Exception if an I/O error occurs or a file/directory cannot be deleted
     */
    public void removeDirRecursively(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Recursively removing directory " + path);
        List<FTPFile> files = listFiles(path);
        for (FTPFile file : files) {
            if (file.getName().equals(".") || file.getName().equals("..")) {
                continue;
            }
            String filePath = path.endsWith("/") ? path + file.getName() : path + "/" + file.getName();
            if (file.isDirectory()) {
                removeDirRecursively(filePath);
            } else {
                removeFile(filePath);
            }
        }
        removeDir(path);
    }

    /**
     * Recursively uploads a local directory to a remote directory.
     *
     * @param localDir  the local directory to upload
     * @param remoteDir the target remote directory
     * @throws Exception if an I/O error occurs
     */
    public void uploadDirRecursively(File localDir, String remoteDir) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Recursively uploading directory " + localDir.getName() + " to " + remoteDir);
        
        String currentDir = printWorkingDirectory();
        
        if (!exists(remoteDir)) {
            openWithMakeDir(remoteDir);
        }
        changeDir(currentDir);

        File[] files = localDir.listFiles();
        if (files != null) {
            for (File file : files) {
                String remoteFilePath = remoteDir.endsWith("/") ? remoteDir + file.getName() : remoteDir + "/" + file.getName();
                if (file.isDirectory()) {
                    uploadDirRecursively(file, remoteFilePath);
                } else {
                    upload(file, remoteFilePath);
                }
            }
        }
    }

    /**
     * Recursively downloads a remote directory to a local directory.
     *
     * @param remoteDir the remote directory to download
     * @param localDir  the target local directory
     * @throws Exception if an I/O error occurs
     */
    public void downloadDirRecursively(String remoteDir, File localDir) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Recursively downloading directory " + remoteDir + " to " + localDir.getName());

        if (!localDir.exists()) {
            if (!localDir.mkdirs()) {
                throw new Exception("Could not create local directory: " + localDir.getAbsolutePath());
            }
        }

        List<FTPFile> files = listFiles(remoteDir);
        for (FTPFile file : files) {
            if (file.getName().equals(".") || file.getName().equals("..")) {
                continue;
            }
            String remoteFilePath = remoteDir.endsWith("/") ? remoteDir + file.getName() : remoteDir + "/" + file.getName();
            File localFile = new File(localDir, file.getName());

            if (file.isDirectory()) {
                downloadDirRecursively(remoteFilePath, localFile);
            } else {
                download(remoteFilePath, localFile);
            }
        }
    }

    /**
     * Provides access to the raw Apache Commons FTPClient instance.
     *
     * @return the underlying FTPClient
     */
    public FTPClient getClient() {
        return client;
    }

}
