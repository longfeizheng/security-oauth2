package cn.merryyou.security.domain;

import lombok.Data;

/**
 * Created on 2018/5/24.
 *
 * @author zlf
 * @since 1.0
 */
@Data
public class Result<T> {
    private Integer code;
    private String msg;

    private T data;
}
