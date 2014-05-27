/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sg.server;
//
import com.customlib.seriousgame.client.DbTables;
import com.customlib.seriousgame.client.DbTablesSpecialColums;
import com.customlib.seriousgame.client.Queries;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.models.Attachment;
import com.customlib.seriousgame.client.models.ModelTag;
import com.customlib.seriousgame.server.DataBaseInteraction;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

public class FileUploadServlet extends HttpServlet {

    //private static final String UPLOAD_DIRECTORY = "d:\\uploaded\\";
    //private static final String zteBooksDir = "D:\\Tomcat 6.0\\webapps\\ZTE2.1\\books";
    //private static final String zteBooksDir = "D:\\Tomcat 6.0\\webapps\\ZTE_BackEnd_2.0\\contents\\pictures\\books";;
    
    public FileUploadServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (ServletFileUpload.isMultipartContent(req)) {                
                // Create a factory for disk-based file items
                FileItemFactory factory = new DiskFileItemFactory();
                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);
                // Parse the request
                try {
                    List<FileItem> items = upload.parseRequest(req);
                    for (FileItem item : items) {
                        // process only file upload - discard other form item types
                        if (item.isFormField()) {
                            continue;
                        }

                        //D:\Tomcat 6.0\webapps\ZTE2.1
                        //D:\Tomcat 6.0\webapps\ZTE_BackEnd_2.0
                        String uploadFolder = getServletContext().getRealPath("") + File.separator + ".." + File.separator + Utilities.uploadedFolder;
//                    
                        String fileNameextension = item.getName();
                        String fileName = "";
                        if (fileNameextension.length() == 0) {
                            continue;
                        }
                        // get only the file name not whole path
                        if (fileNameextension != null) {
                            fileNameextension = FilenameUtils.getExtension(fileNameextension);
                        }
                        if (item.getFieldName().contains("_$_")) {
                            fileName = getUploadedFileName(item.getFieldName());
                            //uploadFolder = realPath + "ZTE2.1\\books";
                            uploadFolder = getServletContext().getRealPath("") + File.separator + Utilities.uploadedFolder;
                        }
                        File dir = new File(uploadFolder);
                        if(!dir.exists())
                            dir.mkdir();


                        File uploadedFile = new File(uploadFolder, fileName);
                        if (uploadedFile.exists()) {
                            try {
                                DataOutputStream outs = new DataOutputStream(new FileOutputStream(uploadedFile, false));
                                outs.write(item.get());
                                outs.close();
                            } catch (FileNotFoundException e) {
                                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                                        "An error occurred while creating the file : " + e.getMessage());
                            }
                        } else {
                            if (uploadedFile.createNewFile()) {
                                item.write(uploadedFile);
                            } else {
                                throw new IOException("The file already exists in repository.");
                            }
                        }
                        Attachment temp = getAttachment(item.getFieldName());
                        if(temp == null)
                        {
                            System.out.println("Can't extract file info.");
                            return;
                        }
                        int insert = DataBaseInteraction.executeSql(Queries.insertAttachment((String)temp.get(ModelTag.name.toString()), (String)temp.get(ModelTag.originalName.toString()), (Integer)temp.get(DbTablesSpecialColums.seriousGameID.toString())));
                        if(insert == 1)
                        {
                            int newId = DataBaseInteraction.getIdFromKeys(Queries.getIdFromKey(DbTables.attachments.toString(), 
                                    ModelTag.name.toString(), (String)temp.get(ModelTag.name.toString())));
                            temp.set(ModelTag.id.toString(), newId);
                            resp.getWriter().print(temp.toString());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred while creating the file : " + e.getMessage());
                }

            } else {
                System.out.println("Request contents type is not supported by the servlet.");
            }

        } catch (Exception e) {
            System.out.println("Errore nel caricamento file: " + e.getMessage());
        }
    }

    private String getUploadedFileName(String info) {
        if (info.startsWith("file")) {
            info = info.replaceAll("file_\\$_", "");
        }
        if (info.length() > 5) {
            String extension = info.substring(info.length() - 4);
            String[] words = info.substring(0, info.length() - 4).split("_\\$_");

            if (words.length != 4) {
                return "";
            }
            java.util.Date date = new java.util.Date(Long.parseLong(words[1]));
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy_HHmmss");
            String dateString = df.format(date);
            String filname = words[0] + "_$_" + dateString + "_$_" + words[2] + "_$_" + words[3] + extension;
            return filname;
        }
        return "";
    }
    private Attachment getAttachment(String info) {
        if (info.startsWith("file")) {
            info = info.replaceAll("file_\\$_", "");
        }
        if (info.length() > 5) {
            String extension = info.substring(info.length() - 4);
            String[] words = info.substring(0, info.length() - 4).split("_\\$_");

            if (words.length != 4) {
                return null;
            }
            java.util.Date date = new java.util.Date(Long.parseLong(words[1]));
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy_HHmmss");
            String dateString = df.format(date);
            String filname = words[0] + "_$_" + dateString + "_$_" + words[2] + "_$_" + words[3] + extension;
            return new Attachment(-1, Integer.parseInt(words[0]), filname, words[2] + extension);
        }
        return null;
    }
}
