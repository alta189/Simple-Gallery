var $imageDisplay = $('#image-display');

function addImage(pImage, id) {
	var grid = $('<div/>', {
		class: 'uk-grid'
	});

	var container = $('<div/>', {
		class: 'uk-width-1-1'
	}).appendTo(grid);

	var figure = $('<figure/>', {
		class: 'uk-thumbnail uk-thumbnail-expand'
	}).appendTo(container);

	var image = $('<img/>', {
		src: '/images/' + pImage,
		alt: 'image-' + id
	}).appendTo(figure);

	var caption = $('<figcaption/>', {
		class: 'uk-thumbnail-caption uk-grid'
	}).appendTo(figure);

	var captionLeft = $('<div/>', {
		class: "uk-text-left uk-width-1-2"
	}).appendTo(caption);

	var captionRight = $('<div/>', {
		class: "uk-text-right uk-width-1-2"
	}).appendTo(caption);

	var description = $('<span/>', {
		class: 'uk-text-left',
		html: 'Description here!'
	}).appendTo(captionLeft);

	var buttonSpan = $('<span/>', {
		class: 'uk-text-right'
	}).appendTo(captionRight);

	var editDescription = $('<a/>', {
		html: '<i class="uk-icon-pencil"></i>&nbsp;Edit Description'
	}).appendTo(buttonSpan);

	var deleteImage = $('<a/>', {
		html: '<i class="uk-icon-remove"></i>&nbsp;Delete'
	}).appendTo(buttonSpan);

	grid.appendTo($imageDisplay);
	console.log('done');
}

$(function() {
	var progressbar = $("#progressbar"),
		bar = progressbar.find('.uk-progress-bar'),
		settings = {
			action: '/api/images/upload', // upload url
			allow : '*.(jpg|jpeg|gif|png)', // allow only images,
			param : 'image',
			type : 'json',
			filelimit: false,

			loadstart: function() {
				bar.css("width", "0%").text("0%");
				progressbar.removeClass("uk-hidden");
			},

			progress: function(percent) {
				percent = Math.ceil(percent);
				bar.css("width", percent+"%").text(percent+"%");
			},

			complete: function(response) {
				console.log(JSON.stringify(response));
				if (!response.error) {
					addImage(response.result.file, response.result.id);
					console.log('add image');
				}
			},

			allcomplete: function (response) {
				bar.css("width", "100%").text("100%");

				setTimeout(function(){
					progressbar.addClass("uk-hidden");
				}, 2000);
			}
		};

	var select = UIkit.uploadSelect($("#upload-select"), settings),
		drop   = UIkit.uploadDrop($("#upload-drop"), settings);
});