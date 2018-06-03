package com.twolz.qiyi.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by chenyu on 2017/6/22.
 */
@Data
public class FileUploadResDto implements Serializable {

    private static final long serialVersionUID = 1435344604892696808L;

    private String filePath;

    private String fileName;

    private String realFileName;

}
