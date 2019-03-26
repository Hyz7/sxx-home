$(function () {
    $('.tabChange li').on("click",function(){
        $(this).addClass("active").siblings().removeClass('active')
        $('.news-content').find('.newsBox').eq($(this).index()).css({display:'block'}).siblings().css({display: "none"})

    });
    $("#info").on('click',function () {
        $.get({

        });
    });


});
