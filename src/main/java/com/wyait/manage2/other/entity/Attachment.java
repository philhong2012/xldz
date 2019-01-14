package com.wyait.manage2.other.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 附件
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 业务类型
     */
    private String bussinessType;

    /**
     * 业务id
     */
    private String bussinessId;

    /**
     * 路径
     */
    private String filePath;

    /**
     * 创建部门
     */
    private String deptId;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createUserId;

    private LocalDateTime updateTime;

    private String updateUserId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 更新人名称
     */
    private String updateUserName;

    /**
     * 创建人名称
     */
    private String createUserName;


}
