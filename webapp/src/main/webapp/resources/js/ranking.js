$(document).ready(function() {
// sort function from http://stackoverflow.com/questions/7558182/sort-a-table-fast-by-its-first-column-with-javascript-or-jquery
    var $tbody = $('table tbody');
    $tbody.find('tr').sort(function (a, b) {
        var tda = $(a).find('td:eq(' + 2 + ')').text(); // Use your wished column index
        var tdb = $(b).find('td:eq(' + 2 + ')').text(); // Use your wished column index
        var a = parseInt(tda);
        var b = parseInt(tdb);

        // if a < b return 1
        return a > b ? -1
               // else if a > b return -1
               : a < b ? 1
               // else they are equal - return 0    
               : 0;
    }).appendTo($tbody);
    $('table tr').each(function(i,row) {
        $(".pos",row).text(i);
    });
});