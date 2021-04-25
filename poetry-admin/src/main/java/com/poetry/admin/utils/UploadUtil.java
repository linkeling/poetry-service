//package com.match.making.utils;
//
//import com.google.common.io.ByteStreams;
//import com.match.making.param.output.PicturesInfoDTO;
//import com.qiniu.common.Zone;
//import com.qiniu.http.Response;
//import com.qiniu.storage.Configuration;
//import com.qiniu.storage.UploadManager;
//import com.qiniu.util.Auth;
//import org.apache.commons.io.FilenameUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.UUID;
//
//@Component
//public class UploadUtil {
//
//
//    private static Configuration cfg = new Configuration(Zone.zone0());
//    private static UploadManager uploadManager = new UploadManager(cfg);
//
//    @Value("${ybs.fileupload.bucket-name}")
//    private String privateBucketName;
//
//    @Value("${ybs.fileupload.access-key}")
//    private String ACCESS_KEY;
//
//    @Value("${ybs.fileupload.secret-key}")
//    private String SECRET_KEY;
//
//    public static String bucketName;
//    public static String accessKey;
//    public static String secretKey;
//
//    @PostConstruct
//    public void init() {
//        bucketName = privateBucketName;
//        accessKey = ACCESS_KEY;
//        secretKey = SECRET_KEY;
//    }
//
//    /**
//     * 上传文件（图片）
//     *
//     * @param multipartFile
//     * @return
//     */
//    public static PicturesInfoDTO uploadImage(MultipartFile multipartFile) {
//        try {
//            if (multipartFile.getSize() != 0L) {
//                Auth auth = Auth.create(accessKey, secretKey);
//                String token = auth.uploadToken(bucketName);
//                byte[] byteData = ByteStreams.toByteArray(multipartFile.getInputStream());
//                String fileName = defFileNamme(multipartFile.getOriginalFilename());
//                String path = GenerateKey(multipartFile);
//                String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
//                Response response = uploadManager.put(byteData, path, token, null, null, false);
//                if (response.isOK()) {
//                    PicturesInfoDTO obj = new PicturesInfoDTO();
//                    obj.setUuid(UUIDUtils.create());
//                    //华为云
//                    obj.setServerId(1);
//                    obj.setDownloadUrl(path);
//                    obj.setSize(byteData.length);
//                    obj.setMd5Hash(EncryptUtil.digestMD5(byteData));
//                    obj.setSha1Hash(EncryptUtil.digestSHA1(byteData));
//                    obj.setFileName(fileName);
//                    String fileExtension = FilenameUtils.getExtension(fileName).toLowerCase();
//                    obj.setFileType(FilePathGenerator.FileType.getFileTypeByExtension(fileExtension).getFtype());
//                    obj.setParentId("");
//                    obj.setDescription("物资图片");
//                    obj.setTags(prefix);
//                    obj.setValidStatus(0);
//                    return obj;
//                } else {
//                    return null;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private static String defFileNamme(String filename) {
//        if (StringUtils.isEmpty(filename)) {
//            return UUID.randomUUID().toString() + ".JPG";
//        } else {
//            return filename;
//        }
//    }
//
//    /**
//     * 上传文件（非图片）
//     *
//     * @param multipartFile
//     * @return
//     */
//    public static PicturesInfoDTO uploadFile(MultipartFile multipartFile) {
//        try {
//            if (multipartFile.getSize() != 0L) {
//                Auth auth = Auth.create(accessKey, secretKey);
//                String token = auth.uploadToken(bucketName);
//                byte[] byteData = ByteStreams.toByteArray(multipartFile.getInputStream());
//                String fileName = defFileNamme(multipartFile.getOriginalFilename());
//                String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
//                String url = GenerateKeyUnImg(multipartFile);
//                Response response = uploadManager.put(byteData, url, token, null, null, false);
//                if (response.isOK()) {
//                    PicturesInfoDTO obj = new PicturesInfoDTO();
//                    obj.setUuid(UUIDUtils.create());
//                    //华为云
//                    obj.setServerId(1);
//                    obj.setDownloadUrl(url);
//                    obj.setSize(byteData.length);
//                    obj.setMd5Hash(EncryptUtil.digestMD5(byteData));
//                    obj.setSha1Hash(EncryptUtil.digestSHA1(byteData));
//                    obj.setFileName(fileName);
//                    String fileExtension = FilenameUtils.getExtension(fileName).toLowerCase();
//                    obj.setFileType(FilePathGenerator.FileType.getFileTypeByExtension(fileExtension).getFtype());
//                    obj.setParentId("");
//                    obj.setDescription("营养包附件");
//                    obj.setTags(prefix);
//                    obj.setValidStatus(0);
//                    return obj;
//                } else {
//                    return null;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    public static String GenerateKey(MultipartFile multipartFile) {
//        StringBuffer keyName = new StringBuffer();
//        LocalDate localDate = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        String date = localDate.format(formatter);
//
//        keyName.append("MDS/").append(date).append("/").append(String.valueOf(System.currentTimeMillis()) + (int) ((Math.random() * 9 + 1) * 100000)).append(".").append((multipartFile.getContentType().split("/")[1]));
//        return keyName.toString();
//    }
//
//
//    /**
//     * 获取图片外后的文件名
//     *
//     * @param multipartFile
//     * @return
//     */
//    public static String GenerateKeyUnImg(MultipartFile multipartFile) {
//        StringBuffer keyName = new StringBuffer();
//        LocalDate localDate = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        String date = localDate.format(formatter);
//
//        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1, multipartFile.getOriginalFilename().length());
//        keyName.append("MDS/").append(date).append("/").append(String.valueOf(System.currentTimeMillis()) + (int) ((Math.random() * 9 + 1) * 100000)).append(".").append((suffix));
//        return keyName.toString();
//    }
//
//    public static String GenerateKey(String fileSuffix) {
//        StringBuffer keyName = new StringBuffer();
//        LocalDate localDate = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        String date = localDate.format(formatter);
//        keyName.append("MDS/").append(date).append("/").append(String.valueOf(System.currentTimeMillis()) + (int) ((Math.random() * 9 + 1) * 100000)).append(fileSuffix);
//        return keyName.toString();
//    }
//}
