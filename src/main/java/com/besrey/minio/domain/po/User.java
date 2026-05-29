package com.besrey.minio.domain.po;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("stg_user")
public class User {

    private Long id;

    private String nickName;

    private String username;

    private String password;

    private String email;

    //性别 0为未知 1为女性 2为男性
    private Integer sex;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
