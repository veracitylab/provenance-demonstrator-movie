$(document).ready(function () {
    let ratingForm = $('#rating-form');
    let movieDetailModal = $('#movieDetailModal');

    // Event listener to clear ratings when modal is closed
    movieDetailModal.on('hide.bs.modal', function() {
        $('[name="rating"]').prop('checked', false);
        movieDetailModal.removeProp('movieId');
    })

    // When ratings change, fire the rate movie event
    ratingForm.on('change', '[name="rating"]', function() {
        let rating = $('[name="rating"]:checked').val();
        let movieId = movieDetailModal.prop('movieId');
        rateMovie(movieId, rating);
    });
});

/**
 * Perform AJAX put to rate a movie
 * @param movieId
 * @param rating
 */
function rateMovie(movieId, rating) {
    $.ajax({
        type: "PUT",
        url: 'movie/rate',
        contentType: 'application/json',
        data: {
            "movieId": movieId,
            "rating": rating
        },
        error: console.error
    });
}

/**
 * Query movie details from movie ID, assume response is using JSON
 * @param movieId
 */
function queryMovieDetail(movieId) {
    // Set the ID of the object for later use
    $('#movieDetailModal').prop('movieId', movieId);

    $.ajax({
        type: "GET",
        url: `/movie/${movieId}`,
        success: displayMovieDetail,
        error: console.error
    })
}

/**
 * Coverts JSON data into items that can be displayed
 * @param movieData
 */
function displayMovieDetail(movieData) {
    let title = movieData['title'];
    let release = movieData['releaseYear'];
    let imgUrl = movieData['posterUrl'];
    let rating = movieData['populatedRating'];

    $('#movieDetailTitle').text(title);
    $('#movieDetailRelease').text(`Released: ${release.split("-")[0]}`);
    $('#movieDetailImage').prop('src', (imgUrl != null) ? imgUrl : "/img/image-not-found.png");
    $("#movieDetailModal").modal('show');
}

