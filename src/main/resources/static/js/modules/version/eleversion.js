$(function () {
	//自定义转换函数
    //cellvalue - 当前cell的值  
	//options - 该cell的options设置，包括{rowId, colModel,pos,gid}  
	//rowObject - 当前cell所在row的值，如{ id=1, name="name1", price=123.1, ...}  
    function customFormatter1(cellvalue, options, rowObject){
        if(cellvalue!=null)return cellvalue.trim()=="1"?"启动":"关闭";
    };
    function customFormatter2(cellvalue, options, rowObject){
        if(cellvalue!=null)return cellvalue.trim()=="1"?"是":"否";
    };
    $("#jqGrid").jqGrid({
        url: baseURL + 'eleversion/list',
        datatype: "json",
        colModel: [			
			//{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '应用版本名称', name: 'versionName', index: 'version_name', width: 80 },
			{ label: '版本更新次数', name: 'versionNum', index: 'version_num', width: 80 },
			{ label: '上传路径(下载路径)', name: 'path', index: 'path', width: 80 }, 			
			{ label: '更新说明', name: 'description', index: 'description', width: 80 }, 			
			{ label: '创建人', name: 'createName', index: 'create_name', width: 80 }, 			
			{ label: '创建时间', name: 'createDate', index: 'create_date', width: 80 }, 			
			{ label: '状态', name: 'state', index: 'state', width: 80 ,formatter:  customFormatter1},
			{ label: '是否强制更新', name: 'forceupdate', index: 'forceUpdate', width: 80,formatter:customFormatter2 }
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

    new AjaxUpload('#upload', {
        action: baseURL + 'eleversion/upload?token=' + token,
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onSubmit:function(file, extension){
        },
        onComplete : function(file, r){
            if(r.code == 0){
                alert(r.url);
                $("#path").val(r.url);
            }else{
                alert(r.msg);
            }
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		eleVersion: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.eleVersion = {};
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
			var url = vm.eleVersion.id == null ? "eleversion/save" : "eleversion/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.eleVersion),
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
				    url: baseURL + "eleversion/delete",
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
			$.get(baseURL + "eleversion/info/"+id, function(r){
                vm.eleVersion = r.eleVersion;
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