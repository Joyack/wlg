$(function () {
    showTime();
    $('.d_arrow').click(function () {
        showTime();
    });
});

//选择日期
function showTime() {
    $(".time-select").datetimepicker({
        language: 'en',
        format: "yyyy-mm-dd",
        minView: "month",
        todayBtn: 1, //点击显示今天时间
        autoclose: 1, //选择日期后关闭
        todayHighlight: 1, //当前天数高亮
        startView: 3, //开始的显示年、还是月、或日、时、分、秒
        forceParse: 0, //默认是那一天
        minuteStep: 1,
        startDate: "2013-09-10"
    });
}

