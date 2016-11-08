/**
 * Created by root on 11/7/16.
 */
$(document).ready(function($) {
    $(".table-row").click(function() {
        window.document.location = $(this).data("href");
    });
});