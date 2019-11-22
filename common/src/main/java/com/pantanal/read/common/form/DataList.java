package com.pantanal.read.common.form;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class DataList<T> implements Serializable {

  public DataList() {
    count = 0;
    dataList = new ArrayList<>();
  }

  public int count;
  public List<T> dataList;
}
