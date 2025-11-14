package com.genealogy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 * 
 * @author ry
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "genealogy")
public class ProjectApiConfig {

    /** 项目名称 */
    private String name;

    /** 版本 */
    private String version;

    /** 版权年份 */
    private String copyrightYear;

    /** 实例演示开关 */
    private boolean demoEnabled;

    /** 上传路径 */
    @Getter
    private static String profile;

    /** 获取地址开关 */
    @Getter
    private static boolean addressEnabled;

    /** 验证码类型 */
    @Getter
    private static String captchaType;

    public void setProfile(String profile)
    {
        ProjectApiConfig.profile = profile;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        ProjectApiConfig.addressEnabled = addressEnabled;
    }

    public void setCaptchaType(String captchaType) {
        ProjectApiConfig.captchaType = captchaType;
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath()
    {
        return getProfile() + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }

    /***
     * 获取附件上传路径
     * @return
     */
    public static String getAttachmentPath(){
        return getProfile() + "/attachment";
    }

    /***
     * 即时通讯图片、文件存储目录
     * @return
     */
    public static String getImFolder(){
        return "/imFile";
    }

    /***
     * 资产管理，条形码存储目录
     * @return
     */
    public static String getAssetBarCodeFolder(){
        return "/assetBarCode";
    }


    /***
     * 获取即时通讯存储的文件
     * @return
     */
    public static String getImPath(){
        return getProfile() + getImFolder();
    }
}
