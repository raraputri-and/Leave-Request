function addCsrfToken() {
    let token = $("meta[name='_csrf']").attr("content"); // TOKEN
    let header = $("meta[name='_csrf_header']").attr("content"); // HEADER NAME
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
};