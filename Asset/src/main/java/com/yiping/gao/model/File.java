package com.yiping.gao.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 高一平
 * @date 2022/10/4
 * @description 文件定义
 **/
@Data
@ApiModel("文件定义")
public class File {
    @ApiModelProperty(value = "ID")
    public Long id;
    @ApiModelProperty(value = "文件名")
    public String name;
    @ApiModelProperty(value = "标签文件名")
    public String tagsName;
    @ApiModelProperty(value = "作者")
    public String author;
    @ApiModelProperty(value = "文件大小")
    public Long size;
    @ApiModelProperty(value = "文件拓展名")
    public String ext;
    @ApiModelProperty(value = "当前文件路径")
    public String currentPath;
}
