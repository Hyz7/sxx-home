$(function () {
    $(".invite-list .title").on("click",function () {
        $(this).closest('.invite-list').toggleClass('avtiveH').stop().find('i').toggleClass('active')

    })
})