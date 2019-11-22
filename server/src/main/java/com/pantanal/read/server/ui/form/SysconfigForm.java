package com.pantanal.read.server.ui.form;

import com.pantanal.read.common.bean.SysconfigBean;
import com.pantanal.read.common.form.Form;
import lombok.Data;

@Data
public class SysconfigForm extends Form {
    private SysconfigBean sysconfig;
}
