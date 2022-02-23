$(document).ready(function () {
    let ratingForm = $('#rating-form');
    let movieDetailModal = $('#movieDetailModal');

    // Event listener to clear ratings when modal is closed
    movieDetailModal.on('hide.bs.modal', function() {
        $('[name="rating"]').prop('checked', false);
        $('#provenanceRow').prop('hidden', true);
        $('#provenanceBtn').removeProp('provId');
        $('#provenanceDataContainer').prop('hidden', true);
        movieDetailModal.removeProp('movieId');
    })

    // When ratings change, fire the rate movie event
    ratingForm.on('change', '[name="rating"]', function() {
        let rating = $('[name="rating"]:checked').val();
        let movieId = movieDetailModal.prop('movieId');
        rateMovie(movieId, rating);
    });

    getRecommendations();
});

/**
 * Perform AJAX put to rate a movie
 * @param movieId
 * @param rating
 */
function rateMovie(movieId, rating) {
    let ratingData = {
        "movieId": movieId,
        "rating": rating
    }

    $.ajax({
        type: "PUT",
        url: 'movie/rate',
        contentType: 'application/json',
        data: JSON.stringify(ratingData),
        error: console.error
    });
}

/**
 * Query movie details from movie ID, assume response is using JSON
 * @param movieId ID of the movie to query
 * @param provenanceId ID of the provenance information associated with a movie
 */
function queryMovieDetail(movieId, provenanceId=null) {
    // Set the ID of the object for later use
    $('#movieDetailModal').prop('movieId', movieId);

    $.ajax({
        type: "GET",
        url: `/movie/${movieId}`,
        success: function(movieData) {
            displayMovieDetail(movieData, provenanceId)
        },
        error: console.error
    })
}

/**
 * Coverts JSON data into items that can be displayed
 * @param movieData
 * @param provenanceId
 */
function displayMovieDetail(movieData, provenanceId=null) {
    let title = movieData['title'];
    let release = movieData['releaseYear'];
    let imgUrl = movieData['posterUrl'];
    let rating = movieData['populatedRating'];

    $('#movieDetailTitle').text(title);
    $('#movieDetailRelease').text(`Released: ${release.split("-")[0]}`);
    $('#movieDetailImage').prop('src', (imgUrl != null) ? imgUrl : "/img/image-not-found.png");
    $("#movieDetailModal").modal('show');

    if (rating != null) {
        $(`#rate-${rating}`).prop('checked', true);
    }

    if(provenanceId != null) {
        $('#provenanceRow').prop('hidden', false);
        $('#provenanceBtn').prop('provId', provenanceId);
    }
}

/**
 * Function to extract the movie recommendations
 */
function getRecommendations() {
    $.ajax({
        type: "GET",
        url: "/recommendations",
        success: function(resBody) {
            $('#recommendations').replaceWith(resBody);
        },
        error: console.error
    })
}

function provenanceDisplay() {
    let provId = $('#provenanceBtn').prop('provId');

    $.ajax({
        type: "GET",
        url: `/provenance/${provId}`,
        success: function(data) {
            let dBox = $('#provenanceData');
            dBox.text(data);
            $('#provenanceDataContainer').prop('hidden', false);
            console.log(data);
        },
        error: console.error
    })
}
