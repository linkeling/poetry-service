package com.poetry.admin.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 加密工具类
 */
public class EncryptUtil {

    public static String calcFingerprint(String userName, String ts, String from) {
        if(null == userName){userName = "";}
        if(null == ts){ts = "";}
        if(null == from){from = "";}
        //
        String result = digestMD5(userName + ts + from);
        result = result.toUpperCase();
        if(result.length()>0){
            return result;// 兼容dot-net;避免hash方法
        }
        int hashCode = result.hashCode();
        String hex = Integer.toHexString(hashCode);
        hex = hex.toUpperCase();
        //
        return hex;
    }

    // [SHA-1, SHA1, SHA-384, OID.1.3.14.3.2.26, 2.16.840.1.101.3.4.2.2, SHA, 2.16.840.1.101.3.4.2.1, 2.16.840.1.101.3.4.2.4, 2.16.840.1.101.3.4.2.3, OID.2.16.840.1.101.3.4.2.4, OID.2.16.840.1.101.3.4.2.3, OID.2.16.840.1.101.3.4.2.2, 1.3.14.3.2.26, OID.2.16.840.1.101.3.4.2.1, SHA-224, SHA-256, MD2, SHA-512, MD5]
    public static String digestMD5(String text) {
        return digest("MD5", text);
    }

    public static String digestMD5(byte[] bytes) {
        return digest("MD5", bytes);
    }

    public static String digestSHA1(String text) {
        return digest("SHA-1", text);
    }
    public static String digestSHA1(byte[] bytes) {
        return digest("SHA-1", bytes);
    }


    public static String digest(String algorithm, String text) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance(algorithm);
            digest.update(text.getBytes());
            byte[] messageDigest = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String digest(String algorithm, byte[] bytes) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance(algorithm);
            digest.update(bytes);
            byte[] messageDigest = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 自定义加密方法，不提供解密，注意Key不泄露
     * @param text
     * @return
     */
    public static String EncryptByCustomer(String text)
    {
        int maxLen=36;
        if (text == null || "".equals(text.trim()))
            return "";
        if(text.length()>maxLen)
            throw new RuntimeException("加密字符串长度不能超过36位");
        String key = "uirehuhgi45767fhsvnamni2$@()()3954misfdkmbjzpvpreuYgrY(**j439@54398jCNKE667398()@89jh()*(her34cn*(4398klfdske54ptu54yups$*(*(9034HU^S~)uuguertgregjq35$*(*y654o89ufvncxzvbawuifahot*CHquty7thfsdngjfaDHJUYpdofgGM8W9468DFMmg945vdflvq3894348mfhdsakh2347vzknbeopore5543n8gnigoumfya*()jgfdvmdavhlm45896kdfjaiugerut$ywnotmif5dahf9mv**(()()dmaio4haug3q78ao3h8qttg$^*javdfnbrq3pon345377c";
        String addValue="874nr8c7n67BRBE4nf776Wnfwmfo8NR32uudm*N@me98me9m8y@MY9dmw9mqp9329mszlm,.moi3qu4483678chuheliuwqhuiornbavee763@$DWFWEIGFieucmepq343=ignvuw7167E$V^732n86nRBmfenv7cynv3MTr38cme786BBCNEMWIQPOIV^$NMCdfwrhg8^^3953409MVIUHOPESLEVHNCDjkmug";
        //长度除以第一位的余数的奇偶校验
        int keyLen=key.length();
        int textLen=text.length();
        int addLen=addValue.length();
        int checkInt = ((int)text.charAt(0)*(int)text.charAt(textLen-1)+textLen) % 10 + 1;
        checkInt = (text.length() % checkInt) % 3 + 1;
        StringBuilder str = new StringBuilder();
        StringBuilder strLeft = new StringBuilder();//左边补全
        StringBuilder strRight = new StringBuilder();//右边补全

        int keyIndex=0;
        //补全数据为36位
        if(textLen<maxLen) {
            //左边补全位数为第一位数*最后一位数，再余上需要补全数
            int leftLen = ((int) text.charAt(0) * (int) text.charAt(text.length() - 1)) % (maxLen - textLen);
            for(int i=0;i<maxLen-textLen;i++){
                if(i<leftLen)
                    strLeft.append(key.charAt(((int)addValue.charAt(((i+1)*checkInt)%addLen)%(keyLen/3))* checkInt));
                else
                    strRight.append(key.charAt(((int)addValue.charAt(((i+1)*checkInt)%addLen)%(keyLen/3))* checkInt));
            }
        }
        //补全和正常的信息校验位方式不能一样
        checkInt=(((int)text.charAt(0)+(int)text.charAt(text.length()-1))*textLen/2) % 3 + 1;
        for (int i=0;i<textLen;i++){
            keyIndex=(int)text.charAt(i)*(i+1);
            //前32位Ascall码为控制符，可去掉
            if(keyIndex>=32)
                keyIndex=keyIndex-32;
            //如果ascall码长度大于key长度的1/3将报错，所以要做余数
            keyIndex=keyIndex%(keyLen/3);
            str.append(key.charAt(keyIndex*checkInt));
        }
        return strLeft.toString()+str.toString()+strRight.toString();
    }
    /**
     * 获取所有算法名称
     *
     * @param serviceType
     * @return
     */
    public static String[] getCryptoImpls(String serviceType) {
        Set result = new HashSet();

        // All all providers
        Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            // Get services provided by each provider
            Set keys = providers[i].keySet();
            for (Iterator it = keys.iterator(); it.hasNext(); ) {
                String key = (String) it.next();
                key = key.split(" ")[0];

                if (key.startsWith(serviceType + ".")) {
                    result.add(key.substring(serviceType.length() + 1));
                } else if (key.startsWith("Alg.Alias." + serviceType + ".")) {
                    // This is an alias
                    result.add(key.substring(serviceType.length() + 11));
                }
            }
        }
        return (String[]) result.toArray(new String[result.size()]);
    }

}
