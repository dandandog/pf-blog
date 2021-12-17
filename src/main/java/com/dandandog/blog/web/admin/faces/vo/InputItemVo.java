package com.dandandog.blog.web.admin.faces.vo;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/27 14:18
 */
@Data
public class InputItemVo {

    private String index;

    private String label;

    public InputItemVo(int index, String label) {
        this.index = StrUtil.toString(index);
        this.label = label;
    }
}
