$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'wxuserinfo/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '用户是否订阅该公众号标识，0表示未关注', name: 'subscribe', index: 'subscribe', width: 80 }, 			
			{ label: '对当前公众号唯一ID', name: 'openid', index: 'openid', width: 80 }, 			
			{ label: '用户的昵称', name: 'nickname', index: 'nickname', width: 80 }, 			
			{ label: '用户的性别，1男、2女', name: 'sex', index: 'sex', width: 80 }, 			
			{ label: '用户所在城市', name: 'city', index: 'city', width: 80 }, 			
			{ label: '用户所在国家', name: 'country', index: 'country', width: 80 }, 			
			{ label: '用户所在省份', name: 'province', index: 'province', width: 80 }, 			
			{ label: '用户的语言', name: 'language', index: 'language', width: 80 }, 			
			{ label: '用户头像地址', name: 'headimgurl', index: 'headimgurl', width: 80 }, 			
			{ label: '用户关注时间', name: 'subscribeTime', index: 'subscribe_time', width: 80 }, 			
			{ label: '用户唯一ID', name: 'unionid', index: 'unionid', width: 80 }, 			
			{ label: '用户管理界面对粉丝添加备注', name: 'remark', index: 'remark', width: 80 }, 			
			{ label: '用户所在的分组ID', name: 'groupid', index: 'groupid', width: 80 }, 			
			{ label: '用户被打上的标签ID列表', name: 'tagidList', index: 'tagid_list', width: 80 }, 			
			{ label: '公众号原始ID', name: 'accountId', index: 'account_id', width: 80 }, 			
			{ label: '数据是否是完整的', name: 'isWhole', index: 'is_whole', width: 80 }			
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
		showList: true,
		title: null,
		wxUserinfo: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.wxUserinfo = {};
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
		selOpenId:function(event){
            $.post("/tuoluo2/wxuserinfo/selAll2",{"accountId":"gh_5c48f34ff74f"},function (result) {
                alert(result)
            },"json")
		},
        selInfo:function(event){
            $.post("/tuoluo/wxuserinfo/selInfo",{"account_id":"gh_5c48f34ff74f"},function (result) {
                alert(result)
            },"json")
		},
		selOpenId2:function(event){
            $.post("/tuoluo/wxuserinfo/selAll2",{"accountId":"gh_7184f36eca2b"},function (result) {
                alert(result)
            },"json")
		},
        selInfo2:function(event){
            $.post("/tuoluo/wxuserinfo/selInfo",{"account_id":"gh_7184f36eca2b"},function (result) {
                alert(result)
            },"json")
		},
        test:function(event){
            $.post("/tuoluo/wxuserinfo/test/0",{},function (result) {
                alert(result)
            },"json")
		},
        test2:function(event){
            $.post("/tuoluo/wxuserinfo/test/1",{},function (result) {
                alert(result)
            },"json")
		},
		saveOrUpdate: function (event) {
			var url = vm.wxUserinfo.id == null ? "wxuserinfo/save" : "wxuserinfo/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.wxUserinfo),
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
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "wxuserinfo/delete",
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
			$.get(baseURL + "wxuserinfo/info/"+id, function(r){
                vm.wxUserinfo = r.wxUserinfo;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});