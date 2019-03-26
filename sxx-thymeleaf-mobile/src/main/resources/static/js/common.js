jQuery.fn.isChildAndSelfOf = function(b){ return (this.closest(b).length > 0); }
$(function() {
    $('body').css({visibility:'visible'})
    $('.menu-btn').on('click',function () {
        $('.menu-container').css({right:'0',})
        $('body,html').css({overflow:'hidden'})
    })
    $(document).mouseup(
        function(e){
            var ev = e || window.event;
            var target = ev.target || ev.srcElement;
            if(!$(target).isChildAndSelfOf('.menu-container')){
                $('.menu-container').css({right:'-3rem'})
                $('body,html').css({overflow:''})
            }
        }
    )

    $("#aaaaa").on("click",function(){
        alert("aaa");
        $.get({
            url : "localhost:31000/mobile/getMobileNewsList",
            success : function (data) {
                if (data.success){

                } else{}
            }
        })
    });
});