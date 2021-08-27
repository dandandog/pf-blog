$(function () {
    var url = window.location.pathname;
    var value = url.split("/");

    value.forEach((val, index) => {
        if (index > 1) {
            var name = "#{msg['blog']['" + val + "']"
            var content = `<li>${name}</li><li>/</li>`;
            $('#viewname').append(content);
        }
    })
})