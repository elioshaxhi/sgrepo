/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.customlib.seriousgame.client.forms;

import com.customlib.seriousgame.client.Utilities;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.google.gwt.i18n.client.DateTimeFormat;
import java.util.Date;

/**
 *
 * @author NERTIL
 */
public class FileBrowser extends FileUploadField {

    private String generatedFileName = "";
    int ownerId = 0;
    String prefix = "";


    public FileBrowser(int ownerId, String prefix) {
        super();
        this.ownerId = ownerId;
        this.prefix = prefix;
    }

    public void generateImageFileName() {
        String fileName = this.getValue();
        if (this.getValue() != null && this.getValue().length() > 0) {
            fileName = fileName.replace('\\', '/');
            String[] words = fileName.split("/");
            fileName = words[words.length - 1];
            Date today = new Date();
            DateTimeFormat formater = DateTimeFormat.getFormat("dd-MM-yyyy_HHmmss");
            String fileInfo = "";
            if(prefix.length() > 0)
                fileInfo += prefix + Utilities.fileNameseparator;
            fileInfo += today.getTime() + Utilities.fileNameseparator
                    + fileName.substring(0, fileName.length() - 4) + Utilities.fileNameseparator
                    + ownerId + Utilities.fileNameseparator + fileName.substring(fileName.length() - 4);
            setName(fileInfo);
            generatedFileName = formater.format(today) + Utilities.fileNameseparator
                    + fileName.substring(0, fileName.length() - 4) + Utilities.fileNameseparator
                    + fileName.substring(fileName.length() - 4);
        }
    }

    /**
     * @return the generatedFileName
     */
    public String getGeneratedFileName() {
        return generatedFileName;
    }
    public void setGeneratedFileName(String value)
    {
        generatedFileName = value;
    }
}
