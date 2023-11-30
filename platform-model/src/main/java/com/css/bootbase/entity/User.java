package com.css.bootbase.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author jiming.jing
 * @since 2023/02/01
 */
@Getter
@Setter
@TableName("T_USER")
@ApiModel(value = "User对象", description = "用户信息表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    @TableId("USER_ID")
    private String userId;

    @ApiModelProperty("用户姓名")
    @TableField("USER_NAME")
    private String userName;

    @ApiModelProperty("年龄")
    @TableField("AGE")
    private Integer age;

    @ApiModelProperty("性别 0-男 1-女")
    @TableField("GENDER")
    private Integer gender;

    @ApiModelProperty("身份证号")
    @TableField("ID_CARD")
    private String idCard;

    @ApiModelProperty("删除标识 0-否 1-是")
    @TableField("DEL_FLAG")
    @TableLogic(value = "0", delval = "1")
    private Integer delFlag;

    @ApiModelProperty("创建人")
    @TableField("CREATOR")
    private String creator;

    @ApiModelProperty("创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("修改人")
    @TableField("LAST_EDITOR")
    private String lastEditor;

    @ApiModelProperty("修改时间")
    @TableField(value = "LAST_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date lastTime;

}
