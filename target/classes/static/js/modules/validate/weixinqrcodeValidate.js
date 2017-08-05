function validWeixinqrcodeForm(){
    //让当前表单调用validate方法，实现表单验证功能
    /*关键在此增加了一个return，返回的是一个validate对象，这个对象有一个form方法，返回的是是否通过验证*/
    return $("#weixinqrcodeForm").validate({
        //debug:true, //调试模式，即使验证成功也不会跳转到目标页面
        rules:{     //配置验证规则，key就是被验证的dom对象，value就是调用验证的方法(也是json格式)
            expireSeconds:{
                required:true,
                number:true //必填。如果验证方法不需要参数，则配置为true
            },
            name:{
                required:true
                //rangelength:[6,12]
            }
        },
        messages:{
            expireSeconds:{
                required:"请输入失效时间",
                number: "请输入合法的数字"
                //rangelength:$.validator.format("用户名长度在必须为：{0}-{1}之间")
            },
            name:{
                required:"请输入用户名"
            }
        }
    });
}

//注册表单验证(每次表单的变化都可以校验)
validWeixinqrcodeForm();
//================jquery_validate_end==================