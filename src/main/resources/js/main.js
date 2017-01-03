function searchGBapi() {
    var gBooksApi = "https://www.googleapis.com/books/v1/volumes?q=";
    var gbApiKey = "AIzaSyCj3Xyoon308IJgDP3sNQsYjwvWcaHFmKw";

    var coverUrl = "";
    var title = "";
    var author = "";
    var description = "";
    var year = "";

    var book = '<li class="book"> <div class="cover"> <img src="'+coverUrl+'"> </div> ' +
        '<div class="info"> ' +
            '<h2 class="title">'+title+'</h2> ' +
            '<h3 class="author">'+author+' <span>'+year+'</span></h3> ' +
            '<p class="description">'+description+'</p> ' +
            '<form action="/add-book" method="post"> ' +
                '<input type="hidden" name="title" value="'+title+'"/> ' +
                '<input type="hidden" name="author" value="'+author+'"/> ' +
                '<input type="hidden" name="description" value="'+description+'"/> ' +
                '<input type="hidden" name="cover" value="'+coverUrl+'"/> ' +
                '<input type="hidden" name="year" value="'+year+'"/> ' +
                '<select name="status"> ' +
                    '<option default disabled>Select Status</option> ' +
                    '<option value="want">Want To Read</option> ' +
                    '<option value="current">Currently Reading</option> ' +
                    '<option value="read">Read</option> ' +
                '</select> ' +
                '<button type="submit">Add</button> ' +
            '</form> </div> </li>';

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
        console.log(url);

        $.getJSON(url, function (data) {
            var rs = data["items"];
            var searchList = $("#searchList");
            console.log(rs);
            rs.forEach(function (result) {
                title = result.volumeInfo.title;
                author = result.volumeInfo.authors[0];
                coverUrl = result.volumeInfo.imageLinks.smallThumbnail;
                year = result.volumeInfo.publishedDate.slice(0,4);
                if(result.searchInfo){
                    description = result.searchInfo.textSnippet;
                }
                var book = '<li class="book"> <div class="cover"> <img src="'+coverUrl+'"> </div> ' +
                    '<div class="info"> ' +
                    '<h2 class="title">'+title+'</h2> ' +
                    '<h3 class="author">'+author+' <span>'+year+'</span></h3> ' +
                    '<p class="description">'+description+'</p> ' +
                    '<form action="/add-book" method="post"> ' +
                    '<input type="hidden" name="title" value="'+title+'"/> ' +
                    '<input type="hidden" name="author" value="'+author+'"/> ' +
                    '<input type="hidden" name="description" value="'+description+'"/> ' +
                    '<input type="hidden" name="cover" value="'+coverUrl+'"/> ' +
                    '<input type="hidden" name="year" value="'+year+'"/> ' +
                    '<select name="status"> ' +
                    '<option default disabled>Select Status</option> ' +
                    '<option value="want">Want To Read</option> ' +
                    '<option value="current">Currently Reading</option> ' +
                    '<option value="read">Read</option> ' +
                    '</select> ' +
                    '<button type="submit">Add</button> ' +
                    '</form> </div> </li>';
                $("#searchList").append(book);
            })
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