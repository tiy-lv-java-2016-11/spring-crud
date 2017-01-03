function searchGBapi() {
    var gBooksApi = "https://www.googleapis.com/books/v1/volumes?q=";
    var gbApiKey = "AIzaSyCj3Xyoon308IJgDP3sNQsYjwvWcaHFmKw";

    $("#searchForm").submit(function (event) {
        event.preventDefault();
        var inputs = $(this).serializeArray();
        var searchTerm = inputs[0].value;
        var searchType = inputs[1].value;
        console.log(searchTerm);
        console.log(searchType);
        var q = searchType+":"+searchTerm;
        var startIndex = 0;
        var maxResults = 10;
        var url = gBooksApi+q+"&startIndex"+startIndex+"&maxResults"+maxResults+"&key"+gbApiKey;

        $.getJSON(url, function (data) {
            var rs = data["items"];
            console.log(rs);
            // rs.each(function (result) {
            //     $("#results").appendChild(
            //         "<li><form action='/add-book' method='post'>" +
            //                 "<input>")
            // })
        })

    });

    // ??? Save for Mike ???
    // $("#searchForm").submit(function (event) {
    //     event.preventDefault();
    //     var inputs = $(this).serializeArray();
    //     var searchTerm = inputs[0].value;
    //     var searchType = inputs[1].value;
    //     console.log(searchTerm);
    //     console.log(searchType);
    //
    //     $.getJSON(gBooksApi, {
    //         q: searchType+":"+searchTerm,
    //         startIndex: 0,
    //         maxResults: 10,
    //         key: gbApiKey,
    //     }).done(function (data) {
    //         var results = data["items"];
    //         console.log(results);
    //     })
    //
    // })
}

$(document).ready(function () {
    searchGBapi();
});