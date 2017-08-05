$(function () {
    function customFormatter1(cellvalue, options, rowObject){
    	if(cellvalue!=null)
        	return cellvalue.trim()=="1"?"启动":"关闭";
    };
    function customFormatter2(cellvalue, options, rowObject){
        if(cellvalue!=null)
        	return cellvalue.trim()=="1"?"是":"否";
    };
    $("#jqGrid").jqGrid({
        url: baseURL + 'appuser/list',
        datatype: "json",
        colModel: [			
			//{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '用户名', name: 'username', index: 'username', width: 80 }, 			
			// { label: '密码', name: 'password', index: 'password', width: 80 },
            { label: '手机号', name: 'mobile', index: 'mobile', width: 80 },
            { label: '邮箱', name: 'email', index: 'email', width: 80 },
			{ label: '状态', name: 'status', index: 'status', width: 80,formatter:  customFormatter1 },
			//{ label: '是否发送短信', name: 'issms', index: 'isSMS', width: 80 ,formatter:  customFormatter2},
			{ label: '角色（备用）', name: 'role', index: 'role', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
        q:{
            username: null,
            mobile:null
        },
		showList: true,
		title: null,
		appUser: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.appUser = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.appUser.id == null ? "appuser/save" : "appuser/update";
            if(validAppuserform().form()) {
                //通过表单验证,向后台发送请求
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.appUser),
                    success: function(r){
                        if(r.code === 0){
                            alert('操作成功', function(index){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            } else {
                //校验不通过，什么都不用做，校验信息已经正常显示在表单上
            }
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "appuser/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "appuser/info/"+id, function(r){
                vm.appUser = r.appUser;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'username': vm.q.username,'mobile':vm.q.mobile},
                page:page
            }).trigger("reloadGrid");
		}
	}
});
