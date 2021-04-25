package com.poetry.admin.qiniu;


public abstract class FilePathGenerator {

    public abstract String get(FileType fileType);


    public static enum FileType {
        IMAGE("img", "png", "/img/"),
        WORD("word", "docx", "/docx/"),
        EXCEL("excel","xlsx", "/xlsx/"),
        PPT("ppt","pptx", "/pptx/"),
        TXT("txt","txt","/txt/"),
        PDF("pdf","pdf","/pdf/"),
        ZIP("zip","zip","/zip/"),
        RAR("rar","rar","/rar/"),
        FILE("file","file", "/file/");

        private String ftype;
        private String extension;
        private String path;

        FileType(String type, String ext, String p) {
            ftype = type;
            extension = ext;
            path = p;
        }


        public String getFtype() {
            return ftype;
        }

        public String getExtension() {
            return this.extension;
        }

        public String getPath() {
            return this.path;
        }

        /**
         * 根据文件后缀,获取内置的文件类型
         * @param extension
         * @return
         */
        public static FileType getFileTypeByExtension(String extension) {
            if(null == extension){
                return FILE;
            } else if(extension.startsWith(".")){
                extension = extension.substring(1);
            }
            if ("png,jpg,jpeg,bmp,gif,tiff,raw".contains(extension.toLowerCase())) {
                return IMAGE;
            } else if("doc, docx, dot".contains(extension.toLowerCase())){
                return WORD;
            }else if("ppt, pptx".contains(extension.toLowerCase())){
                return PPT;
            }else if("xls, xlsx".contains(extension.toLowerCase())){
                return EXCEL;
            } else if("txt".contains(extension.toLowerCase())){
                return TXT;
            } else if("pdf".contains(extension.toLowerCase())){
                return PDF;
            }else if("zip".contains(extension.toLowerCase())){
                return ZIP;
            }else if("rar".contains(extension.toLowerCase())){
                return RAR;
            }
            return FILE;

        }
    }
}
