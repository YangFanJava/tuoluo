//================jquery_validate_begin==================
//自定义方法，完成手机号码的验证
//name:自定义方法的名称，method：函数体, message:错误消息
$.validator.addMethod("phone", function(value, element, param){
    //方法中又有三个参数:value:被验证的值， element:当前验证的dom对象，param:参数(多个即是数组)
    //alert(value + "," + $(element).val() + "," + param[0] + "," + param[1]);
    return new RegExp(/^1[3458]\d{9}$/).test(value);

}, "手机号码不正确");

function validAppuserform(){
    //让当前表单调用validate方法，实现表单验证功能
    /*关键在此增加了一个return，返回的是一个validate对象，这个对象有一个form方法，返回的是是否通过验证*/
    return $("#appuserDetail").validate({
        //debug:true, //调试模式，即使验证成功也不会跳转到目标页面
        rules:{     //配置验证规则，key就是被验证的dom对象，value就是调用验证的方法(也是json格式)
            username:{
                required:true //必填。如果验证方法不需要参数，则配置为true
            },
            password:{
                required:true
                //rangelength:[6,12]
            },
            email:{
                email:true
                //equalTo:'#spass' //表示和id="spass"的值相同
            },
            mobile:{
                required:true,
                phone:true
            }
        },
        messages:{
            username:{
                required:"请输入用户名"
                //rangelength:$.validator.format("用户名长度在必须为：{0}-{1}之间")
            },
            password:{
                required:"请输入密码"
            },
            email:{
                //required:"请填写邮件",
                email:"邮箱格式不正确"
            },
            mobile:{
                required:"请选择地址",
                phone:"手机号码格式不正确"
            }
        }
    });
}

//注册表单验证(每次表单的变化都可以校验)
validAppuserform();
//================jquery_validate_end==================