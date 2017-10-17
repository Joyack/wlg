$(function () {
    //将点击到的按钮的链接跳转到<iframe>中
    let linkArr = $(".options a");
    for (let i = 0; i < linkArr.length; i++) {
        $(linkArr[i]).click(function (e) {
            e.preventDefault();
            let link = $(this).attr('href');
            $('iframe').attr('src', link);
        })
    }

    //对接下拉菜单接口
    $.get(
        HEADER + 'resources/queryAllResourcesByRole.do',
        function (data) {
            let html = template('navTem', data);
            $('#nav-ul').html(html);

            //导航栏下拉效果
            $('.level-one').mouseover(function () {
                $(this).next('ul').css('display', 'block');
            });
            $('.nav>ul>li').mouseleave(function () {
                $(this).children('ul').css('display', 'none');
            });

            //将点击到的按钮的链接跳转到<iframe>中
            clickIntoIframe();
        }
    );

    $("#exit").click(function () {
        $("#logoutform").submit();
    });

});

function clickIntoIframe() {
    let linkArr = $("header a");
    let link;
    for (let i = 0; i < linkArr.length; i++) {
        $(linkArr[i]).click(function Link(e) {
            e.preventDefault();
            link = $(this).attr('href');
            let ifr = $('iframe');
            ifr.attr('src', link);

            let url = window.location.href.split('#')[0];
            window.location.href = url + '#' + ensecret(link);
        });
    }
}

document.addEventListener('DOMContentLoaded', function () {
    let hash = location.hash;
    if (hash) {
        let link = desecret(hash.slice(1));
        $('iframe').attr('src', link);
    }
});

//加密
function ensecret(str) {
    let c = String.fromCharCode(str.charCodeAt(0) + str.length);

    for (let i = 1; i < str.length; i++) {
        c += String.fromCharCode(str.charCodeAt(i) + str.charCodeAt(i - 1));
    }

    return encodeURIComponent(c);
}

//解密
function desecret(str) {
    str = decodeURIComponent(str);
    let c = String.fromCharCode(str.charCodeAt(0) - str.length);

    for (let i = 1; i < str.length; i++) {
        c += String.fromCharCode(str.charCodeAt(i) - c.charCodeAt(i - 1));
    }
    return c;
}

// window.onhashchange=function () {
//     let hash=location.hash;
//     hash=hash.slice(1);
//     $('iframe').attr('src',hash);
// };

