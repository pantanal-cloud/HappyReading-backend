package com.pantanal.read.common.form;

import lombok.Data;

import java.util.List;

/**
 * @author gudong
 */
@Data
public class Result<T> {
  private String msg;
  private int code = 0;
  private T data;

  public Result() {

  }


  public Result(T data) {
    this();
    this.data = data;
  }
}
