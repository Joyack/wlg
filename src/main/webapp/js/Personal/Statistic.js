$(function () {
    start();

    let today=new Date();
    let firstYear=today.getFullYear();
    $('.first').attr('value',firstYear).text(firstYear);

    //切换下方视图并渲染
    let showArr = $('.navbar>span');
    for (let i = 0; i < showArr.length; i++) {
        //点击切换视图
        $(showArr[i]).click(function () {
            let showClass = '.' + $(this).prop('id') + '-show';
            $(showClass).addClass('show')
                .siblings().removeClass('show');
            /**********销售对接**********/
            if (showClass === '.sales-show') {
                intoSales();
            }
            /**********采购对接**********/
            else if (showClass === '.purchase-show') {
                intoPurchase();
            }
            /**********库存对接**********/
            else if (showClass === '.stock-show') {
                intoStock();
            }
            else {
                start();
            }
        });
    }
});


/*封装函数区*/

// 使其他函数只运行一次
function runOnce(fn, context) {
    let result;
    return function () {
        if (fn) {
            result = fn.apply(context || this, arguments);
            fn = null;
        }
        return result;
    }
}

//年份对接
function yearAjax(id) {
    $.ajax({
        url: HEADER + "report/getDateList.do?flag=2",
        type: "GET",
        async: false,
        success: function (data) {
            let jsonarr = data.result;
            let html = "";
            for (let i = 0; i < jsonarr.length; i++) {
                html += "<option value='" + jsonarr[i].datetime + "'>" + jsonarr[i].datetime + "</option>";
            }
            $("#" + id).html(html);
        }
    });
}

/**************************出入库***********************************/
//打开页面后的初始渲染
function start() {
    yearAjax('out-in-year');
    let outInYear = $('#out-in-year');
    let outInStatus = $('#out-in-status');
    let selectYear = outInYear.val();
    let selectSopertype = outInStatus.val();
    let url = HEADER + 'dreport/outInStocknum.do';
    outInAjax(url, selectYear, selectSopertype);
    outInCheckbox('out-in-ul');

    /**********出入库对接**********/

    let outInArr = $('#outInSelect').find('select');
    for (let i = 0; i < outInArr.length; i++) {
        $(outInArr[i]).change(function () {
            if (this.id === 'out-in-year') {
                let selectYear = $(this).val();
                let selectSopertype = $('#out-in-status').val();
                outInAjax(url, selectYear, selectSopertype);
            } else {
                let selectSopertype = $(this).val();
                let selectYear = $('#out-in-year').val();
                outInAjax(url, selectYear, selectSopertype);
            }
        });
    }
}

//出入库渲染函数
function outInAjax(url, year, sopertype, ids) {
    $.get(
        url,
        {
            year: year, sopertype: sopertype, ids: ids
        },
        function statisSuccess(statis) {
            //eCharts图表
            let myChart = echarts.init(document.getElementById('diagram'));
            let option = {
                tooltip: {},
                xAxis: {
                    data: ["一月", "二月", "三月", "四月", "五月", "六月"
                        , "七月", "八月", "九月", "十月", "十一月", "十二月"
                    ]
                },
                yAxis: {},
                series: [{
                    type: 'bar',
                    data: [statis.result[0].stocknum, statis.result[1].stocknum, statis.result[2].stocknum,
                        statis.result[3].stocknum, statis.result[4].stocknum, statis.result[5].stocknum,
                        statis.result[6].stocknum, statis.result[7].stocknum, statis.result[8].stocknum,
                        statis.result[9].stocknum, statis.result[10].stocknum, statis.result[11].stocknum
                    ]
                }]
            };
            myChart.setOption(option);
        }
    );
}

//出入库下拉复选框
function outInCheckbox(id) {
    $.get(
        HEADER + 'goods/getAllGoodsName.do',
        function (data) {
            let html = template('goodList', data);
            $('#' + id).html(html);
            //全选,全不选
            $('.selectAll').change(function () {
                $('.goods input').not('.selectAll').prop('checked', this.checked);
            });
            //加号减号
            $('.plus').click(function () {
                $(this).toggleClass('glyphicon-minus')
                    .siblings('li').toggleClass('show');
            });

            //获取自定义属性
            let checkboxArr = $('.statisCheckbox');
            let checkedArr = [];
            for (let i = 0; i < checkboxArr.length; i++) {
                $(checkboxArr[i]).change(function idsFn() {
                    //保存好上一次循环已经被选中的复选框
                    let doms = $('#' + id).find("input:checked");
                    checkedArr = [];
                    for (let j = 0; j < doms.length; j++) {
                        let dataId = $(doms[j]).attr('data-id');
                        if (dataId) {
                            checkedArr.push(dataId);
                        }
                    }
                    let ids = checkedArr.toString();



                    function start2(ids) {
                        let outInYear = $('#out-in-year');
                        let outInStatus = $('#out-in-status');
                        let selectYear = outInYear.val();
                        let selectSopertype = outInStatus.val();
                        let url = HEADER + 'dreport/outInStocknum.do';
                        outInAjax(url, selectYear, selectSopertype, ids);

                        let outInArr = $('#outInSelect').find('select');
                        for (let i = 0; i < outInArr.length; i++) {
                            $(outInArr[i]).change(function () {
                                if (this.id === 'out-in-year') {
                                    let selectYear = $(this).val();
                                    let selectSopertype = $('#out-in-status').val();
                                    outInAjax(url, selectYear, selectSopertype, ids);
                                } else {
                                    let selectSopertype = $(this).val();
                                    let selectYear = $('#out-in-year').val();
                                    outInAjax(url, selectYear, selectSopertype,ids);
                                }
                            });
                        }
                    }

                    start2(ids);
                });
            }
        }
    );
}


/**************************销售***********************************/
//进入销售页面后渲染一次
function intoSales() {
    yearAjax('sales-year');
    let salesYear = $('#sales-year');
    let selectYear = salesYear.val();
    let url = HEADER + 'dreport/salesStocknum.do';
    salesAjax(url, selectYear);
    salesCheckbox('sales-ul');

    salesYear.change(function () {
        let selectYear = $(this).val();
        salesAjax(url, selectYear);
    });
}

//销售渲染函数
function salesAjax(url, year, ids) {
    $.get(
        url,
        {year: year, ids: ids},
        function statisSuccess(statis) {
            //eCharts图表
            let myChart = echarts.init(document.getElementById('sales-diagram'));
            let option = {
                tooltip: {},
                xAxis: {
                    data: ["一月", "二月", "三月", "四月", "五月", "六月"
                        , "七月", "八月", "九月", "十月", "十一月", "十二月"
                    ]
                },
                yAxis: {},
                series: [{
                    type: 'bar',
                    data: [statis.result[0].num, statis.result[1].num, statis.result[2].num,
                        statis.result[3].num, statis.result[4].num, statis.result[5].num,
                        statis.result[6].num, statis.result[7].num, statis.result[8].num,
                        statis.result[9].num, statis.result[10].num, statis.result[11].num
                    ]
                }]
            };
            myChart.setOption(option);
        }
    );
}

//销售下拉复选框
function salesCheckbox(id) {
    $.get(
        HEADER + 'goods/getAllGoodsName.do',
        function (data) {
            let html = template('goodList', data);
            $('#' + id).html(html);
            //全选,全不选
            $('.selectAll').change(function () {
                $('.goods input').not('.selectAll').prop('checked', this.checked);
            });
            //加号减号
            $('.plus').click(function () {
                $(this).toggleClass('glyphicon-minus')
                    .siblings('li').toggleClass('show');
            });

            //获取自定义属性
            let checkboxArr = $('.statisCheckbox');
            let checkedArr = [];
            for (let i = 0; i < checkboxArr.length; i++) {
                $(checkboxArr[i]).change(function idsFn() {
                    //保存好上一次循环已经被选中的复选框
                    let doms = $('#' + id).find("input:checked");
                    checkedArr = [];
                    for (let j = 0; j < doms.length; j++) {
                        let dataId = $(doms[j]).attr('data-id');
                        if (dataId) {
                            checkedArr.push(dataId);
                        }
                    }
                    let ids = checkedArr.toString();

                    function intoSales2(ids) {
                        let salesYear = $('#sales-year');
                        let url = HEADER + 'dreport/salesStocknum.do';
                        let selectYear = salesYear.val();
                        salesAjax(url, selectYear, ids);

                        salesYear.change(function () {
                            let selectYear = $(this).val();
                            salesAjax(url, selectYear, ids);
                        });
                    }

                    intoSales2(ids);
                });
            }
        }
    );
}


/**************************采购***********************************/
//采购
function purchaseAjax(url, year, ids) {
    $.get(
        url,
        {year: year, ids: ids},
        function statisSuccess(statis) {
            //eCharts图表
            let myChart = echarts.init(document.getElementById('purchase-diagram'));
            let option = {
                tooltip: {},
                xAxis: {
                    data: ["一月", "二月", "三月", "四月", "五月", "六月"
                        , "七月", "八月", "九月", "十月", "十一月", "十二月"
                    ]
                },
                yAxis: {},
                series: [{
                    type: 'bar',
                    data: [statis.result[0].num, statis.result[1].num, statis.result[2].num,
                        statis.result[3].num, statis.result[4].num, statis.result[5].num,
                        statis.result[6].num, statis.result[7].num, statis.result[8].num,
                        statis.result[9].num, statis.result[10].num, statis.result[11].num
                    ]
                }]
            };
            myChart.setOption(option);
        }
    );
}

//进入采购页面后渲染一次
let intoPurchase = function () {
    yearAjax('purchase-year');
    let purchaseYear = $('#purchase-year');
    let url = HEADER + 'dreport/purchaseStocknum.do';
    let selectYear = purchaseYear.val();
    purchaseAjax(url, selectYear);
    purchaseCheckbox('purchase-ul');

    purchaseYear.change(function () {
        let selectYear = $(this).val();
        purchaseAjax(url, selectYear)
    });
};

//采购下拉复选框
function purchaseCheckbox(id) {
    $.get(
        HEADER + 'goods/getAllGoodsName.do',
        function (data) {
            let html = template('goodList', data);
            $('#' + id).html(html);
            //全选,全不选
            $('.selectAll').change(function () {
                $('.goods input').not('.selectAll').prop('checked', this.checked);
            });
            //加号减号
            $('.plus').click(function () {
                $(this).toggleClass('glyphicon-minus')
                    .siblings('li').toggleClass('show');
            });

            //获取自定义属性
            let checkboxArr = $('.statisCheckbox');
            let checkedArr = [];
            for (let i = 0; i < checkboxArr.length; i++) {
                $(checkboxArr[i]).change(function idsFn() {
                    //保存好上一次循环已经被选中的复选框
                    let doms = $('#' + id).find("input:checked");
                    checkedArr = [];
                    for (let j = 0; j < doms.length; j++) {
                        let dataId = $(doms[j]).attr('data-id');
                        if (dataId) {
                            checkedArr.push(dataId);
                        }
                    }
                    let ids = checkedArr.toString();

                    function intoPurchase2(ids) {
                        let purchaseYear = $('#purchase-year');
                        let url = HEADER + 'dreport/purchaseStocknum.do';
                        let selectYear = purchaseYear.val();
                        purchaseAjax(url, selectYear, ids);

                        purchaseYear.change(function () {
                            let selectYear = $(this).val();
                            purchaseAjax(url, selectYear, ids)
                        });
                    }

                    intoPurchase2(ids);
                });
            }
        }
    );
}


/**************************库存***********************************/
//库存
function stockAjax(url, year, ids) {
    $.get(
        url,
        {year: year, ids: ids},
        function statisSuccess(statis) {
            //eCharts图表
            let myChart = echarts.init(document.getElementById('stock-diagram'));
            let option = {
                tooltip: {},
                xAxis: {
                    data: ["一月", "二月", "三月", "四月", "五月", "六月"
                        , "七月", "八月", "九月", "十月", "十一月", "十二月"
                    ]
                },
                yAxis: {},
                series: [{
                    type: 'bar',
                    data: [statis.result[0].stocknum, statis.result[1].stocknum, statis.result[2].stocknum,
                        statis.result[3].stocknum, statis.result[4].stocknum, statis.result[5].stocknum,
                        statis.result[6].stocknum, statis.result[7].stocknum, statis.result[8].stocknum,
                        statis.result[9].stocknum, statis.result[10].stocknum, statis.result[11].stocknum
                    ]
                }]
            };
            myChart.setOption(option);
        }
    );
}

//进入库存页面后渲染一次
let intoStock = function () {
    yearAjax('stock-year');
    let stockYear = $('#stock-year');
    let url = HEADER + 'dreport/stocknum.do';
    let selectYear = stockYear.val();
    stockAjax(url, selectYear);
    stockCheckbox('goods-ul');

    stockYear.change(function () {
        let selectYear = $(this).val();
        stockAjax(url, selectYear);
    });
};

//库存下拉复选框
function stockCheckbox(id) {
    $.get(
        HEADER + 'goods/getAllGoodsName.do',
        function (data) {
            let html = template('goodList', data);
            $('#' + id).html(html);
            //全选,全不选
            $('.selectAll').change(function () {
                $('.goods input').not('.selectAll').prop('checked', this.checked);
            });
            //加号减号
            $('.plus').click(function () {
                $(this).toggleClass('glyphicon-minus')
                    .siblings('li').toggleClass('show');
            });

            //获取自定义属性
            let checkboxArr = $('.statisCheckbox');
            let checkedArr = [];
            for (let i = 0; i < checkboxArr.length; i++) {
                $(checkboxArr[i]).change(function idsFn() {
                    //保存好上一次循环已经被选中的复选框
                    let doms = $('#' + id).find("input:checked");
                    checkedArr = [];
                    for (let j = 0; j < doms.length; j++) {
                        let dataId = $(doms[j]).attr('data-id');
                        if (dataId) {
                            checkedArr.push(dataId);
                        }
                    }
                    let ids = checkedArr.toString();

                    function intoStock2(ids) {
                        let stockYear = $('#stock-year');
                        let url = HEADER + 'dreport/stocknum.do';
                        let selectYear = stockYear.val();
                        stockAjax(url, selectYear, ids);

                        stockYear.change(function () {
                            let selectYear = $(this).val();
                            stockAjax(url, selectYear, ids);
                        });
                    }

                    intoStock2(ids);
                });
            }
        }
    );
}













