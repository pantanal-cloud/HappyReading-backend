package com.pantanal.read.server.ui.form;

import com.pantanal.read.common.bean.UserBean;
import com.pantanal.read.common.form.Form;
import lombok.Data;

@Data
public class UserForm extends Form {

    private UserBean user;

}
