package com.twolz.qiyi.api.web.controller;

import com.twolz.qiyi.common.core.ResultDO;
import com.twolz.qiyi.common.core.SysCode;
import com.twolz.qiyi.common.dto.FileUploadResDto;
import com.twolz.qiyi.common.exception.BizException;
import com.twolz.qiyi.common.service.oss.OSSClientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by liuwei
 * date 2017-05-16
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class UploadController {

    private @Value("system.common.imgsDomain") String imgsDomain;

    private @Value("system.common.imgsLocalhostDomain") String imgsLocalhostDomain;

    private @Value("system.common.imgRoot") String imgRoot;

    @Autowired
    private OSSClientService ossClientService;

    @Autowired
    private ResourceLoader resourceLoader;

    @ApiOperation(value = "文件上传：刘为", notes = "文件上传，返回图片服务端路径")
    @RequestMapping(value="/fileUpload",method= RequestMethod.POST)
    public ResultDO<FileUploadResDto> fileUpload(@ApiParam(name = "file", value = "上传文件") @RequestParam(value = "file", required = false) MultipartFile file) throws BizException{
        ResultDO result = new ResultDO();
        try {
            Date date = new Date();
            String fileName = date.getTime()+ RandomStringUtils.randomNumeric(4)+".jpg";
            if(file!=null && !file.isEmpty()){
                //OSS上传
                ossClientService.putOSSByByte(fileName,file.getBytes());
                FileUploadResDto fileUploadResDto = new FileUploadResDto();
                fileUploadResDto.setFilePath(imgsDomain);
                fileUploadResDto.setFileName(fileName);
                result.setModule(fileUploadResDto);
                result.setSuccess(true);
            } else {
                throw new BizException(SysCode.UPLOAD_FILE_ERROR);
            }

        } catch (Exception e) {
            throw new BizException(SysCode.UPLOAD_FILE_ERROR);
        }
        return result;
    }

    @ApiOperation(value = "文件上传到本地：刘为", notes = "文件上传到本地，返回图片服务端路径")
    @RequestMapping(value="v1/fileUploadLoc",method= RequestMethod.POST)
    public ResultDO<FileUploadResDto> fileUploadLoc(@ApiParam(name = "file", value = "上传文件") @RequestParam(value = "file", required = false) MultipartFile file,
                                                    @ApiParam(name = "imgBase64", value = "上传图标Base64字符" ) @RequestParam(value = "imgBase64", required = false) String imgBase64) throws BizException{
        ResultDO result = new ResultDO();
        try {
            Date date = new Date();
            //图片路径
            StringBuffer filePath = new StringBuffer(imgsLocalhostDomain);
            String fileName = date.getTime()+ RandomStringUtils.randomNumeric(4)+".jpg";
            if(file!=null && !file.isEmpty()){
                //本地文件上传
                Files.copy(file.getInputStream(), Paths.get(imgRoot, fileName));
            } else if (!StringUtils.isEmpty(imgBase64)) {
                byte[] imgByte = Base64.decodeBase64(imgBase64.split(",")[1]);
                ImageIO.write(ImageIO.read(new ByteArrayInputStream(imgByte)), "jpg", new File(imgRoot + fileName));

            }
            FileUploadResDto fileUploadResDto = new FileUploadResDto();
            fileUploadResDto.setFilePath(filePath.toString());
            fileUploadResDto.setFileName(fileName);
            result.setModule(fileUploadResDto);
            result.setSuccess(true);
        } catch (Exception e) {
            throw new BizException(SysCode.UPLOAD_FILE_ERROR);
        }
        return result;
    }

    /**
     * 显示图片的方法关键 匹配路径像
     *
     * */
    @RequestMapping(method = RequestMethod.GET, value = "/{fileName:.+}")
    public ResponseEntity<?> getFile(@PathVariable String fileName) {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(imgRoot, fileName).toString()));
        } catch (Exception e) {
            log.error("查看图片出错",e);
            return ResponseEntity.notFound().build();
        }
    }
}
