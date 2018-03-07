$(function () {
    $(".feed .record").click(function(e) {
        if ($(e.target).hasClass("link")) {
            e.preventDefaults();
        }
        $(e.currentTarget).find(".summary").toggle();
    });
});
