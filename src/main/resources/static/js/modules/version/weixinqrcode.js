$(function () {
    function customFormatter1(cellvalue, options, rowObject){
        if(cellvalue!=null)return cellvalue.trim()=="1"?"永久":"临时";
    };
    function customFormatter2(cellvalue, options, rowObject){
        if(cellvalue!=null)return cellvalue.trim()=="gh_7184f36eca2b"?"我的发票":"51发票";
    };
    $("#jqGrid").jqGrid({
        url: baseURL + 'weixinqrcode/list',
        datatype: "json",
        colModel: [			
			// { label: 'id', name: 'id', index: 'id', width: 50, key: true },
			// { label: '二维码中间Logo存放地址', name: 'imageHref', index: 'image_href', width: 80 },
			// { label: '创建时间', name: 'createDate', index: 'create_date', width: 80 },
			// { label: '获取的二维码ticket', name: 'ticket', index: 'ticket', width: 80 },
            { label: '公众号名称', name: 'accountId', index: 'account_id', width: 80 ,formatter:  customFormatter2},
            { label: '渠道名称', name: 'name', index: 'name', width: 80 },
            { label: '分组名称', name: 'groupName', index: 'group_name', width: 80 },
            { label: '场景ID', name: 'sceneId', index: 'scene_id', width: 80 },
            { label: '类型', name: 'actionName', index: 'action_name', width: 80  ,formatter:  customFormatter1},
            { label: '有效时间(s)', name: 'expireSeconds', index: 'expire_seconds', width: 80 },
            { label: '二维码生成时间', name: 'generationTime', index: 'generation_time', width: 80},
			// { label: '二维码保存路径', name: 'savePath', index: 'save_path', width: 80 },
			{ label: '生成二维码URL', name: 'qrcodeUrl', index: 'qrcode_url', width: 80 },
			// { label: '描述', name: 'description', index: 'description', width: 80 }
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
		weixinQrcode: {},
        groupNames:[]
	},
	methods: {
		query: function () {
			vm.reload();
		},
        queryGroupName:function(){
            $.get(baseURL + "weixinqrcode/group/", function(r){
                vm.groupNames = r.groupNames;
            });
        },
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.weixinQrcode = {actionName:0,accountId:'gh_7184f36eca2b'};
            vm.queryGroupName();
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id);
            vm.queryGroupName();
		},
		saveOrUpdate: function (event) {
			var url = vm.weixinQrcode.id == null ? "weixinqrcode/save" : "weixinqrcode/update";
			if(vm.weixinQrcode.actionName==1){//类型为永久,超时时间为-198
                vm.weixinQrcode.expireSeconds=-198;
			}
            if(validWeixinqrcodeForm().form()) {
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.weixinQrcode),
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
				    url: baseURL + "weixinqrcode/delete",
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
        previewQRcode:function(event){
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.getInfo(id);
            //異步的問題
            var imgsrc=vm.weixinQrcode.savePath;
            alert(imgsrc);
            //var imgsrc=grid.jqGrid('getRowData',id).savePath;
            //$("#qrcodeImg").attr("src",imgsrc);
            //$("#qrcode").modal();
        },
        generateQRcode: function (event) {
            var rowDatas=getSelectedRowsData();
            if(rowDatas == null){
                return ;
            }

            confirm('确定要生成吗？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "weixinqrcode/generateQRcode",
                    contentType: "application/json",
                    data: JSON.stringify(rowDatas),
                    success: function(r){
                        if(r.code == 0){
                            if(r.msg){
                                alert(r.msg, function(index){
                                    $("#jqGrid").trigger("reloadGrid");
                                });
                            }else{
                                alert('操作成功', function(index){
                                    $("#jqGrid").trigger("reloadGrid");
                                });
                            }
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
		getInfo: function(id){
			$.get(baseURL + "weixinqrcode/info/"+id, function(r){
                vm.weixinQrcode = r.weixinQrcode;
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