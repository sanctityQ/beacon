/*!
 * grid
 * levone
 * Mail:zuoliwen@sinosoft.com.cn
 * Date: 2012-07-03
 */
 //查询keyCode keyUp
 //1171
(function ($) {
    $.fn.extend({
        Grid: function(opts){
            var defaults = {
                url       : "grid.json" ,
                dataType  : "json" ,
                height    : "auto" ,
                width     : "auto",
                loadText  : "数据加载中…",
                colums    : "",
                rowNum    : "auto",
                number    : true,
                hasSpan   : false,
                //queryData : {"age":1},
                multiselect: true,
                hasAllCheckbox:true,//如果为true，有全选的checkbox；如果不为true的haunted，没有全选的checkbox
                autoColWidth: true,
                radioSelect: false,
                sorts     : false,
                colDisplay: true,
				frontJumpPage : true,
                draggable : true,
                sequence  : true,
                clickSelect :true,
				searchClass : "",
				searchBtn:"",
				//true为后端分页，before为前端分页
				afterRepage : false,
                //下面几个是新加的属性
                modifyUrl : "",
                isCanModify : false,
				
				checkboxClickFun : "",

                pages     : {
                    pager    : true ,
                    renew    : false ,
                    paging   : true ,
                    goPage   : true ,
                    info     : true
                },
                callback : {
                    beforeDelData : function(gridNode){},
                    afterDelData: function(gridNode){return false;}
                }
            };
            var defaults = $.extend(defaults, opts);
            var g = $(this);
            var $gBox = $('<div class="grid_box"></div>').width('100%');

            var $gHeader = $('<div class="grid_head"></div>');
            var $gHeadBox = $('<div class="head_box"></div>');

            var $gHeadColumn = $('<div class="gird_head_column"></div>');

            var $needle = $('<div class="needle"></div>');

            var $gView = $('<div class="grid_view"></div>');
            var $load = $('<div class="loading"><span>' + defaults.loadText + '</span></div>');

            var colAttributes = {};
            colAttributes.widths = [];
            colAttributes.aligns = [];
            colAttributes.colors = [];

            var widths = [],aligns = [],colors = [],allCh = [],hids = [];
            var $checkBox = $('<input type="checkbox" value="" name="all_sec" class="chex" />');
            var $gNumber = '';

            var adaptive = 0,str = 0,end = defaults.rowNum,pB = 1;

            var onOff,
                pageUnclik,
                disb = true,
                trId,
                oW,
                nW,
                tI,
                rows,
                total,
                pFirst,
                pPrev,
                pNext,
                pLast,
                pNub,
                pB = 1,
                pSel,
                ps,
                _stop;
            var _move=false;
            var _x;

            var _data;
			
			//初始化的时候data的值
			var originGridData = "";

            g.append($gBox);

           // alert($gBox.width());

            var $gW = $gBox.width();
            var buttonLeft = 0;
            var $thisTr = "";

            autoWidth();

            createGridHead();

            function createGridHead(){
                var col = defaults.colums;

                if(defaults.multiselect) {
                    if(defaults.hasAllCheckbox) {
                        $gHeadColumn.addClass("multiple").append($checkBox);
                    } else {
                        $gHeadColumn.addClass("multiple");
                    }

                    $gHeader.append($gHeadColumn);
                };

                if(defaults.number){

                    $gNumber = $('<div class="gird_head_column number"><span class="number">序号</span></div>');
                    $gHeader.append($gNumber);
                };
                for(var i = 0; i < col.length; i++){

                    if(!defaults.sorts && !defaults.colDisplay) {
                        $gHeadColumn = $('<div class="gird_head_column cols col_' + i +'"></div>');
                    }else{
                        $gHeadColumn = $('<div class="gird_head_column cols col_' + i +'"><div class="grid_menu"></div></div>');
                    };
                    $gHeadColumn.append("<span>"+ col[i].text +"</span>");
                    if(col[i].id){
                        $gHeadColumn.attr("id", col[i].id);
                    }else{
                        alert("id不能为空");
                        return false;
                    };
                    if(col[i].name){
                        $gHeadColumn.attr("name",col[i].name);
                    }else{
                        alert("name不能为空");
                        return false;
                    };
                    if(col[i].index){
                        $gHeadColumn.attr("index",col[i].index);
                    };

                    if(col[i].width){
                        $gHeadColumn.css({"width":col[i].width});

                        colAttributes.widths.push(col[i].width);
                    }else{
                        $gHeadColumn.css({"width":adaptive});
                        colAttributes.widths.push(adaptive);
                    };

                    if(col[i].align){
                        colAttributes.aligns.push(col[i].align);
                    }else{
                        colAttributes.aligns.push("left");
                    };

                    if(col[i].color){
                        colAttributes.colors.push(col[i].color);
                    }else{
                        colAttributes.colors.push("inherit");
                    };

                    if(defaults.pages.goPage || defaults.pages.paging){
                        formatHids(i);
                    };
                    $gHeader.append($gHeadColumn);

                    $gHeadColumn
                        .bind({"mouseover":changeNeedle,"mouseout":unHover})
                        .children("div").bind("click", colMenuOpt);
                    if(defaults.sequence){
                        $gHeadColumn.bind("click", colSequence);
                    }
                    if(defaults.draggable){
                        $gHeadColumn.bind("mousedown",moveThead);
                    };
                };
                $gHeader.append($needle);
                $gBox.append($gHeader);
                if(defaults.sorts || defaults.colDisplay){
                    greatMenu();
                    $needle.bind("mousedown", dragCol);
                };
                $("div.gird_head_column",$gHeader).wrapAll($gHeadBox);
            };

            function formatHids(i){
                var obj = new HideInfo('col_' + i, false);
                hids.push(obj);
            };
			
            function HideInfo(name,isHide){
                this.name = name;
                this.isHide = isHide;
                this.getInfo = function(){
                    return this.name + ": " + this.isHide;
                };
            };

            function colMenuOpt(e){
                var $gM = $gBox.children('div.grid_head_menu');

                $('div.grid_head_menu', $gBox).hide();

                if($.browser.msie && ($.browser.version == "7.0")){
                    $('div.grid_head_menu:last',$gBox).css('border-width','0')
                }

                var offset = $(this).offset();
                var nH = $(this).height();
                var pHead = $(this).parent();

                var sm = $gW + g.offset().left - offset.left < $gM.width();

                pHead
                    .siblings().removeClass('select')
                    .children('div.grid_menu').removeClass('clk');
                $(this).addClass('clk');
                pHead.addClass('select');

                if(sm){
                    $gM.css({'left':offset.left - g.offset().left - $gM.width() + $(this).width(),'top':nH}).show();

                    onOff = false;
                }else{
                    $gM.css({'left':offset.left - g.offset().left,'top':nH}).show();
                    onOff = true;
                };
                $(document).bind("click",bodyClick);
                e.stopPropagation();
                var part = $('div.grid_head_menu > ul > li.parting',$gBox);

                part.bind('mouseover',{part:part,onOff:onOff},subMopt);
            };


            function subMopt(e){
            	if($.browser.msie&&($.browser.version == "7.0")){
                    $('div.grid_head_menu:last',$gBox).css('border-width','1px')
                };
                var part = e.data.part;
                var onOff = e.data.onOff;
                var sM = $('div.grid_head_menu > div.grid_head_menu',$gBox);
                if(onOff){
                    sM.css({'top':part.position().top,'left':part.position().left + 126}).show();
                }else{
                    sM.css({'top':part.position().top,'left':-128}).show();
                };
                $('li',sM).unbind('click');
                $('li',sM).bind('click',subClick);
                return false;
            };

            function subClick(e){
                e.stopPropagation();
                var check = $('div > label',$(this));
                var index = $(this).index();
                var head = $("div.col_" + index,$gHeader);
                var hI = head.index();
                var bw = $("div.gird_head_column[col]:visible",$gHeader).length;
                var tb = $("table",$gView);
                var tr = $("table > tbody > tr",$gView);
                var ul = check.parents('ul');
                var oI = hI;
                if(defaults.multiselect){
                    oI = oI - 1;
                };
                if(defaults.number){
                    oI = oI - 1;
                };
                var obj = hids[oI];
                if(check.hasClass('chected')){
                    if(disb){
                        var tw = tb.width() - head.width();
                        check.removeClass('chected');
                        head.hide();
                        tb.width(tw - bw);
                        tr.each(function(){
                            $("td",$(this)).eq(hI).hide();
                        });
                        obj.isHide=true;
                    }
                }else{
                    var tw = tb.width() + head.width();
                    check.addClass('chected')
                    $('label.disabled',ul).removeClass('disabled');
                    head.show();
                    tb.width(tw);
                    tr.each(function(){
                        $("td",$(this)).eq(hI).show();
                    });
                    disb = true;
                    obj.isHide=false;
                };
                var cl = $("label.chected",ul).length;
                if(cl == 1){
                    var last = $('label.chected',ul);
                    last.addClass('disabled');
                    disb = false;
                };
            }


            function bodyClick(e){
                var $gM = $('div.grid_head_menu',$gBox);
                var pHead = $('div.select',$gHeader);
                pHead
                    .removeClass('select')
                    .children('div.grid_menu').removeClass('clk');
                $gM.hide();
                if($.browser.msie&&($.browser.version == "7.0")){
                    $gM.last().css('border-width','0')
                };
                $(document).unbind("click",bodyClick);
                return false
            };

            $gHeader.width($gW);

            if(defaults.height != 'auto'){
                $gView.height(defaults.height - $gHeader.height() - 1);
            };
			
            if(defaults.width == 'auto'){
                $gBox.width($gW);
            }else{
                $gBox.width(defaults.width);
            };

            $gBox.append($gView);
            
			if(_data){
                loading();
                initGrid(_data, str, end, colAttributes);
                _stop = _data.rows.length;
            }else{
                createData();
            }
            
			$checkBox.bind("click",allClick);
            
			$gView.scroll(function(){
                var sL = $gView.scrollLeft();
                $gHeader.css('margin-left',-sL);
            });
            
			//制作page
			if(defaults.pager){
                greadPage();
            }
            
			$gView.width($gW).height($gView.height());

            function createData(){
                var url = defaults.url;
                var dataType = defaults.dataType;
                var type = defaults.type;
                var obj = {};
                obj.pageNo = 1;
                obj.rowNum = defaults.rowNum;
                //obj.queryData = defaults.queryData;
                //console.log(defaults.data)
                obj = $.extend(defaults.data,obj);
                $.ajax({
                    url: url,
                    dataType : dataType,
                    type : type,
                    data: obj,
                    async : false,
                    //processData : false,
                    beforeSend : function(){loading()},
                    error : function (XMLHttpRequest,errorThrown) {
                        alert("数据加载出错！" + errorThrown);
                    },
                    success: function(data){
                        initGrid(data, str, end, colAttributes);
                        _data = data;
						originGridData = _data;
                        _stop = _data.rows.length;
                    }
                });
            };

            function initGrid(data, startParam, endParam, colParams) {
                console.log(data);
                readJson(data, startParam, endParam, colParams);
                $load.hide();
                $('tr',$gView).hover(function(){
                    $(this).addClass('hover');
                },function(){
                    $(this).removeClass('hover');
                });
                var checkboxFun = typeof defaults.checkboxClickFun;
                if(defaults.checkboxClickFun != "" && defaults.checkboxClickFun != null && checkboxFun == "function") {

                } else {
                    $('tr',$gView).bind('click',trClick);
                }
            };


            function readJson(data, startParam, endParam, colParams){
                var gTable = '<table border="0" cellspacing="0" cellpadding="0">';
                rows = data.rows;
                total = data.total;
                var colLen = defaults.colums.length;
                console.log(colLen);
                var allW = 0;
                gTable = gTable + '<tr class="th_rows">';
                if(defaults.multiselect) {
                    gTable = gTable + '<td style="width:22px">&nbsp;</td>';
                    allW += 22;
                };
                if(defaults.number) {
                    gTable = gTable + '<td style="width:30px">&nbsp;</td>';
                    allW += 30;
                };
                for(i = 0; i < colLen; i++){
                    gTable  = gTable + '<td col="col_' + i + '" style="width:' + colParams.widths[i] + 'px">&nbsp;</td>';
                    allW += Math.abs(colParams.widths[i]);
                };
                gTable  = gTable + '</tr>';

                if(rows.length < endParam){
                    endParam = rows.length;
                    pageUnclik = true;
                };
                for(j = startParam; j < endParam; j++){
                    var cell = rows[j].cell;
                    gTable = gTable + '<tr id="row_' + rows[j].id + '">';
                    allCh.push(false);
                    if(defaults.multiselect){
                        //gTable  = gTable + '<td class="multiple"><input name="g_check" type="checkbox" value="" /></td>';
                        if(rows[j].cell[colLen] == "true") {
                            //if(defaults.hasAllCheckbox) {
                                gTable  = gTable + '<td class="multiple tr_multiple"><input name="g_check" type="checkbox" checked="checked" value="" /></td>';
                            //} else {
                                //gTable  = gTable + '<td class="multiple tr_multiple"></td>';
                            //}

                        } else {
                            //if(defaults.hasAllCheckbox) {
                                gTable  = gTable + '<td class="multiple tr_multiple"><input name="g_check" type="checkbox" value="" /></td>';
                            //} else {
                                //gTable  = gTable + '<td class="multiple tr_multiple"></td>';
                            //}

                        }
                    };
                    if(defaults.number){
                        //jjjjjjj
                        var tempIndex = 0;
						if(defaults.frontJumpPage) {
							tempIndex = j + 1;
						} else {
							tempIndex = j + 1 + str;	
						}
                        gTable = gTable + '<td class="number"><span title="' + tempIndex + '">' + tempIndex + '</span></td>' ;
                    };
                    for(i = 0; i < colLen; i++){
                        var text = cell[i];
                        if(text) {
                            if(defaults.hasSpan == true) {
                                gTable  = gTable + '<td col="col_' + i + '" style="text-align:' + colParams.aligns[i] + ';color:' + colParams.colors[i] + '"><span title="' + text + '">' + text + '</span></td>';
                            } else {
                                gTable  = gTable + '<td col="col_' + i + '" style="text-align:' + colParams.aligns[i] + ';padding:0 3px;color:' + colParams.colors[i] + '">' + text + '</td>';
                            }
                        } else {
                            if(defaults.hasSpan == true) {
                                gTable  = gTable + '<td col="col_' + i + '" style="text-align:' + colParams.aligns[i] + ';color:' + colParams.colors[i] + '><span title="' +text + '">' + "&nbsp"  +'</span></td>';
                            } else {
                                gTable  = gTable + '<td col="col_' + i + '" style="text-align:' + colParams.aligns[i] + ';padding:0 3px;color:' + colParams.colors[i] + '">' + '&nbsp;</td>';
                            }
                        }
                    };
                    gTable  = gTable + '</tr>';
                };
                gTable  = gTable + '</table>';
                $gView.append(gTable);
                $('tr:odd',$gView).addClass('odd');
                $('table',$gView).width(allW);
                if(defaults.isCanModify) {
					$('table', $gView).find("tr").bind("dblclick", createTrShadow);
				}
				var checkboxFun = typeof defaults.checkboxClickFun;
				if(defaults.checkboxClickFun != "" && defaults.checkboxClickFun != null && checkboxFun == "function") {
					defaults.checkboxClickFun();
                    $('tr',$gView).unbind("click");
				}
				
            };
			
			//grid的前端查询
			if(defaults.searchClass != "") {
				$("." + defaults.searchClass).bind("keyup",
                    function(e){
                        searchFun(e,$("." + defaults.searchClass).val())
                    });
			}
			if(defaults.searchBtn != "") {
				$("." + defaults.searchBtn).bind("click", {btn:"button"},
                    function(e){
                        searchFun(e,$("." + defaults.searchClass).val())
                    });
			}
			
			function searchFun(e,searchVal) {
				if(e.keyCode == "13" || e.data) {
					//var searchVal = $("." + defaults.searchClass).val();
					if(searchVal == "") {
						_data = originGridData;
						$gView.html("");
						if(_data.total < defaults.rowNum) {
							initGrid(_data, 0, _data.total, colAttributes);	
						} else {
							initGrid(_data, 0, defaults.rowNum, colAttributes);	
						}
							
						g.find(".grid_page").eq(0).remove();
						greadPage();	
					} else {
						var allCount = 0; //用来记录查询之后一共有多少条数据
						//原始json数据一共有多少条记录
						var originDataLength = originGridData.rows.length;
						var originCellLength = originGridData.rows[0];
						if(originCellLength) {
							originCellLength = originGridData.rows[0].cell.length;	
						} else {
							originCellLength = 0;	
						}
						//字符串转化成json
						//originGridData
						//循环这个json数据
						var temStr = '"rows":[';
						
						for(var i = 0; i < originGridData.rows.length; i++) {
							var temCount = 0;
							for(var j = 0; j < originGridData.rows[i].cell.length; j++) {
								if( -1 == originGridData.rows[i].cell[j].indexOf(searchVal) ) {
									temCount++;	
								}	
							}
							if( temCount != originCellLength ) {
								//说明这条记录是需要的，需要把它拼进新的json串中
								var tem = '{"id":"' + originGridData.rows[i].id + '","cell":['
								for(var m = 0; m < originCellLength; m++) {
									if( m + 1 < originCellLength ) {
										tem = tem +  '"'+ originGridData.rows[i].cell[m] +'",';		
									} else {
										tem = tem + '"'+ originGridData.rows[i].cell[m] +'"]},';
									}
								}
								temStr = temStr + tem;	
								allCount++;
							}	
						}
						var lastDouHaoIndex = temStr.lastIndexOf(",");
						temStr = temStr.substring(0, lastDouHaoIndex);
						temStr = '{"total":"' + allCount + '",' + temStr + ']}';
						//alert(temStr);
						//var temStr = '{"total":"1", "rows": [{"id":"1","cell": ["test01","管理员角色1","2012-08-23","2012-08-30"]}]}';
						_data = eval('(' + temStr + ')');
						$gView.html("");
						if(_data.total < defaults.rowNum) {
							initGrid(_data, 0, _data.total, colAttributes);	
						} else {
							initGrid(_data, 0, defaults.rowNum, colAttributes);	
						}
						g.find(".grid_page").eq(0).remove();
						greadPage();	
					} 
				}
				
			}
			
			
            //在滚动条移动的时候要是有了roweditor，那么最下边的button也得跟着移动相应的位置
//，而且整个rowEditor的宽度得重新计算
            $gView.scroll(function(){
                var sL = $gView.scrollLeft();
                $gHeader.css('margin-left',-sL);
                if($(".rowEditorDiv", $gView).length > 0) {
                    var $reSelf = $(".rowEditorDiv", $gView);
                    var btDiv = $reSelf.find(".button_div");
                    var oldLeft = btDiv.css("left");
                    if(buttonLeft + sL + $gView.find(".button_div").width() < $gView.width) {
                        btDiv.css("left" , buttonLeft + sL);
                    }
                }

            });

            //生成修改框
            function createTrShadow(e) {
                var width = $(this).width();
                var left = $(this).position().left;
                var thisHeight = $(this).height();
                var fontSize = $(this).css("font-size");
                var tem = fontSize.split("px");
                var top = $(this).position().top - (thisHeight - tem[0])/2;
                var height = thisHeight*2 - tem[0] - 2;
                var $rowEditorDiv = $("<div class='rowEditorDiv'></div>");
                $thisTr = $(this);
                var idArray = [];
                if($(".rowEditorDiv", $gView).length > 0) {
                    $(".rowEditorDiv", $gView).find(".inner_div").remove();
                    var $thisEditor = $(".rowEditorDiv", $gView);
                    var $thisButtonDiv = $(".button_div", $gView);

                    var bunDivHt = 32;
                    var thisIndex = $(this).index();
                    var allTrNum = $gView.find("table").find("tr").length;
                    //判断是不是最后一个，只要不是最后一个，其他的都是到下边
                    if(thisIndex < allTrNum - 2 || thisIndex == 1 || thisIndex == 2) {
                        $thisButtonDiv.removeClass("upDiv").addClass("downDiv");
                        $thisButtonDiv.width(200).css({"background-color":"#EAF1FB", "top":height, "left":bLeft});
                        $thisEditor.width(width).height(height)//.css("line-height", height+"px");
                        //$thisEditor.css({"top":top+1, "left":left});
                        $thisEditor.animate({ top: top + 1 + $gView.scrollTop()}, 200 );
                    } else {
                        $thisButtonDiv.removeClass("downDiv").addClass("upDiv");
                        $thisButtonDiv.width(200).css({"background-color":"#EAF1FB", "top":-bunDivHt - 1, "left":bLeft});
                        $thisEditor.width(width).height((thisHeight - tem[0])/2 + thisHeight);
                        //$thisEditor.css({"top":top-2, "left":left});
                        $thisEditor.animate({ top: top - 3 + $gView.scrollTop()}, 200 );
                    }

                    var flag = true;
                    $(this).find("td").each(function(){
                        var $innerDiv = $("<div class='inner_div'></div>");
                        var tdWidth = $(this).innerWidth();
                        var $innerDivInput = "";
                        if($(this).find("input[type='checkbox']").length > 0) {
                            if($(this).find("input[type='checkbox']").attr("checked")) {
                                $innerDivInput = $innerDiv.append("<input type='checkbox' checked='checked' />").width(tdWidth).css("margin-top", thisHeight - tem[0] - 1);
                                $thisEditor.append($innerDiv);
                            } else {
                                $innerDivInput = $innerDiv.append("<input type='checkbox' />").css("margin-top", thisHeight - tem[0] - 1);
                                $innerDiv.width(tdWidth).find("input").width(tdWidth - 6);
                                $thisEditor.append($innerDiv);
                            }
                        } else {
                            if(flag) {
                                $innerDiv.append($(this).html()).width(tdWidth).height(height).css("line-height", height+"px");
                                $thisEditor.append($innerDiv);
                                flag = false;
                            } else {
                                var colTem = $(this).attr("col");
                                var colOne = colTem.split("_");
                                var colVal = colOne[1];
                                var colId = "in_" + colTem;
                                if(defaults.colums[colVal].type == "date") {
//									idArray.push(colId);
//									$innerDivInput = $("<input id="+ colId + "  type='text' />").val($(this).text()).width(tdWidth - 26);
//									$innerDiv.append($innerDivInput).width(tdWidth).css("margin-left","1px").css("margin-top", (thisHeight - tem[0])/2 - 1);
//									$thisEditor.append($innerDiv);
                                    if(defaults.colums[colVal].disabled) {
                                        $innerDivInput = $("<input type='text' />").val($(this).text()).width(tdWidth - 6).attr("disabled", "disabled");
                                    } else {
                                        idArray.push(colId);
                                        $innerDivInput = $("<input id="+ colId + "  type='text' />").val($(this).text()).width(tdWidth - 6);
                                    }
                                    $innerDiv.append($innerDivInput).width(tdWidth).css("margin-left","1px").css("margin-top", (thisHeight - tem[0])/2 + 1);
                                    $thisEditor.append($innerDiv);

                                    if(defaults.colums[colVal].datePicker && defaults.colums[colVal].timePicker) {
                                        //说明两个都有呗
                                        $( "#"+ colId  ).datetimepicker({
                                            showHour : defaults.colums[colVal].timePicker.hasHour !== 'undefined' ? defaults.colums[colVal].timePicker.hasHour : true,
                                            showMinute : defaults.colums[colVal].timePicker.hasMinute !== 'undefined' ? defaults.colums[colVal].timePicker.hasMinute : true,
                                            showSecond: defaults.colums[colVal].timePicker.hasSecond !== 'undefined' ? defaults.colums[colVal].timePicker.hasSecond : true,
                                            timeFormat: defaults.colums[colVal].timePicker.timeFormate !== 'undefined' ? defaults.colums[colVal].timePicker.timeFormate : 'HH:mm:ss',
                                            dateFormat: defaults.colums[colVal].datePicker.dateFormate !== 'undefined' ? defaults.colums[colVal].datePicker.dateFormate : "yy-mm-dd"
                                        });
                                    } else if(defaults.colums[colVal].datePicker) {
                                        //说明只有datePicker
                                        $( "#"+ colId  ).datepicker({dateFormat: defaults.colums[colVal].datePicker.dateFormate !== 'undefined' ? defaults.colums[colVal].datePicker.dateFormate : "yy-mm-dd"});
                                    } else if(defaults.colums[colVal].timePicker) {
                                        //说明只有timePicker
                                        $('#' + colId).timepicker({
                                            showHour : defaults.colums[colVal].timePicker.hasHour !== 'undefined' ? defaults.colums[colVal].timePicker.hasHour : true,
                                            showMinute : defaults.colums[colVal].timePicker.hasMinute !== 'undefined' ? defaults.colums[colVal].timePicker.hasMinute : true,
                                            showSecond: defaults.colums[colVal].timePicker.hasSecond !== 'undefined' ? defaults.colums[colVal].timePicker.hasSecond : true,
                                            timeFormat: defaults.colums[colVal].timePicker.timeFormate !== 'undefined' ? defaults.colums[colVal].timePicker.timeFormate : 'HH:mm:ss'
                                        });
                                    }


                                } else if(defaults.colums[colVal].type == "button") {
                                    //$innerDivInput = $("<input type='text' />").val($(this).text()).width(tdWidth - 6);
                                    $innerDiv.width(tdWidth).css("margin-left","1px").css("margin-top", (thisHeight - tem[0])/2 + 1);
                                    $thisEditor.append($innerDiv);
                                } else if(defaults.colums[colVal].type == "text") {
                                    $innerDivInput = $("<input type='text' />").val($(this).text()).width(tdWidth - 6);
                                    $innerDiv.append($innerDivInput).width(tdWidth).css("margin-left","1px").css("margin-top", (thisHeight - tem[0])/2 + 1);
                                    $thisEditor.append($innerDiv);
                                    if(defaults.colums[colVal].disabled) {
                                        $innerDivInput.attr("disabled", "disabled")
                                    }
//									$innerDivInput = $("<input type='text' />").val($(this).text()).width(tdWidth - 6);
//									$innerDiv.append($innerDivInput).width(tdWidth).css("margin-left","1px").css("margin-top", (thisHeight - tem[0])/2 + 1);
//									$thisEditor.append($innerDiv);
                                } else {
                                    //首先获得当前select的值
                                    var thisSelVal = $(this).text();
                                    //首先生成select
                                    var $select = $("<select></select>").width(tdWidth - 20);
                                    for(var i = 1; i < defaults.colums[colVal].type.length; i++) {
                                        if(defaults.colums[colVal].type[i][1] == thisSelVal) {
                                            $select.append("<option value='"+ defaults.colums[colVal].type[i][0] +"' selected='selected'>" + defaults.colums[colVal].type[i][1] + "</option>");
                                        } else {
                                            $select.append("<option value='"+ defaults.colums[colVal].type[i][0] +"'>" + defaults.colums[colVal].type[i][1] + "</option>");
                                        }
                                    }
                                    $innerDiv.append($select).width(tdWidth).css("margin-left","1px").css("margin-top", (thisHeight - tem[0])/2 + 1);
                                    $thisEditor.append($innerDiv);
                                    if(defaults.colums[colVal].disabled) {
                                        $select.attr("disabled", "disabled");
                                    }
                                }

                            }
                        }

                    });
                    /*
                     for(var l = 0; l < idArray.length; l++) {





                     $( "#"+ idArray[l]  ).datetimepicker({
                     showSecond: true,
                     timeFormat: 'mm:ss'
                     //showOn: "button",
                     //buttonImage: "images/icon/calendar.gif",
                     //buttonImageOnly: true
                     });

                     }
                     */
                    idArray = [];



                } else {

                    var flag = true;
                    $(this).find("td").each(function(i){
                        var $innerDiv = $("<div class='inner_div'></div>");
                        var tdWidth = $(this).innerWidth();
                        var $innerDivInput = "";
                        if($(this).find("input[type='checkbox']").length > 0) {
                            if($(this).find("input[type='checkbox']").attr("checked")) {
                                $innerDivInput = $innerDiv.append("<input type='checkbox' checked='checked' />").width(tdWidth).css("margin-top", thisHeight - tem[0] - 1);
                                $rowEditorDiv.append($innerDiv);
                            } else {
                                $innerDivInput = $innerDiv.append("<input type='checkbox' />").css("margin-top", thisHeight - tem[0] - 1);
                                $innerDiv.width(tdWidth).find("input").width(tdWidth - 6);
                                $rowEditorDiv.append($innerDiv);
                            }
                        } else {
                            if(flag) {
                                $innerDiv.append($(this).html()).width(tdWidth).height(height).css("line-height", height+"px");
                                $rowEditorDiv.append($innerDiv);
                                flag = false;
                            } else {
//##############################首先要根据type属性，判断他是不是datepicker
                                var colTem = $(this).attr("col");
                                var colOne = colTem.split("_");
                                var colVal = colOne[1];
                                var colId = "in_" + colTem;

                                if(defaults.colums[colVal].type == "date") {
                                    if(defaults.colums[colVal].disabled) {
                                        $innerDivInput = $("<input type='text' />").val($(this).text()).width(tdWidth - 6).attr("disabled", "disabled");
                                    } else {
                                        idArray.push(colId);
                                        $innerDivInput = $("<input id="+ colId + "  type='text' />").val($(this).text()).width(tdWidth - 6);
                                    }
                                    $innerDiv.append($innerDivInput).width(tdWidth).css("margin-left","1px").css("margin-top", (thisHeight - tem[0])/2 + 1);
                                    $rowEditorDiv.append($innerDiv);

                                } else if(defaults.colums[colVal].type == "button") {
                                    //$innerDivInput = $("<input type='text' />").val($(this).text()).width(tdWidth - 6);
                                    $innerDiv.width(tdWidth).css("margin-left","1px").css("margin-top", (thisHeight - tem[0])/2 + 1);
                                    $rowEditorDiv.append($innerDiv);
                                } else if(defaults.colums[colVal].type == "text") {
                                    $innerDivInput = $("<input type='text' />").val($(this).text()).width(tdWidth - 6);
                                    $innerDiv.append($innerDivInput).width(tdWidth).css("margin-left","1px").css("margin-top", (thisHeight - tem[0])/2 + 1);
                                    $rowEditorDiv.append($innerDiv);
                                    if(defaults.colums[colVal].disabled) {
                                        $innerDivInput.attr("disabled", "disabled")
                                    }
                                } else {
                                    //首先获得当前select的值
                                    var thisSelVal = $(this).text();
                                    //首先生成select
                                    var $select = $("<select></select>").width(tdWidth - 6);
                                    for(var i = 1; i < defaults.colums[colVal].type.length; i++) {
                                        if(defaults.colums[colVal].type[i][1] == thisSelVal) {
                                            $select.append("<option value='"+ defaults.colums[colVal].type[i][0] +"' selected='selected'>" + defaults.colums[colVal].type[i][1] + "</option>");
                                        } else {
                                            $select.append("<option value='"+ defaults.colums[colVal].type[i][0] +"'>" + defaults.colums[colVal].type[i][1] + "</option>");
                                        }
                                    }
                                    $innerDiv.append($select).width(tdWidth).css("margin-left","1px").css("margin-top", (thisHeight - tem[0])/2 + 1);
                                    $rowEditorDiv.append($innerDiv);
                                    if(defaults.colums[colVal].disabled) {
                                        $select.attr("disabled", "disabled");
                                    }

                                }
                            }
                        }

                    });

                    //生成upadte和cancel
                    var gWidth = $gView.width();
                    var bLeft = (gWidth - 200) / 2;
                    buttonLeft = bLeft;
                    //一般情况下初始化的时候是在下边，但是当到了表格的第一条和最后一条道的时候，就不能这样了，所以得改下
                    var $buttonDiv = $("<div class='button_div'></div>");
                    var bunDivHt = 32;
                    var thisIndex = $(this).index();
                    var allTrNum = $gView.find("table").find("tr").length;
                    //判断是不是最后一个，只要不是最后一个，其他的都是到下边
                    if(thisIndex < allTrNum - 2 || thisIndex == 1 || thisIndex == 2) {
                        $buttonDiv.addClass("downDiv");
                        $buttonDiv.width(200).css({"background-color":"#EAF1FB", "top":height, "left":bLeft});
                        $rowEditorDiv.width(width).height(height);
                        $rowEditorDiv.css({"top":top + 1 + $gView.scrollTop(), "left":left});
                    } else {
                        $buttonDiv.addClass("upDiv");
                        $buttonDiv.width(200).css({"background-color":"#EAF1FB", "top":-bunDivHt - 1, "left":bLeft});
                        $rowEditorDiv.width(width).height((thisHeight - tem[0])/2 + thisHeight);
                        $rowEditorDiv.css({"top":top-2 + $gView.scrollTop(), "left":left});
                    }
                    var $innerButtonDiv = $("<div class='inner_button_div'></div>");
                    var $leftD = $("<div class='left_div'></div>").bind("mousedown", buttonMove);
                    var $rightD = $("<div class='right_div'></div>").bind("mousedown", buttonMove);
                    var $leftButton = $("<input class='myButton' type='button' value='确定'/>").bind("click", checkData);
                    var $rightButton = $("<input class='myButton' type='button' value='取消' />").bind("click", cancelData);
                    $innerButtonDiv.append($leftButton, $rightButton).width(120);
                    $buttonDiv.append($leftD, $innerButtonDiv, $rightD);
                    $innerButtonDiv.css("left", ($buttonDiv.width() - $innerButtonDiv.width()) / 2);
                    $rowEditorDiv.append("<div class='clear'></div>").append($buttonDiv);
                    $gView.append($rowEditorDiv);

                    for(var l = 0; l < idArray.length; l++) {
                        var colOne = idArray[l].split("_");
                        var colVal = colOne[2];
                        if(defaults.colums[colVal].datePicker && defaults.colums[colVal].timePicker) {
                            //说明两个都有呗
                            $( "#"+ idArray[l]  ).datetimepicker({
                                showHour : defaults.colums[colVal].timePicker.hasHour !== 'undefined' ? defaults.colums[colVal].timePicker.hasHour : true,
                                showMinute : defaults.colums[colVal].timePicker.hasMinute !== 'undefined' ? defaults.colums[colVal].timePicker.hasMinute : true,
                                showSecond: defaults.colums[colVal].timePicker.hasSecond !== 'undefined' ? defaults.colums[colVal].timePicker.hasSecond : true,
                                timeFormat: defaults.colums[colVal].timePicker.timeFormate !== 'undefined' ? defaults.colums[colVal].timePicker.timeFormate : 'HH:mm:ss',
                                dateFormat: defaults.colums[colVal].datePicker.dateFormate !== 'undefined' ? defaults.colums[colVal].datePicker.dateFormate : "yy-mm-dd"
                            });
                        } else if(defaults.colums[colVal].datePicker) {
                            //说明只有datePicker
                            $( "#"+ idArray[l]  ).datepicker({dateFormat: defaults.colums[colVal].datePicker.dateFormate !== 'undefined' ? defaults.colums[colVal].datePicker.dateFormate : "yy-mm-dd"});
                        } else if(defaults.colums[colVal].timePicker) {
                            //说明只有timePicker
                            $('#' + idArray[l]).timepicker({
                                showHour : defaults.colums[colVal].timePicker.hasHour !== 'undefined' ? defaults.colums[colVal].timePicker.hasHour : true,
                                showMinute : defaults.colums[colVal].timePicker.hasMinute !== 'undefined' ? defaults.colums[colVal].timePicker.hasMinute : true,
                                showSecond: defaults.colums[colVal].timePicker.hasSecond !== 'undefined' ? defaults.colums[colVal].timePicker.hasSecond : true,
                                timeFormat: defaults.colums[colVal].timePicker.timeFormate !== 'undefined' ? defaults.colums[colVal].timePicker.timeFormate : 'HH:mm:ss'
                            });
                        }

                        /*
                         $( "#"+ idArray[l]  ).datetimepicker({
                         //showOn: "button",
                         //buttonImage: "images/icon/calendar.gif",
                         //buttonImageOnly: true
                         showSecond: true,
                         timeFormat: 'HH:mm:ss'
                         });
                         */
                    }

                    idArray = [];
                }
                return false;
            }

            //确定按钮
            function checkData() {
                //改一次发送一次ajax请求
                var $thisRowEd = $(this).parents(".rowEditorDiv");
                var i = 0;
                var obj = {};
                $thisRowEd.find(".inner_div").each(function(){
                    //把所有的列封装成一个属性
                    if($(this).find("input:not(:checkbox)").length > 0 || $(this).find("select").length > 0) {
                        if($(this).find("input").length > 0) {
                            obj[defaults.colums[i].name] = $(this).find("input").val();
                            i++;
                        } else {
                            obj[defaults.colums[i].name] = $(this).find("select").val();
                            i++;
                        }
                    }
                });
                var tem = $thisTr.attr("id");
                id = tem.split("_")[1];
                obj.id = id;
                $.ajax({
                    type: "POST",
                    url: defaults.modifyUrl,
                    data: obj,
                    dataType: "text",
                    success: function(msg){
                        if(msg == "success") {
                            var newObj = {};
                            newObj.pageNo = pB;
                            newObj.rowNum = defaults.rowNum;
                            //obj.queryData = defaults.queryData;
                            //console.log(defaults.data)
                            newObj = $.extend(defaults.data,newObj);
                            $.ajax({
                                url: defaults.url,
                                dataType: "json",
                                type : defaults.type,
                                data : newObj,
                                error: function (XMLHttpRequest, errorThrown) {
                                    alert("数据加载出错！" + errorThrown);
                                },
                                success: function(myData){
                                    $(".rowEditorDiv", $gView).remove();
                                    _data = myData;
                                    if(_data.total < pB * defaults.rowNum) {
                                        end = _data.total;
                                    } else {
                                        end = pB * defaults.rowNum;
                                    }
                                    cancelCheck();
                                    $('table', $gView).remove();
                                    hideCol();
                                }
                            });
                        }

                    }
                });

            }
			
            //取消按钮
            function cancelData() {
                $(this).parents(".rowEditorDiv").remove();
            }

            //button中的div的移动方法
            function buttonMove(e) {
                e = e || event;
                _move = true;
                var oldX = e.pageX;
                var newX = 0;
                var oldY = e.pageY;
                var newY = 0;
                var self = $(this);
                var thisHeight = self.parent().height(); //包含确定取消的div
                var parDivHeight = self.parent().parent().height(); //整个编辑的div
                var oldTop = self.parent().position().top;

                $(document).mousemove(function(e){
                    newX = e.pageX;
                    var oldLeft = self.parent().position().left;
                    if(newX < oldX) {
                        //说明向左拖拽，左边界我不判断了，只判断右边界，先把样式改改吧
                        var midX = oldX - newX;
                        if(oldLeft - midX > 5) {
                            self.parent().css("left", oldLeft - midX);
                        } else {
                            self.parent().css("left", 0);
                        }
                        buttonLeft = oldLeft - midX;
                    }
                    if(newX > oldX) {
                        //说明向右拖拽
                        var midX = newX - oldX;
                        if(oldLeft + midX < $gView.find("table").width() - 202) {
                            self.parent().css("left", oldLeft + midX);
                        }
                        buttonLeft = oldLeft + midX;
                    }
                    oldX = newX;
                    return false;
                }).mouseup(function(e){
                        $(document).unbind('mousemove mouseup');
                        newY = e.pageY;
                        var tem = oldTop - thisHeight - parDivHeight - 1;
                        var temStr = oldTop + thisHeight + parDivHeight + 1;
                        //如果符合下边两个条件，说明他在下边
                        var thisIndex = $thisTr.index();
                        var allTrNum = $gView.find("table").find("tr").length;
                        if((oldY - newY) > 20 && self.parent().hasClass("downDiv")) {
                            //向上的话得先判断是不是在前两行，在前两行是不能向上的
                            //var $thisTr
                            if(thisIndex != 1 && thisIndex != 2) {
                                self.parent().removeClass("downDiv").addClass("upDiv");
                                self.parent().animate({top: tem + "px"}, 200 );
                            }
                        }
                        if((newY - oldY) > 20 && self.parent().hasClass("upDiv")) {
                            if(thisIndex < allTrNum -2) {
                                self.parent().removeClass("upDiv").addClass("downDiv");
                                self.parent().animate({top: temStr + "px"}, 200 );
                            }
                        }
                        //其实他就是通过下边这两个DOM元素来实现的标志的
                    });
                return false;
            }

            function isAllSelected(){
                for(var i = 0, len = allCh.length; i < len; i++){
                    if(allCh[i] == false){
                        return false
                    };
                };
                return true;
            };

            function greadPage(){
                var $gPage = $('<div class="grid_page"><div class="grid_page_box"></div></div>');
                var $pBox = $('div.grid_page_box',$gPage);
                var $p = $('<div class="prick"></div>');
                ps = Math.ceil(_data.total/defaults.rowNum);
                if(defaults.pages.goPage) {
                    var goTo = '<div class="grid_entry"><select name="grid_pages">';
                    for(i = 1; i < ps + 1; i++){
                        goTo = goTo + '<option>' + i + '</option>';
                    };
                    goTo = goTo + '</select></div>';
                    $pBox.append(goTo,'<div class="prick"></div>');
                    pSel = $('select', $pBox);
                    pSel.bind('change', {b:'select'}, jampPage);
                };
                if(defaults.pages.paging){
                    var $pagings = $('<span><b class="grid_page_fist unclick"></b></span><span><b class="grid_page_prev unclick"></b></span><div class="prick"></div><div class="grid_note"><input name="" type="text" class="page_nub" value="1" />页 共 '+ ps +' 页</div><div class="prick"></div><span><b class="grid_page_next"></b></span><span><b class="grid_page_last"></b></span><div class="prick"></div>');
                    pFirst = $('b.grid_page_fist',$pagings);
                    pPrev = $('b.grid_page_prev',$pagings);
                    pNub = $('input.page_nub',$pagings);  //页面输入框
                    pNext = $('b.grid_page_next', $pagings); //跳转到表格的下一页
                    pLast = $('b.grid_page_last', $pagings); //跳转到表格的最后一页


                    if(pageUnclik){
                        pNext.addClass('unclick');
                        pLast.addClass('unclick');
                    };
                    $pBox.append($pagings);
                    pFirst.bind("click",{b:'first'},pageFn);
                    pPrev.bind("click",{b:'prev'},pageFn);
                    pNext.bind("click",{b:'next'},pageFn);
                    pLast.bind("click",{b:'last'},pageFn);

                    pNub.bind('keyup',{b:'nub'},jampPage);
                };
                if(defaults.pages.renew){
                    var $refresh = $('<span title="刷新"><b class="grid_refresh"></b></span>');
                    $pBox.append($refresh,$p);
                    $refresh.bind('click',pageRefresh);
                };
                if(defaults.pages.info){
                    var $info = $('<div class="grid_info">每页显示 '+ defaults.rowNum +' 条数据 - 共 '+ total +' 条数据</div>');
                    $pBox.append($info);
                };
                $gBox.append($gPage);
            };

            function jampPage(e){
                //如果有修改的div存在，先删除他
                $(".rowEditorDiv", $gView).remove();
                var path = e.data.b,
                    old = pB,
                    on = 'unclick',
                    un = !$(this).hasClass(on),

                    lHas = pLast.hasClass(on),

                    fHas = pFirst.hasClass(on);
                if(path == 'nub'){
                    if(e.keyCode == 13){
                        var tempNum = pNub.val();
                        var ok = tempNum >= 1 && tempNum <= ps;
                        if(ok){
                            pB = tempNum;
                        };
                        str = (pB - 1) * defaults.rowNum;
                        end = pB * defaults.rowNum;
                        if(ok){
                            if(tempNum == 1){
                                if(!fHas){
                                    pFirst.addClass(on);
                                    pPrev.addClass(on);
                                };
                                if(lHas){
                                    pNext.removeClass(on);
                                    pLast.removeClass(on);
                                };
                            }else if(tempNum == ps){
                                if(end > _stop)
                                    end = _stop;
                                if(!lHas){
                                    pNext.addClass(on);
                                    pLast.addClass(on);
                                };
                                if(fHas){
                                    pFirst.removeClass(on);
                                    pPrev.removeClass(on);
                                };
                            }else{
                                if(lHas || fHas){
                                    pNext.removeClass(on);
                                    pLast.removeClass(on);
                                    pFirst.removeClass(on);
                                    pPrev.removeClass(on);
                                };
                            };
                        }
                        $(this).blur();
                    }else{
                        return false;
                    };
                }else if(path == 'select'){
                    //pB代表你选择的select的值
                    pB = $(this).val();
                    if(pB == 1){
                        if(!fHas){
                            pFirst.addClass(on);
                            pPrev.addClass(on);
                        };
                        if(lHas){
                            pNext.removeClass(on);
                            pLast.removeClass(on);
                        };
                    }else if(pB == ps){
                        //进入这个if表示当前select选择的是最后一页
                        if(end > _stop)
                            end = _stop;
                        if(!lHas){
                            pNext.addClass(on);
                            pLast.addClass(on);
                        };
                        if(fHas){
                            pFirst.removeClass(on);
                            pPrev.removeClass(on);
                        };
                    }else{
                        if(lHas || fHas){
                            pNext.removeClass(on);
                            pLast.removeClass(on);
                            pFirst.removeClass(on);
                            pPrev.removeClass(on);
                        };
                    };
                    str = (pB - 1) * defaults.rowNum;
                    end = pB * defaults.rowNum;
                    if(end > _stop)
                        end = _stop;
                };
                rePage();
				if(defaults.isCanModify) {
					//加入双击修改的bind
					$('table', $gView).find("tr").bind("dblclick", createTrShadow);
				}
				var checkboxFun = typeof defaults.checkboxClickFun;
				if(defaults.checkboxClickFun != "" && defaults.checkboxClickFun != null && checkboxFun == "function") {
					defaults.checkboxClickFun();
                    $('tr',$gView).unbind("click");
				}
            };
			
            function pageFn(e){
                //如果有修改的div存在，先删除他
                $(".rowEditorDiv", $gView).remove();
                var path = e.data.b,
                    on = 'unclick',
                    un = !$(this).hasClass(on),
                    lHas = pLast.hasClass(on),
                    fHas = pFirst.hasClass(on);
                if(path == 'first' && un){
                    if(!fHas){
                        pFirst.addClass(on);
                        pPrev.addClass(on);
                        pNext.removeClass(on);
                        pLast.removeClass(on);
                    };
                    pB = 1;
                    str = 0;
                    end = defaults.rowNum;
                }else if(path == 'prev' && un){
                    if(lHas){
                        pNext.removeClass(on);
                        pLast.removeClass(on);
                    };
                    pB --;
                    if(pB <= 1){
                        pB = 1;
                        pFirst.addClass(on);
                        pPrev.addClass(on);
                    };
                    str = (pB - 1) * defaults.rowNum;
                    end = pB * defaults.rowNum;
                }else if(path == 'next' && un){
                    if(!lHas){
                        pFirst.removeClass(on);
                        pPrev.removeClass(on);
                    };
                    pB ++;
                    if(pB >= ps){
                        pB = ps;
                        pNext.addClass(on);
                        pLast.addClass(on);
                    };
                    str = (pB - 1) * defaults.rowNum;
                    end = pB * defaults.rowNum;
                    if(end > _stop)
                        end = _stop;
                }else if(path == 'last' && un){
                    if(!lHas){
                        pNext.addClass(on);
                        pLast.addClass(on);
                        pFirst.removeClass(on);
                        pPrev.removeClass(on);
                    };
                    pB = ps;
                    str = (pB - 1) * defaults.rowNum;
                    end = _stop;
                }else{
                    return false;
                };
                rePage();
                //加入双击修改的bind
				if(defaults.isCanModify) {
					$('table', $gView).find("tr").bind("dblclick", createTrShadow);
				}
				var checkboxFun = typeof defaults.checkboxClickFun;
				if(defaults.checkboxClickFun != "" && defaults.checkboxClickFun != null && checkboxFun == "function") {
					defaults.checkboxClickFun();
                    $('tr',$gView).unbind("click");
				}
            };
			
            function cancelCheck(){
                if($(".chex").attr("checked")) {
                    $(".chex").attr("checked", false);
                }
            }

            function rePage(){
                var obj = {};
                obj.pageNo = pB;
                obj.rowNum = defaults.rowNum;
              //  obj.queryData = defaults.queryData;
                obj = $.extend(defaults.data,obj);
				if(defaults.afterRepage) {
					$.ajax({
						url: defaults.url,
						dataType: "json",
						type: defaults.type,
						data: obj,
						async:false,
						//processData: false,
						beforeSend : function(){
	
						},
						error: function (XMLHttpRequest, errorThrown) {
							alert("数据加载出错！" + errorThrown);
						},
						success: function(myData){
							_data = myData;
							if(_data.total < pB * defaults.rowNum) {
	
								end = _data.total;
							} else {
								end = pB * defaults.rowNum;
							}
                            str = 0;
							cancelCheck();
                            hids=[];
                            //alert(str)
						}
					});	
				}
                $('table', $gView).remove();
                loading();
                if(hids.length > 0){
                    hideCol();
                }else{
                    initGrid(_data, str, end, colAttributes);
                };
                if(defaults.pages.paging)
                    pNub.val(pB);
                if(defaults.pages.goPage)
                    pSel.val(pB);
				//在这里调用改变顺序的方法
				$(".gird_head_column").each(function(){
					if($(this).find("span").length > 0) {
						//查看span中是否有up或者down的class
						var $thisSpan = $(this).find("span").eq(0);
						if($thisSpan.hasClass("down")) {
							//"click", {b:'up'} ,colSequence
							var myE = {};
							var myData = {};
							myE.data = myData;
							myE.data.b = "down";
							myE.data.funUse = true;
							colSequence(myE, $(this));
							return false;	
						} else if($thisSpan.hasClass("up")){
							var myE = {};
							var myData = {};
							myE.data = myData;
							myE.data.b = "up";
							myE.data.funUse = true;
							colSequence(myE, $(this));
							return false;	
						}	
					}	
				})
            };
			
            function hideCol(){
                var oldTabW = $gView.width();
                var dataTemp = {};
                var dataRows = _data.rows;
                dataTemp.rows = [];
                var colAttributesTemp = {};
                colAttributesTemp.widths = [];
                colAttributesTemp.aligns = [];
                colAttributesTemp.colors = [];
                for(var i=0, k=0; i < dataRows.length; i++, k++){
                    var rowData = dataRows[i];
                    var rowCells = rowData.cell;
                    var rowCellsTemp = [];
                    for(var j = 0, len = hids.length; j<len; j++){
                        var ok = hids[j];
                        var index = ok.name.substring(ok.name.indexOf("_") + 1);
                        rowCellsTemp.push(rowCells[index]);
                        if(k == 0){
                            colAttributesTemp.widths[j] = colAttributes.widths[index];
                            colAttributesTemp.aligns[j] = colAttributes.aligns[index];
                            colAttributesTemp.colors[j] = colAttributes.colors[index];
                        };
                    };
                    dataTemp.rows[k] = {};
                    dataTemp.rows[k].cell = rowCellsTemp;
                };
				if(defaults.frontJumpPage) {
					initGrid(_data, str, end, colAttributesTemp);
				} else {
					initGrid(_data,0,end - str,colAttributesTemp);	
				}
                
                var tr = $("table > tbody > tr",$gView);
                var hideWidth = 0,tdW = 0;
                tr.each(function(j){
                    for(i = 0; i < hids.length; i++){
                        var the = i;
                        if(defaults.multiselect){
                            the = the + 1;
                        };
                        if(defaults.number){
                            the = the + 1;
                        };
                        var hides = hids[i].isHide;
                        var index = hids[i].index;
                        var td;
                        if(hides){
                            td = $("td",$(this)).eq(the);
                            tdW = td.width();
                            td.hide();
                        };
                        if(j == 0){
                            hideWidth = hideWidth + tdW;
                        };
                    };
                });
                var newTabW = $gHeader.width();
                $('table',$gView).width(newTabW);
            };
			
            function pageRefresh(){
                var sM = $('div.grid_head_menu > div.grid_head_menu',$gBox).find('label');
                formatHead();
                $('table',$gView).remove();
                loading();
                if(defaults.pages.goPage || defaults.pages.paging){
                    var col = defaults.colums;
                    hids = [];
                    for(var i = 0; i < col.length; i++) {
                        formatHids(i);
                    };
                };
                initGrid(_data, 0, end-str, colAttributes);
                sM.each(function(){
                    if(!$(this).hasClass('chected')){
                        $(this).addClass('chected');
                    };
                });
            };
            
			function formatHead(){
                var cols = $('div.cols',$gHeader);
                var hidTh = $('div.cols:hidden',$gHeader);
                for(i = 0; i < cols.length; i++){
                    var wid = colAttributes.widths[i];
                    cols.eq(i).width(wid);
                };
                if(hidTh.length > 0){
                    hidTh.show();
                };
                if($checkBox.attr("checked")){
                    $checkBox.attr("checked", false);
                };
            };
            
			function trClick(){
                if(defaults.multiselect){
                    var cInpt = $("td:first > input",$(this));
                    var theI = $(this).index() - 1;
                    if(defaults.radioSelect){
                        $(this).siblings().removeClass("select");
                    };
                    if($(this).hasClass('select')){
                        $(this).removeClass('select');
                        if(defaults.clickSelect){
                            cInpt.attr("checked",false);
                        }
                        allCh[theI] = false;
                    }else{
                        $(this).addClass('select');
                        if(defaults.clickSelect){
                            cInpt.attr("checked",true);
                        }
                        allCh[theI] = true;
                        trId = $(this).attr("id");
                    };
                    if(isAllSelected()){
                        $checkBox.attr("checked",true);
                    }else{
                        $checkBox.attr("checked",false);
                    };
                }else{
                    if($(this).hasClass('select')){
                        $(this).removeClass('select');
                    }else{
                        $(this).siblings().removeClass('select');
                        $(this).addClass('select');
                    };
                };
            };
			
            function allCheckedChange(isChecked){
                for(var i = 0, len = allCh.length; i < len; i++){
                    allCh[i] = isChecked;
                };
            };
            
			function allClick(){
                var trs = $("table tr",$gView);
                var cInpt = $("table td input[name=g_check]",$gView);
                if($(this).attr("checked")=="checked"){
                    cInpt.attr("checked",true);
                    trs.addClass('select');
                    allCheckedChange(true);
                }else{
                    cInpt.attr("checked",false);
                    trs.removeClass('select');
                    allCheckedChange(false);
                }
            };

            //加载动画
            function loading(){
                if($('div.loading',$gView).length > 0){
                    $load.show();
                }else{
                    if(defaults.height){
                        $load.css("padding-top",defaults.height/2 - 20)
                    }
                    $gView.append($load);
                };
            }

            function colSequence(e, $thisHdCo){
                if(e.data != null) {
					if(e.data && e.data.funUse) {
						
					} else {
						var path = e.data.b;
						var tem = judgeMenu(path);
						if(tem == false) {
							return true;
						}	
					}
                    
                }
                var isAsc;
                var sorts = [];
				var way = "";
				var ways = "";
				if( e.data && e.data.funUse) {
					way = $thisHdCo.children("span");
					ways = $thisHdCo.siblings().find("span");
				} else {
					way = $(this).children("span");
					ways = $(this).siblings().find("span");
				}
                //var way = $(this).children("span");
                //var ways = $(this).siblings().find("span");
                var listData = $('table > tbody > tr:not(:first)', $gView);
				var tdIndex = "";
				if(e.data && e.data.funUse) {
					tdIndex = $thisHdCo.index();	
				} else {	
					tdIndex = $(this).index();
				}
                listData.each(function(i){
                    var colsText = $(this).children('td').eq(tdIndex).text();
                    var obj = new sortInfo(i, colsText);
                    sorts.push(obj);
                });
                ways.removeClass();
				
				if(way.parent().text() != "升序排序" && way.parent().text() != "降序排序") {
					if(way.hasClass('up')){
						if(e.data && e.data.funUse) {
						} else {
							way.removeClass('up').addClass('down');	
						}
					}else{
						if(e.data && e.data.funUse) {		
						} else {
							way.removeClass('down').addClass('up');
						}
					
					}
				}	
                
                if(way.hasClass('down')){
                    isAsc = true;
                }else{
                    isAsc = false;
                };
                sortFn(sorts,isAsc);
                var sortedTrs = "";
                $('table > tbody > tr:not(:first)',$gView).remove();
                listData.removeClass("odd");
                for(var i=0, len=sorts.length; i<len; i++){
                    var j = i + 1 & 1;
                    var index = sorts[i].index;
                    if(defaults.number){
                        $('td.number', listData.get(index))
                            .attr("title",i + 1)
                            .children('span').text(i + (pB-1)*defaults.rowNum + 1).attr("title", i + (pB-1)*defaults.rowNum + 1);
                    };
                    if(j == 0){
                        listData.eq(index).addClass("odd");
                    }
                    sortedTrs = sortedTrs + listData.get(index).outerHTML;
                };
                listData.remove();
                $('table > tbody',$gView).append(sortedTrs);
                //加入双击修改的bind
				if(defaults.isCanModify) {
					$('table', $gView).find("tr").bind("dblclick", createTrShadow);
				}
				var checkboxFun = typeof defaults.checkboxClickFun;
				if(defaults.checkboxClickFun != "" && defaults.checkboxClickFun != null && checkboxFun == "function") {
					defaults.checkboxClickFun();
                    $('tr',$gView).unbind("click");
				}
            };
            var upFlag = false;
            var downFlag = false;
            function judgeMenu(tem){
				if(tem == "up") {
					if(!upFlag) {
						upFlag = true;
						downFlag = false;
						return true;
					}
					return false;
				} else {
					if(!downFlag) {
						downFlag = true;
						upFlag = false;
						return true;
					}
					return false;
				}		
                
            }

            //快速排序
            function sortFn(arr,isAsc){
                return quickSort(arr, 0, arr.length - 1);
                function quickSort(arr,l,r){
                    if(l<r){
                        var mid=arr[parseInt((l+r)/2)].text,
                            i=l-1,
                            j=r+1;
                        while(true){
                            if(isAsc){
                                while(arr[++i].text < mid);
                                while(arr[--j].text > mid);
                            }else{
                                while(arr[++i].text > mid);
                                while(arr[--j].text < mid);
                            }
                            if(i>=j)break;
                            var temp=arr[i];
                            arr[i]=arr[j];
                            arr[j]=temp;
                        }
                        quickSort(arr,l,i-1);
                        quickSort(arr,j+1,r);
                    }
                    return arr;
                };
            };

            //siblings找的是同辈的元素，挺好用的，用到它好几次了。
            function changeNeedle(){
                $(this)
                    .addClass('hover')
                    .siblings().removeClass('th_change');
                var offset = $(this).offset();
                var mL = parseInt($gHeader.css("margin-left"));
                var x = offset.left - g.offset().left + $(this).width() + Math.abs(mL) - 5;
                var y = offset.top - g.offset().top - 1;
                $needle.css({'left':x,'top':y});
                $(this).addClass('th_change');
            };
			
            function unHover(){
                $(this).removeClass('hover')
            };
			
            //列排序
            function moveThead(e){
                var $T = $(this);
                $T.siblings(".cols").addClass("can");
                $T.removeClass("can next");
                $T.nextAll().addClass("next");
                var oldL = $(this).offset().left;
                var oldT = $(this).offset().top;
                var $Tt = $('<div class="g_indicator no">' + $T.text() + '</div>');
                var $P = $('<div class="g_pointer"></div>');
                $gBox.append($Tt,$P);
                _move = true;
                _x = $T.offset().left;
                $(document).mousemove(function(e){
                    var $e = $(e.target);
                    var has = $e.hasClass("can");
                    var par = $e.parent().hasClass("can");
                    var nex = $e.hasClass("next");
                    var pne = $e.parent().hasClass("next");
                    var l,t;
                    if(_move){
                        x = e.clientX;
                        y = e.clientY + 20;
                        $Tt.css({"left":x,"top":y});
                        if(nex || pne){
                            $Tt.removeClass("no");
                            if(nex){
                                l = $e.offset().left +  $e.width() - g.offset().left - 8;
                                t = $e.height();
                            }else{
                                l = $e.parent().offset().left +  $e.parent().width() - g.offset().left - 8;
                                t = $e.parent().height();
                            };
                            $P.css({"left":l,"top":t});
                        }else if(has || par){
                            $Tt.removeClass("no");
                            if(has){
                                l = $e.offset().left - g.offset().left - 8;
                                t = $e.height();
                            }else{
                                l = $e.parent().offset().left - g.offset().left - 8;
                                t = $e.parent().height();
                            }
                            $P.css({"left":l,"top":t});
                        }else{
                            $Tt.addClass("no");
                            $P.attr("style","")
                        };
                    };
                    return false;
                }).mouseup(function(e){
                        _move=false;
                        $(document).unbind('mousemove mouseup');
                        if($Tt.hasClass("no")){
                            $Tt.animate({top:oldT,left:oldL,opacity:0},400).queue(function(){
                                $Tt.remove();
                                $P.remove();
                            });
                        }else{
                            var point = $(e.target);
                            var oCol = $T.index();
                            var nex = point.hasClass("next") || point.parent().hasClass("next");
                            var bOra = true;
                            $Tt.remove();
                            $P.remove();
                            if(point.parent().is(".cols")){
                                point = point.parent();
                            };
                            var nCol = point.index();
                            if(nex){
                                $T.insertAfter(point);
                                bOra = false;
                            }else{
                                $T.insertBefore(point);
                            };
                            $T.siblings().removeClass("can next");
                            formatCol(oCol,nCol,bOra);
                            if(hids.length > 0){
                                if(defaults.multiselect){
                                    oCol = oCol - 1;
                                    nCol = nCol - 1;
                                };
                                if(defaults.number){
                                    oCol = oCol - 1;
                                    nCol = nCol - 1;
                                };
                                var temp = hids[oCol];
                                hids.splice(oCol,1);
                                hids.splice(nCol,0,temp);
                            };
                        }
                    });
                return false;
            };
			
            //打印obj
            function testObj(arr){
                var result = "";
                for(var i=0;i<arr.length;i++){
                    result += arr[i].getInfo() + ", ";
                }
                return result;
            };

            function sortInfo(index,text){
                this.index = index;
                this.text = text;
                this.getInfo = function(){
                    return this.index + ": " + this.text;
                }
            }
            //排td位置
            function formatCol(oCol,nCol,bOra){
                var $t = $('table > tbody > tr',$gView);
                $t.each(function(){
                    var oT = $(this).children("td");
                    if(bOra){
                        oT.eq(oCol).insertBefore(oT.eq(nCol));
                    }else{
                        oT.eq(oCol).insertAfter(oT.eq(nCol));
                    };
                });
            }

            //生成menu
            function greatMenu(){
                var gMb = '<div class="grid_head_menu"><ul>';
                if(defaults.sorts){
                    gMb += '<li><div><span class="up"></span>升序排序</div></li><li><div><span class="down"></span>降序排序</div></li>';
                };
                if(defaults.colDisplay){
                    gMb += '<li class="parting"><div><b class="parent"></b><span class="veiw"></span>列显示</div></li></ul>';
                    gMb += '<div class="grid_head_menu"><ul>'
                    for(i = 0;i < defaults.colums.length; i++){
                        var text = defaults.colums[i].text ;
                        gMb += '<li><div><label class="chected">' + text + '</label></div></li>'
                    };
                    gMb += '</div>'
                };
                gMb += '</div>';
                $gBox.prepend(gMb);
                $('div.grid_head_menu > ul > li > div:not(.grid_head_menu)',$gBox)
                    .bind('mouseover',function(){$(this).addClass('hover')})
                    .bind('mouseout',function(){$(this).removeClass('hover')});
                $('.grid_head_menu .up', $gBox).parent().bind("click", {b:'up'} ,colSequence);
                $('.grid_head_menu .down', $gBox).parent().bind("click", {b:'down'} ,colSequence);
            };


            function dragCol(e){
                $('div.grid_head_menu',$gBox).hide();
                var $c = $("div.th_change",$gHeader);
                var mL = parseInt($gHeader.css("margin-left"));
                var oldW = $c.width();
                var index = $c.index();
                var colL = $c.position().left;
                var colW = $c.width();
                var $rL = $('<div class="ruler left"></div>');
                var $rR = $('<div class="ruler right"></div>');
                var x,y,p;
                if($('div.ruler',$gView).length > 0){
                    $rL = $('div.left',$gBox);
                    $rR = $('div.right',$gBox);
                    $rL.show().css("left",colL + mL + $gView.scrollLeft() - 1);
                    $rR.show().css("left",colL + colW + $gView.scrollLeft() + mL);
                }else{
                    $rL.css("left",colL - 1);
                    $rR.css("left",colL + colW);
                    $gView.append($rL,$rR);
                };
                _move = true;
                _x = $c.offset().left;
                p = _x - colL;
                $(document).mousemove(function(e){
                    if(_move){
                        x = e.pageX - p + mL + $gView.scrollLeft();//移动时根据鼠标位置计算控件左上角的绝对位置
                        if(x >= colL + mL + 20 + $gView.scrollLeft()){
                            $rR.css("left", x);
                        };
                    };
                    return false;
                }).mouseup(function(e){
                        _move=false;
                        $(document).unbind('mousemove mouseup');
                        $rL.hide();
                        $rR.hide();
                        if(e.pageX - _x > 20){
                            $c.width(e.pageX - _x);
                        }else{
                            $c.width(20);
                        };
                        oW = oldW;
                        nW = $c.width();
                        tI = index;
                        formatWidth(oldW, nW, index);
                        if(defaults.multiselect)
                            tI = index - 1;
                        if(defaults.number)
                            tI = index - 1;
                        colAttributes.widths.splice(tI - 1, 1, nW);
                        var sL = $gView.scrollLeft();
                        $gHeader.css('margin-left',-sL);
                        if($("table", $gView).width() < $gBox.width()) {

                            var cWidth = $c.width();
                            var newWidth = cWidth + $gBox.width() - $("table", $gView).width();
                            $c.width(newWidth);
                            var $f = $('table > tbody > tr:first > td', $gView);
                            $f.eq(index).width(newWidth);

                            if($(".rowEditorDiv", $gView).length > 0) {
                                var itIndex = $c.index();
                                $(".rowEditorDiv", $gView).find(".inner_div").each(function(i){
                                    if(i == itIndex) {
                                        if($(this).find("input").attr("id")) {
                                            $(this).width(newWidth).find("input").width(newWidth - 26);
                                        } else {
                                            $(this).width(newWidth).find("input").width(newWidth - 6);
                                        }
                                        var oldRowWidth = $(".rowEditorDiv", $gView).width();
                                        $(".rowEditorDiv", $gView).width(oldRowWidth + newWidth - oW);
                                    }
                                });
                            }

                        } else {
                            if($(".rowEditorDiv", $gView).length > 0) {
                                var itIndex = $c.index();
                                $(".rowEditorDiv", $gView).find(".inner_div").each(function(i){
                                    if(i == itIndex) {
                                        if($(this).find("input").attr("id")) {
                                            $(this).width(nW).find("input").width(nW - 26);
                                        } else {
                                            $(this).width(nW).find("input").width(nW - 6);
                                        }
                                        var oldRowWidth = $(".rowEditorDiv", $gView).width();
                                        $(".rowEditorDiv", $gView).width(oldRowWidth + nW - oW);
                                    }
                                });
                            }
                        }
                        //判断此时是否有rowEditor，如果有的话，那么相应的列的宽度变宽，总宽度也跟着改变
                        //$c就是改变的列的jQuery对象，我们要对$c所对应的input进行修改，然后改变整个div的宽度


                    });
                return false;
            };

            //改变td宽度
            function formatWidth(oldW,newW,i){
                var $f = $('table > tbody > tr:first > td', $gView);
                var tW = $('table',$gView).width();
                var z = tW + (newW - oldW);
                var n = tW - (oldW - newW) ;
                $f.eq(i).width(newW);
                if(newW > oldW){
                    $('table',$gView).width(z);
                    $gHeader.width(z);
                } else{
                    $('table',$gView).width(n);
                    if(n > $gBox.width())
                        $gHeader.width(n);
                };
                if($checkBox.attr("checked")){
                    $checkBox.attr("checked",false)
                };

            };
			
            //计算滚动条(一般是不会出现滚动条的)
            function scrollWidth(){
                if(end == "auto"){
                    var url = defaults.url;
                    var dataType = defaults.dataType;
                    $.ajax({
                        url: url,
                        dataType: dataType,
                        async:false,
                        success: function(data){
                            end = data.rows.length;
                            _data = data;
                            _stop = end;
                        }
                    });
                };
                if(defaults.height < end * 21){
                    return true;
                }else{
                    return false;
                };
            }
			
            //算header中td宽度
            function autoWidth(){
                var col = defaults.colums;
                var check = defaults.multiselect; //判断是否需要checkbox
                var num = defaults.number;  //是否需要序列号
                var allW = 0;
                if(defaults.width){
                    if(defaults.width == 'auto'){
                        allW = $gW;
                    }else{
                        allW = defaults.width;
                    };
                }else{
                    allW = $gW;
                };
                var j = 0,b = 0;//b的作用是记录共有表格共有多少列，包含序号列和checkbox，如果有的话
                var hasWidth = 0;
                for(i = 0; i < col.length; i++){
                    if(col[i].width){
                        hasWidth = parseInt(hasWidth) + parseInt(col[i].width);
                    }else{
                        j++; //如果没有给列定义宽度，那么j++
                    };
                    b++; //每循环一次b加1
                };
                if(check){
                    allW = allW - 22;
                    b++;
                };
                if(num){
                    allW = allW - 30;
                    b++;
                };
                if(defaults.height == 'auto'){
                    adaptive = Math.abs((allW - b - hasWidth) / j);
                }else{
                    if(scrollWidth()){
                        adaptive = Math.abs((allW - b - hasWidth - 17) / j);
                    }else{
                        adaptive = Math.abs((allW - b - hasWidth) / j);
                    };
                };

            }

            function reGreadPage() {
                var $gPage = $('<div class="grid_page"><div class="grid_page_box"></div></div>');
                var $pBox = $('div.grid_page_box',$gPage);
                var $p = $('<div class="prick"></div>');
                if(defaults.pages.goPage) {
                    var goTo = '<div class="grid_entry"><select name="grid_pages">';
                    for(i = 1; i < ps + 1; i++){
                        goTo = goTo + '<option>' + i + '</option>';
                    };
                    goTo = goTo + '</select></div>';
                    $pBox.append(goTo,'<div class="prick"></div>');
                    pSel = $('select', $pBox);
                    $($pBox).find("select").val(pB);
                    pSel.bind('change', {b:'select'}, jampPage);
                };
                if(defaults.pages.paging){
                    var $pagings = $('<span><b class="grid_page_fist"></b></span><span><b class="grid_page_prev"></b></span><div class="prick"></div><div class="grid_note"><input name="" type="text" class="page_nub" value="1" />页 共 '+ ps +' 页</div><div class="prick"></div><span><b class="grid_page_next"></b></span><span><b class="grid_page_last"></b></span><div class="prick"></div>');
                    pFirst = $('b.grid_page_fist',$pagings);
                    pPrev = $('b.grid_page_prev',$pagings);
                    pNub = $('input.page_nub',$pagings);
                    pNext = $('b.grid_page_next', $pagings);
                    pLast = $('b.grid_page_last', $pagings);
                    $pBox.append($pagings);
                    pNub.val(pB);
                    pFirst.bind("click",{b:'first'},pageFn);
                    pPrev.bind("click",{b:'prev'},pageFn);
                    pNext.bind("click",{b:'next'},pageFn);
                    pLast.bind("click",{b:'last'},pageFn);
                    pNub.bind('keyup',{b:'nub'},jampPage);
                };
                if(defaults.pages.renew){
                    var $refresh = $('<span title="刷新"><b class="grid_refresh"></b></span>');
                    $pBox.append($refresh,$p);
                    $refresh.bind('click',pageRefresh);
                };
                if(defaults.pages.info){
                    var $info = $('<div class="grid_info">每页显示 '+ defaults.rowNum +' 条数据 - 共 '+ total +' 条数据</div>');
                    $pBox.append($info);
                };
                $gBox.append($gPage);
            };



            return ({
                grid : {
					getDataTotalCount : function () {
						return _data.total;	
					},
                    delGridData : function(delUrl, dataType, theThis) {
                        defaults.callback.beforeDelData();
                        var trId = $(theThis).parents("tr").attr("id");
                        var idArray = trId.split("row_");
                        $.ajax({
                            url: delUrl,
                            dataType: "text",
                            type: "POST",
                            data:"dataId=" + idArray[1],
                            beforeSend : function(){

                            },
                            error: function (XMLHttpRequest, errorThrown) {
                                alert("删除失败！");
                            },
                            success: function(myData){
                                if(myData == "success") {
                                    if(!defaults.callback.afterDelData()){
                                        $(theThis).parents('tr').remove();
                                        if($(".grid_view").find("tr").length > 1) {
                                            var obj = {};
                                            obj.pageNo = pB;
                                            obj.rowNum = defaults.rowNum;
                                       //     obj.queryData = defaults.queryData;
                                            obj = $.extend(defaults.data,obj);
                                            $.ajax({
                                                type: defaults.type,
                                                url: defaults.url,
                                                dataType: "json",
                                                data : obj,
                                                success: function(loadData) {
                                                    $('table',$gView).remove();
													if(!defaults.afterRepage) {
														//前端分页
														var dataStart = (pB - 1)*defaults.rowNum + 1;
														var dataEnd = 0;
														if(loadData.rows.length < pB*defaults.rowNum) {
															dataEnd = loadData.rows.length;
															if(!defaults.afterRepage) {
																originGridData = loadData;	
															}
															initGrid(loadData, dataStart, dataEnd, colAttributes);		
														} else {
															dataEnd = pB * defaults.rowNum;
															if(!defaults.afterRepage) {
																originGridData = loadData;	
															}
															initGrid(loadData, dataStart, dataEnd, colAttributes);	
														}			
													} else {
														var dataEnd = (pB-1)*defaults.rowNum + loadData.rows.length;
														if(!defaults.afterRepage) {
															originGridData = loadData;	
														}
                                                    	initGrid(loadData, 0, dataEnd, colAttributes);		
													}
                                                    $(".grid_page").remove();
                                                    reGreadPage();
                                                },
                                                error:function(XMLHttpRequest, errorThrown) {
                                                    alert("数据加载出错！" + errorThrown);
                                                }
                                            });
                                        } else {
                                            //传入的页码是上一页
                                            var obj = {};
                                            obj.pageNo = pB - 1;
                                            obj.rowNum = defaults.rowNum;
                                      //      obj.queryData = defaults.queryData;
                                            obj = $.extend(defaults.data,obj);
                                            $.ajax({
                                                type: defaults.type,
                                                url: defaults.url,
                                                dataType: "json",
                                                data: obj,
                                                success: function(loadData) {
                                                    var on = "unclick";
                                                    if(pB != 1) {
                                                        pB = pB - 1;
                                                    }
                                                    str = (pB - 1) * defaults.rowNum;
                                                    ps = Math.ceil(loadData.total/defaults.rowNum);
                                                    total = loadData.total;
                                                    $(".grid_page").remove();
                                                    reGreadPage();
                                                    if((ps == pB && ps == 1) || ps == 0) {
                                                        pNext.removeClass(on);
                                                        pLast.removeClass(on);
                                                        pFirst.removeClass(on);
                                                        pPrev.removeClass(on);
                                                        pNext.addClass(on);
                                                        pLast.addClass(on);
                                                        pFirst.addClass(on);
                                                        pPrev.addClass(on);
                                                    } else if(ps == pB) {
                                                        pNext.removeClass(on);
                                                        pLast.removeClass(on);
                                                        pFirst.removeClass(on);
                                                        pPrev.removeClass(on);
                                                        pNext.addClass(on);
                                                        pLast.addClass(on);
                                                    } else if(pB == 1) {
                                                        pNext.removeClass(on);
                                                        pLast.removeClass(on);
                                                        pFirst.removeClass(on);
                                                        pPrev.removeClass(on);
                                                        pFirst.addClass(on);
                                                        pPrev.addClass(on);
                                                    }
                                                    $('table',$gView).remove();
													if(defaults.frontJumpPage) {
														var dataStart = (pB - 1)*defaults.rowNum + 1;
														var dataEnd = pB * defaults.rowNum;
														if(!defaults.afterRepage) {
															originGridData = loadData;	
														}
														initGrid(loadData, dataStart, dataEnd, colAttributes);		
													} else {
														if(!defaults.afterRepage) {
															originGridData = loadData;	
														}
														var dataEnd = (pB-2)*defaults.rowNum + loadData.rows.length;
                                                    	initGrid(loadData, 0, dataEnd, colAttributes);	
													}
                                                    
                                                },
                                                error:function(XMLHttpRequest, errorThrown) {
                                                    alert("数据加载出错！" + errorThrown);
                                                }
                                            });

                                        }

                                    }
                                }
                            }
                        });
                    },
                    clickId : function(){
                        return trId;
                    },
                    selectTrs : function(obj){
                        var objs = obj.split(",");
                        for(var i=0;i < objs.length;i++){
                            var id = objs[i];
                            var trId = $('tr#'+id,g);
                            if(trId.hasClass("select")){
                                return;
                            }else{
                                trId.trigger('click');
                            };
                        }
                    },
                    reloadGrid : function() {
                        var newObj = {};
                        newObj.pageNo = pB;
                        newObj.rowNum = defaults.rowNum;
                    //    newObj.queryData = defaults.queryData;
                        newObj = $.extend(defaults.data,newObj);
                        $.ajax({
                            url: defaults.url,
                            dataType: "json",
                            type : defaults.type,
                            data : newObj,
                            error: function (XMLHttpRequest, errorThrown) {
                                alert("数据加载出错！" + errorThrown);
                            },
                            success: function(myData){
                                _data = myData;
                                if(_data.total < pB * defaults.rowNum) {
                                    end = _data.total;
                                } else {
                                    end = pB * defaults.rowNum;
                                }
                                cancelCheck();
                                $('table', $gView).remove();
                                hideCol();
                                $('.grid_page').remove();
                                reGreadPage();

                            }
                        });

                    },
                    checkAll : function(gridId) {
                        $("#"+gridId).find(".chex").attr("checked", "checked");
                        $("#"+gridId).find("table").find("tr").each(function(){
                            if(!$(this).hasClass("select")) {
                                $(this).trigger("click");
                            }
                        });
                    },
                    cancelAllChecked : function(gridId){
                        $("#"+gridId).find(".chex").attr("checked", false);
                        $("#"+gridId).find("table").find("tr").each(function(){
                            if($(this).hasClass("select")) {
                                $(this).trigger("click");
                            }
                        });
                    },
                    delMulGridData : function(gridId, delUrl,successMsg,errorMsg){
                        //首先得到所有checkbox的id，拼装成一个id的字符串
                        //然后往后台传值，他给我返回新的数据
                        var allId = "";
                        var temGrid = $("#"+gridId);
                        //下面这个变量用来记录一共删除多少条
                        var itemNum = 0;
                        $(temGrid).find("table").eq(0).find("input[type='checkbox']").each(function(){
                            if($(this).attr("checked")) {
                                itemNum++;
                                allId = allId + $(this).parents("tr").attr("id") + ",";
                            }
                        });
                        if(allId != ""){
                            allId = allId.substring(0, allId.length-1);
                        }

                        $.ajax({
                            url: delUrl,
                            dataType: "text",
                            type: "GET",
                            data:"allId=" + allId,
                            beforeSend : function(){
                            },
                            error: function (XMLHttpRequest, errorThrown) {
                                if(errorMeagess){
                                    msgAlert("Alert", errorMsg);
                                }else{
                                    msgAlert("Alert", "删除失败！");
                                };
                            },
                            success: function(myData){
//我得想办法能让他传一个删除成功的提示
                                if(myData == "success") {
                                    if(successMsg){
                                        msgSuccess("Success", successMsg);
                                        return;
                                    }else{
                                        msgSuccess("Success", "删除成功！");
                                    }
                                    if(!defaults.callback.afterDelData()){
                                        if($(".grid_view").find("tr").length > itemNum + 1) {
                                            var obj = {};
                                            obj.pageNo = pB;
                                            obj.rowNum = defaults.rowNum;
                                        //    obj.queryData = defaults.queryData;
                                            obj = $.extend(defaults.data,obj);
                                            $.ajax({
                                                type: defaults.type,
                                                url: defaults.url,
                                                dataType: "json",
                                                data: obj,
                                                success: function(loadData) {
                                                    $('table',$gView).remove();
                                                    var dataEnd = (pB-1)*defaults.rowNum + loadData.rows.length;
                                                    if(defaults.frontJumpPage) {
														//前端分页
														var dataStart = (pB - 1)*defaults.rowNum + 1;
														var dataEnd = 0;
														if(loadData.rows.length < pB*defaults.rowNum) {
															dataEnd = loadData.rows.length;
															initGrid(loadData, dataStart, dataEnd, colAttributes);		
														} else {
															dataEnd = pB * defaults.rowNum;
															initGrid(loadData, dataStart, dataEnd, colAttributes);	
														}			
													} else {
														var dataEnd = (pB-1)*defaults.rowNum + loadData.rows.length;
                                                    	initGrid(loadData, 0, dataEnd, colAttributes);		
													}
                                                    $(".grid_page").remove();
                                                    reGreadPage();
                                                },
                                                error:function(XMLHttpRequest, errorThrown) {
                                                    alert("数据加载出错！" + errorThrown);
                                                }
                                            });
                                        } else {
                                            //传入的页码是上一页
                                            var obj = {};
                                            if(pB - 1 == 0) {
                                                obj.pageNo = pB;
                                            } else {
                                                obj.pageNo = pB - 1;
                                            }

                                            obj.rowNum = defaults.rowNum;
                                     //       obj.queryData = defaults.queryData;
                                            obj = $.extend(defaults.data,obj);
                                            $.ajax({
                                                type: defaults.type,
                                                url: defaults.url,
                                                dataType: "json",
                                                data:obj,
                                                success: function(loadData) {
                                                    var on = "unclick";
                                                    if(pB != 1) {
                                                        pB = pB - 1;
                                                    }
                                                    str = (pB - 1) * defaults.rowNum;
                                                    ps = Math.ceil(loadData.total/defaults.rowNum);
                                                    total = loadData.total;
                                                    $(".grid_page").remove();
                                                    reGreadPage();
                                                    if((ps == pB && ps == 1) || ps == 0) {
                                                        pNext.removeClass(on);
                                                        pLast.removeClass(on);
                                                        pFirst.removeClass(on);
                                                        pPrev.removeClass(on);
                                                        pNext.addClass(on);
                                                        pLast.addClass(on);
                                                        pFirst.addClass(on);
                                                        pPrev.addClass(on);
                                                    } else if(ps == pB) {
                                                        pNext.removeClass(on);
                                                        pLast.removeClass(on);
                                                        pFirst.removeClass(on);
                                                        pPrev.removeClass(on);
                                                        pNext.addClass(on);
                                                        pLast.addClass(on);
                                                    } else if(pB == 1) {
                                                        pNext.removeClass(on);
                                                        pLast.removeClass(on);
                                                        pFirst.removeClass(on);
                                                        pPrev.removeClass(on);
                                                        pFirst.addClass(on);
                                                        pPrev.addClass(on);
                                                    }
                                                    $('table',$gView).remove();
													if(defaults.frontJumpPage) {
														var dataStart = (pB-1)*defaults.rowNum + 1;
														var dataEnd = pB*defaults.rowNum;
														if(!defaults.afterRepage) {
															originGridData = loadData;	
														}
														initGrid(loadData, dataStart, dataEnd, colAttributes)
													} else {
														var dataEnd = (pB-2)*defaults.rowNum + loadData.rows.length;
														if(!defaults.afterRepage) {
															originGridData = loadData;	
														}
                                                    	initGrid(loadData, 0, dataEnd, colAttributes);	
													}
                                                    
                                                },
                                                error:function(XMLHttpRequest, errorThrown) {
                                                    alert("数据加载出错！" + errorThrown);
                                                }
                                            });

                                        }

                                    }
                                }
                            }
                        });

                    }

                }
            });





        }
    });
})(jQuery);