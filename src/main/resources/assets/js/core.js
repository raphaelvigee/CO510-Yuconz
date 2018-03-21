$(function () {
    $(".feed .record").click(function(e) {
        if ($(e.target).hasClass("link")) {
            e.preventDefaults();
        }
        $(e.currentTarget).find(".summary").toggle();
    });

    var tx = document.getElementsByTagName('textarea');
    for (var i = 0; i < tx.length; i++) {
        tx[i].setAttribute('style', 'height:' + (tx[i].scrollHeight) + 'px;overflow-y:hidden;');
        tx[i].addEventListener("input", OnInput, false);
    }

    function OnInput() {
        this.style.height = 'auto';
        this.style.height = (this.scrollHeight) + 'px';
    }
});

