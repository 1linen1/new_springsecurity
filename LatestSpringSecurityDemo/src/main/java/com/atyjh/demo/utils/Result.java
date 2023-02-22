package com.atyjh.demo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright: Copyright(c) 2022 iwhalecloud
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: Result.java
 *
 * @author huang.yijie
 * 时间: 2023/2/11 22:03
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    /**
     * 状态码：1为正确，2为出错
     */
    private Integer code;
    private T data;
    private String msg;

    private Result(String msg) {
        code = 1;
        this.msg = msg;
    }
    private Result(T data) {
        code = 1;
        this.data = data;
    }

    public static <E> Result<E> success(E data, String msg) {
        return new Result<>(1, data, msg);
    }

    public static <E> Result<E> success(String msg) {
        return new Result<>(msg);
    }

    public static <E> Result<E> success(E data) {
        return new Result<>(data);
    }

    public static <S> Result<S> error(String msg) {
        return new Result<>(0, null, msg);
    }

    public static <S> Result<S> error(Integer code, String msg) {
        return new Result<>(code, null, msg);
    }
}
