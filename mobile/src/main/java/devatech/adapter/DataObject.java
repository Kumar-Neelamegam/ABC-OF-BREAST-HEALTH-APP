package devatech.adapter;


public   class DataObject {
    public  String getFileId() {
        return FileId;
    }

    public  void setFileId(String fileId) {
        FileId = fileId;
    }

    public  String getLanguageID() {
        return LanguageID;
    }

    public  void setLanguageID(String languageID) {
        LanguageID = languageID;
    }

    public  String getFile_Name() {
        return File_Name;
    }

    public  void setFile_Name(String file_Name) {
        File_Name = file_Name;
    }

    public  String getFile_Path() {
        return File_Path;
    }

    public  void setFile_Path(String file_Path) {
        File_Path = file_Path;
    }

    public  String getFile_Size() {
        return File_Size;
    }

    public  void setFile_Size(String file_Size) {
        File_Size = file_Size;
    }

    public  String getFileTypeID() {
        return FileTypeID;
    }

    public  void setFileTypeID(String fileTypeID) {
        FileTypeID = fileTypeID;
    }

    public  String getFileCategoryID() {
        return FileCategoryID;
    }

    public  void setFileCategoryID(String fileCategoryID) {
        FileCategoryID = fileCategoryID;
    }

    public  String FileId;
    public  String LanguageID;
    public  String File_Name;
    public  String File_Path;
    public  String File_Size;
    public  String FileTypeID;
    public  String FileCategoryID;
    public  String DownloadStatus;

    public String getDownloadStatus() {
        return DownloadStatus;
    }

    public void setDownloadStatus(String downloadStatus) {
        DownloadStatus = downloadStatus;
    }
}